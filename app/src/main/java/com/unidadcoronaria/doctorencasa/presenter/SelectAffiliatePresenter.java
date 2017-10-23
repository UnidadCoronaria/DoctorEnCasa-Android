package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.SelectAffiliateView;
import com.unidadcoronaria.doctorencasa.SelectProviderView;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateListUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetProviderListUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class SelectAffiliatePresenter extends BasePresenter<SelectAffiliateView> {

    private GetAffiliateListUseCase mGetAffiliateUseCase;

    @Inject
    public SelectAffiliatePresenter(GetAffiliateListUseCase mGetAffiliateUseCase) {
        this.mGetAffiliateUseCase = mGetAffiliateUseCase;
    }

    @Override
    public void onStop(){
        super.onStop();
        this.mGetAffiliateUseCase.unsubscribe();
    }

    public void getAffiliateData(String affiliateNumber, int provider) {
        if(affiliateNumber != null && ! affiliateNumber.isEmpty()){
            mGetAffiliateUseCase.setData(affiliateNumber, provider);
            mGetAffiliateUseCase.execute(o -> view.onAffiliateListRetrieved((List<Affiliate>) o), throwable -> view.onGetAffiliateListError());
            return;
        }
        view.onEmptyAffiliateNumber();
    }

}