package com.unidadcoronaria.doctorencasa.streaming;

import android.util.Log;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;
import com.unidadcoronaria.doctorencasa.App;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

public class SinchCallManager implements CallManager<Call> {

    private static final String APP_KEY = "f8a3b0ad-ac00-4e1b-988d-9f2902d8582a";
    private static final String APP_SECRET = "KiIT/CNYsUekiUskZ2D4GA==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";
    private static final String TAG = "SinchCallManager";

    private StartFailedListener mListener;
    private SinchClient mSinchClient;
    private String mUserId;



    public SinchClient initClient(String userName, StartFailedListener mListener, CallClientListener mIncomingCallClientListener){
            mUserId = userName;
            mSinchClient = Sinch.getSinchClientBuilder().context(App.getInstance()).userId(mUserId)
                    .applicationKey(APP_KEY)
                    .applicationSecret(APP_SECRET)
                    .environmentHost(ENVIRONMENT).build();
            mSinchClient.setSupportCalling(true);
            mSinchClient.setSupportActiveConnectionInBackground(true);
            mSinchClient.startListeningOnActiveConnection();
            mSinchClient.addSinchClientListener(new ClientListener());
            mSinchClient.getCallClient().addCallClientListener(mIncomingCallClientListener);
            mSinchClient.start();
        this.mListener = mListener;
        return mSinchClient;
    }

    public Call makeCall(String userId, VideoCallListener callListener) {
        Call mCall = mSinchClient.getCallClient().callUserVideo(userId);
        if (mCall != null) {
            mCall.addCallListener(callListener);
        }
        return mCall;
    }

    public Call getCall(String callId, VideoCallListener callListener) {
        Call mCall = mSinchClient.getCallClient().getCall(callId);
        if (mCall != null) {
            mCall.addCallListener(callListener);
        }
        return mCall;
    }


    public VideoController getVideoController() {
        if (!isStarted()) {
            return null;
        }
        return mSinchClient.getVideoController();
    }

    public AudioController getAudioController() {
        if (!isStarted()) {
            return null;
        }
        return mSinchClient.getAudioController();
    }

    public boolean isStarted() {
        return (mSinchClient != null && mSinchClient.isStarted());
    }


    public void stopClient(){
        if (mSinchClient != null) {
            mSinchClient.terminate();
            mSinchClient = null;
        }
    }

    public interface StartFailedListener {

        void onStartFailed(SinchError error);

        void onStopped();

        void onStarted();
    }

    private class ClientListener implements SinchClientListener {

        @Override
        public void onClientFailed(SinchClient client, SinchError error) {
            Log.d(TAG, "SinchClient error "+error.getMessage());
            if (mListener != null) {
                mListener.onStartFailed(error);
            }
            mSinchClient.terminate();
            mSinchClient = null;
        }

        @Override
        public void onClientStarted(SinchClient client) {
            Log.d(TAG, "SinchClient started");
            if (mListener != null) {
                mListener.onStarted();
            }
        }

        @Override
        public void onClientStopped(SinchClient client) {
            Log.d(TAG, "SinchClient stopped");
            if(mListener != null){
                mListener.onStopped();
            }
        }

        @Override
        public void onLogMessage(int level, String area, String message) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(area, message);
                    break;
                case Log.ERROR:
                    Log.e(area, message);
                    break;
                case Log.INFO:
                    Log.i(area, message);
                    break;
                case Log.VERBOSE:
                    Log.v(area, message);
                    break;
                case Log.WARN:
                    Log.w(area, message);
                    break;
            }
        }

        @Override
        public void onRegistrationCredentialsRequired(SinchClient client,
                                                      ClientRegistration clientRegistration) {
        }
    }

}
