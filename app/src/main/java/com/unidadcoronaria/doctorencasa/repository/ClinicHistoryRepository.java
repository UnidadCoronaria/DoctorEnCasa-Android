package com.unidadcoronaria.doctorencasa.repository;

import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.network.rest.ClinicHistoryService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryRepository {

    private ClinicHistoryService mClinicHistoryService;

    @Inject
    public ClinicHistoryRepository(ClinicHistoryService mClinicHistoryService) {
        this.mClinicHistoryService = mClinicHistoryService;
    }

    public Single<List<ClinicHistory>> getClinicHistoryList() {
        return mClinicHistoryService.get();
    }

}
