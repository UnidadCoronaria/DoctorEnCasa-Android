package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.CreateAffiliateAccountView;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateListUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetProviderListUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class CreateAffiliateAccountPresenter extends BasePresenter<CreateAffiliateAccountView> {

    private GetProviderListUseCase mGetProviderListUseCase;
    private GetAffiliateListUseCase mGetAffiliateUseCase;

    @Inject
    public CreateAffiliateAccountPresenter(GetAffiliateListUseCase mGetAffiliateUseCase, GetProviderListUseCase mGetProviderListUseCase) {
        this.mGetAffiliateUseCase = mGetAffiliateUseCase;
        this.mGetProviderListUseCase = mGetProviderListUseCase;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mGetProviderListUseCase.execute(o -> view.onProviderListRetrieved((List<Provider>) o), throwable -> view.onProviderListError());
    }

    @Override
    public void onStop(){
        super.onStop();
        this.mGetAffiliateUseCase.unsubscribe();
        this.mGetProviderListUseCase.unsubscribe();
    }

    public void getAffiliateData(String affiliateNumber, Provider provider) {
        if(affiliateNumber != null && ! affiliateNumber.isEmpty() && provider != null){
            mGetAffiliateUseCase.setData(affiliateNumber, provider.getId());
            mGetAffiliateUseCase.execute(o -> view.onAffiliateListRetrieved((List<Affiliate>) o), throwable -> view.onGetAffiliateListError());
        }
        view.onEmptyAffiliateNumber();
    }

}
