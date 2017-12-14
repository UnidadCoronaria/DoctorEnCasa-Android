package com.unidadcoronaria.doctorencasa.presenter;

import android.support.annotation.CallSuper;

import com.unidadcoronaria.doctorencasa.ClinicHistoryView;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.usecase.network.GetClinicHIstoryListUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryPresenter extends BasePresenter<ClinicHistoryView> {

    private GetClinicHIstoryListUseCase mGetClinicHistoryListUseCase;

    @Inject
    public ClinicHistoryPresenter(GetClinicHIstoryListUseCase mGetClinicHistoryListUseCase){
        this.mGetClinicHistoryListUseCase = mGetClinicHistoryListUseCase;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetClinicHistoryListUseCase.execute(
                o -> view.onClinicHistoryListRetrieved((List<ClinicHistory>) o),
                throwable -> view.onClinicHistoryListError());
    }

    @Override
    public void onStop(){
        super.onStop();
        mGetClinicHistoryListUseCase.unsubscribe();
    }


}
