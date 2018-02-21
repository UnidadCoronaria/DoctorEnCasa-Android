package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.ClinicHistoryView;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateCallHistoryUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetClinicHIstoryListUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.List;

import javax.inject.Inject;

import retrofit2.HttpException;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryPresenter extends BasePresenter<ClinicHistoryView> {

    private GetClinicHIstoryListUseCase mGetAffiliateCallHistoryUseCase;

    @Inject
    public ClinicHistoryPresenter(GetClinicHIstoryListUseCase mGetAffiliateCallHistoryUseCase){
        this.mGetAffiliateCallHistoryUseCase = mGetAffiliateCallHistoryUseCase;
    }

    public void init() {
        mGetAffiliateCallHistoryUseCase.execute(
                o -> view.onClinicHistoryListRetrieved((List<ClinicHistory>) o),
                throwable -> handleException(throwable));
    }

    private void handleException(Throwable throwable){
        if (throwable instanceof HttpException) {
            // We had non-2XX http error
            HttpException e = (HttpException) throwable;
            if(e.response().code() == 408){
                SessionUtil.logout();
                view.logout();
                return;
            }
        }
        view.onClinicHistoryListError();
    }

    @Override
    public void onStop(){
        super.onStop();
        mGetAffiliateCallHistoryUseCase.unsubscribe();
    }


}
