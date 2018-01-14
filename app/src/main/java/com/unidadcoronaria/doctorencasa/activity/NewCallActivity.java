package com.unidadcoronaria.doctorencasa.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.NewCallFragment;
import com.unidadcoronaria.doctorencasa.service.SinchService;
import com.unidadcoronaria.doctorencasa.streaming.SinchCallManager;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;


/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class NewCallActivity extends BaseActivity implements SinchCallManager.StartFailedListener {

    private String mCallId;
    private NewCallFragment mNewCallFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_new_video_call;
    }


    @Override
    protected NewCallFragment getFragment() {
        mNewCallFragment = NewCallFragment.newInstance();
        return mNewCallFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    @Override
    public void onAttachedToWindow() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }


    @Override
    public void onStartFailed(SinchError error) {
        //TODO Show error
    }

    @Override
    public void onStopped() {
        //TODO Is there anything we can do here?
    }

    @Override
    public void onStarted() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mNewCallFragment.initCall(call, getSinchServiceInterface());
        } else {
            Log.e("NewCallActivity", "Started with invalid callId, aborting");
            finish();
        }
    }

    protected void onServiceConnected() {
        if(getSinchServiceInterface() != null){
            getSinchServiceInterface().setStartListener(this);
            getSinchServiceInterface().startClient(SessionUtil.getUsername());
        }
    }

}
