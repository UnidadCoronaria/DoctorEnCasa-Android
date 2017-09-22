package com.unidadcoronaria.doctorencasa.presenter;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.video.VideoCallListener;
import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.communication.SinchVideoCallService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class VideoCallPresenter extends BasePresenter<VideoCallView> {


    @Inject
    public VideoCallPresenter(){
    }

    public void performCall(String userId){
        SinchVideoCallService m = new SinchVideoCallService();
        m.initCall(userId, new VideoCallListener() {
            @Override
            public void onCallProgressing(Call call) {

            }

            @Override
            public void onCallEstablished(Call call) {

            }

            @Override
            public void onCallEnded(Call call) {

            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) {

            }

            @Override
            public void onVideoTrackAdded(Call call) {

            }

            @Override
            public void onVideoTrackPaused(Call call) {

            }

            @Override
            public void onVideoTrackResumed(Call call) {

            }
        });
    }

}
