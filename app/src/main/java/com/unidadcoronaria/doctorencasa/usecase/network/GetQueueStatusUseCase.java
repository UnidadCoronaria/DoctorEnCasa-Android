package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.repository.VideoCallRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class GetQueueStatusUseCase extends SingleItemUseCase {

    private final VideoCallRepository mVideoCallRepository;

    @Inject
    public GetQueueStatusUseCase(VideoCallRepository mVideoCallRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mVideoCallRepository = mVideoCallRepository;
    }

    @Override
    public Single<Queue> buildUseCaseObservable() {
        return this.mVideoCallRepository.getQueueStatus();
    }

}
