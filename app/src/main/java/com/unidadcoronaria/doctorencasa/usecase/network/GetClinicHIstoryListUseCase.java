package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.repository.ClinicHistoryRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class GetClinicHIstoryListUseCase extends SingleItemUseCase {

    private final ClinicHistoryRepository mClinicHistoryRepository;

    @Inject
    public GetClinicHIstoryListUseCase(ClinicHistoryRepository mClinicHistoryRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mClinicHistoryRepository = mClinicHistoryRepository;
    }

    @Override
    public Single<List<ClinicHistory>> buildUseCaseObservable() {
        return this.mClinicHistoryRepository.getClinicHistoryList();
    }


}
