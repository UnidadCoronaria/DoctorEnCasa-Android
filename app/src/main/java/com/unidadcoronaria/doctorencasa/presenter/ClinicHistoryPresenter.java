package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.ClinicHistoryView;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.usecase.network.GetClinicHIstoryListUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryPresenter extends BasePresenter<ClinicHistoryView> {

    private GetClinicHIstoryListUseCase mGetAffiliateCallHistoryUseCase;

    @Inject
    public ClinicHistoryPresenter(GetClinicHIstoryListUseCase mGetAffiliateCallHistoryUseCase){
        this.mGetAffiliateCallHistoryUseCase = mGetAffiliateCallHistoryUseCase;
    }

    public void nninit() {
        mGetAffiliateCallHistoryUseCase.execute(
                o -> view.onClinicHistoryListRetrieved((List<ClinicHistory>) o),
                throwable -> checkTokenExpired(throwable, () -> {
                    view.onClinicHistoryListError();
                    return null;
                }));
    }


    @Override
    public void onStop(){
        super.onStop();
        mGetAffiliateCallHistoryUseCase.unsubscribe();
    }


}
