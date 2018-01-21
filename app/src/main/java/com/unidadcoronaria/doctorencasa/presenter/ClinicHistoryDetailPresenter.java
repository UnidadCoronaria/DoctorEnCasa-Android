package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.ClinicHistoryDetailView;
import com.unidadcoronaria.doctorencasa.ClinicHistoryView;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.usecase.network.GetClinicHIstoryListUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryDetailPresenter extends BasePresenter<ClinicHistoryDetailView> {


    @Inject
    public ClinicHistoryDetailPresenter(){
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }


}
