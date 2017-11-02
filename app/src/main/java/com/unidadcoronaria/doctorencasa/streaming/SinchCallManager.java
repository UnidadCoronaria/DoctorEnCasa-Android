package com.unidadcoronaria.doctorencasa.streaming;

import android.content.Intent;
import android.util.Log;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;
import com.unidadcoronaria.doctorencasa.App;

import java.util.List;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

public class SinchCallManager implements CallManager<Call, SinchCallManager.SinchCallListener> {

    private static final String APP_KEY = "f8a3b0ad-ac00-4e1b-988d-9f2902d8582a";
    private static final String APP_SECRET = "KiIT/CNYsUekiUskZ2D4GA==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";
    private static final String TAG = "SinchCallManager";

    private StartFailedListener mListener;
    private SinchClient mSinchClient;
    private String mUserId;

    public void initClient(String userName, StartFailedListener mListener){
        if (mSinchClient == null) {
            mUserId = userName;
            mSinchClient = Sinch.getSinchClientBuilder().context(App.getInstance()).userId(mUserId)
                    .applicationKey(APP_KEY)
                    .applicationSecret(APP_SECRET)
                    .environmentHost(ENVIRONMENT).build();

            mSinchClient.setSupportCalling(true);
            mSinchClient.startListeningOnActiveConnection();

            mSinchClient.addSinchClientListener(new ClientListener());
            mSinchClient.getCallClient().addCallClientListener(new IncomingCallClientListener());
            mSinchClient.start();
        }
        this.mListener = mListener;
    }

    public Call makeCall(String userId, SinchCallListener callListener) {
        Call mCall = mSinchClient.getCallClient().callUserVideo(userId);
        if (mCall != null) {
            mCall.addCallListener(callListener);
        }
        return mCall;
    }

    public Call getCall(String callId, SinchCallListener callListener) {
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

    public void resumeListeningIncomingCalls() {
        if (mSinchClient != null) {
            mSinchClient.startListeningOnActiveConnection();
        }
    }

    public void stopListeningIncomingCalls(){
        if(mSinchClient != null) {
            mSinchClient.stopListeningOnActiveConnection();
        }
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

    private class IncomingCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            Log.d(TAG, "Incoming call");
            /*Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
            intent.putExtra(CALL_ID, call.getCallId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*/

        }
    }

    public static class SinchCallListener implements VideoCallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.i(TAG, "Call ended, cause: " + cause.toString());
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.i(TAG, "Call established");
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.i(TAG, "Call progressing");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

        @Override
        public void onVideoTrackAdded(Call call) {
            // Display some kind of icon showing it's a video call
        }

        @Override
        public void onVideoTrackPaused(Call call) {

        }

        @Override
        public void onVideoTrackResumed(Call call) {

        }
    }
}
