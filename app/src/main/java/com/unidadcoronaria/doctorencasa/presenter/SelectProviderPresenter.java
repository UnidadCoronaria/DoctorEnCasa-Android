package com.unidadcoronaria.doctorencasa.presenter;

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

public class SelectProviderPresenter extends BasePresenter<SelectProviderView> {

    private GetProviderListUseCase mGetProviderListUseCase;

    @Inject
    public SelectProviderPresenter(GetProviderListUseCase mGetProviderListUseCase) {
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
        this.mGetProviderListUseCase.unsubscribe();
    }

}
