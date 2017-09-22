package com.unidadcoronaria.doctorencasa.communication;

import android.content.Context;
import android.hardware.Camera;
import android.view.View;
import android.view.ViewGroup;

import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.List;

/**
 * Created by AGUSTIN.BALA on 7/30/2017.
 */

public class SinchVideoCallService implements VideoCallService {

    private static final String APP_KEY = "f8a3b0ad-ac00-4e1b-988d-9f2902d8582a";
    private static final String APP_SECRET = "KiIT/CNYsUekiUskZ2D4GA==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";
    private SinchClient mSinchClient;

    @Override
    public void startService(Context context,SinchClientListener mSinchClientListener) {
        if(mSinchClient != null && mSinchClient.isStarted()){
            mSinchClient.terminate();
        }
        mSinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT)
                // TODO: Use username to identify user
                .userId(SessionUtil.getToken())
                .build();

        // Specify the client capabilities.
        // At least one of the messaging or calling capabilities should be enabled.
        mSinchClient.setSupportCalling(true);
        mSinchClient.setSupportManagedPush(true);

        //Note: Only for active connection
        //sinchClient.setSupportActiveConnectionInBackground(true);
        //sinchClient.startListeningOnActiveConnection();

        //Note: If the application is meant to only make outgoing calls but not receive incoming calls,
        // don’t call startListeningOnActiveConnection or setSupportManagedPush.
        // Outgoing calls can be made after calling the start method.

        mSinchClient.addSinchClientListener(mSinchClientListener);
        mSinchClient.start();
    }

    @Override
    public void stopService() {
        if(mSinchClient != null){
            mSinchClient.terminate();
        }
    }

    @Override
    public void initCall(String userID, VideoCallListener mVideoCallListener) {
        if (mSinchClient != null) {
            CallClient callClient = mSinchClient.getCallClient();
            Call call = callClient.callUserVideo(userID);
            call.addCallListener(mVideoCallListener);
        }
    }

    @Override
    public void addVideoCallInterface(ViewGroup mLocalView, ViewGroup mRemoteView) {
        VideoController vc = mSinchClient.getVideoController();
        vc.setCaptureDevicePosition(Camera.CameraInfo.CAMERA_FACING_FRONT);
        View myPreview = vc.getLocalView();
        mLocalView.addView(myPreview);
        View remoteView = vc.getRemoteView();
        mRemoteView.addView(remoteView);
    }

    @Override
    public void removeVideoCallInterface(ViewGroup mLocalView, ViewGroup mRemoteView) {
        mLocalView.removeAllViews();
        mRemoteView.removeAllViews();
    }
}
