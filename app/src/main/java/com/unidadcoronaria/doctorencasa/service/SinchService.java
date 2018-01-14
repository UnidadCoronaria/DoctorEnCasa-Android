package com.unidadcoronaria.doctorencasa.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.video.VideoController;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.activity.NewCallActivity;
import com.unidadcoronaria.doctorencasa.streaming.SinchCallManager;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

/**
 * Created by agustin on 4/1/18.
 */

public class SinchService extends Service {

    public static final String CALL_ID = "CALL_ID";
    static final String TAG = SinchService.class.getSimpleName();

    private SinchServiceInterface mSinchServiceInterface = new SinchServiceInterface();
    private SinchCallManager mCallManager = new SinchCallManager();
    private SinchCallManager.StartFailedListener mListener;
    private SinchClient mSinchClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("SinchService", "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e("SinchService", "onDestroy");
        mCallManager.stopClient();
        super.onDestroy();
    }


    private void createClient(String userName) {
        if(mSinchClient == null || !mSinchClient.isStarted()){
            mSinchClient = mCallManager.initClient(userName, mListener, new SinchCallClientListener());
        } else {
            mListener.onStarted();
        }

    }

    private void start(String username) {
        //TODO ver de no crear siempre un nuevo cliente pero que llame al start
        createClient(username);
        Log.d(TAG, "Starting SinchClient");
    }

    private void stop() {
        if(mSinchClient != null){
            mSinchClient.terminate();
            mSinchClient = null;
        }
    }


    private boolean isStarted() {
        return (mSinchClient != null && mSinchClient.isStarted());
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSinchServiceInterface;
    }

    public class SinchServiceInterface extends Binder {

        public boolean isStarted() {
            return SinchService.this.isStarted();
        }

        public void startClient(String userName) {
            start(userName);
        }

        public void stopClient() {
           stop();
        }

        public void setStartListener(SinchCallManager.StartFailedListener listener) {
            mListener = listener;
        }

        public Call getCall(String callId) {
            return mSinchClient.getCallClient().getCall(callId);
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

    }

    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            Log.d(TAG, "Incoming call");
            Intent intent = new Intent(SinchService.this, NewCallActivity.class);
            intent.putExtra(CALL_ID, call.getCallId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SinchService.this.startActivity(intent);
        }
    }
}
