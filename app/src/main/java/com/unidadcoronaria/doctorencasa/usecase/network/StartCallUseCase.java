package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;
import com.unidadcoronaria.doctorencasa.repository.VideoCallRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class StartCallUseCase extends SingleItemUseCase {

    private final VideoCallRepository mVideoCallRepository;
    private VideoCallDTO videoCallDTO;


    @Inject
    public StartCallUseCase(VideoCallRepository mVideoCallRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mVideoCallRepository = mVideoCallRepository;
    }

    @Override
    public Single<VideoCall> buildUseCaseObservable() {
        return this.mVideoCallRepository.startCall(this.videoCallDTO);
    }

    public void setData(VideoCallDTO mVideoCall){
        this.videoCallDTO = mVideoCall;
    }


}
