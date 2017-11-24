package com.unidadcoronaria.doctorencasa.repository;


import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;
import com.unidadcoronaria.doctorencasa.network.rest.VideoCallService;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by AGUSTIN.BALA on 5/25/2017.
 */

public class VideoCallRepository {

    private final VideoCallService mVideoCallService;


    @Inject
    public VideoCallRepository(VideoCallService mVideoCallService) {
        this.mVideoCallService = mVideoCallService;
    }

    public Single<VideoCall> hangup(VideoCallDTO videoCallDTO) {
        return mVideoCallService.hangup(videoCallDTO);
    }

    public Single<VideoCall> startCall(VideoCallDTO mVideoCallDTO) {
        return mVideoCallService.start(mVideoCallDTO);
    }

    public Single<VideoCall> rank(VideoCallDTO mVideoCallDTO) {
        return mVideoCallService.rank(mVideoCallDTO);
    }

    public Single<VideoCall> create() {
        return mVideoCallService.create();
    }

    public Single<Queue> getQueueStatus() {
        return mVideoCallService.getQueue();
    }

    public Single<VideoCall> getLastCall() {
        return mVideoCallService.getLastCall();
    }
}
