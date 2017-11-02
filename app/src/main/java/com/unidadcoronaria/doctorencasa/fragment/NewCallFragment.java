package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.video.VideoController;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.presenter.VideoCallPresenter;
import com.unidadcoronaria.doctorencasa.streaming.SinchCallManager;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class NewCallFragment extends BaseFragment<VideoCallPresenter> implements VideoCallView {

    public static final String TAG = NewCallFragment.class.getSimpleName();

    @BindView(R.id.call_frame)
    protected RelativeLayout vFrame;

    @BindView(R.id.call_self_camera)
    protected RelativeLayout vSelfCamera;

    @BindView(R.id.fragment_video_call_button)
    protected View vButtonCall;

    @BindView(R.id.fragment_video_call_container)
    protected View vContainer;

    private SinchCallManager mCallManager = new SinchCallManager();
    private Call mCall;
    public final static String CALL_DESTINATION_KEY = "com.unidadcoronaria.doctorencasa.fragment.newcallfragment.CALL_DESTINATION_KEY";
    private String mCallDestination;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_new_video_call;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
       DaggerVideoCallComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
        if(getArguments() != null && getArguments().containsKey(CALL_DESTINATION_KEY)){
            mCallDestination = getArguments().getString(CALL_DESTINATION_KEY);
        }

        mCallManager.initClient(SessionUtil.getUsername(), new SinchCallManager.StartFailedListener() {
            @Override
            public void onStartFailed(SinchError error) {
                Log.i(TAG,  "Error in onStartFailed");
                Toast.makeText(getActivity(), "Error in onStartFailed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStopped() {
                Log.i(TAG,  "Sinch Client stopped");
                Toast.makeText(getActivity(), "Sinch Client stopped", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStarted() {
                Log.i(TAG,  "Sinch Client started");
                Toast.makeText(getActivity(), "Sinch Client started", Toast.LENGTH_LONG).show();
                makeCall();
            }
        });
    }

    public static NewCallFragment newInstance(String callDestination) {
        NewCallFragment instance = new NewCallFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(CALL_DESTINATION_KEY, callDestination);
        instance.setArguments(bundle);
        return instance;
    }

    protected void makeCall(){
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
        mCall = mCallManager.makeCall(mCallDestination, new SinchCallManager.SinchCallListener(){
            @Override
            public void onCallEstablished(Call call) {
                super.onCallEstablished(call);
                addVideoViews();
            }


            @Override
            public void onCallEnded(Call call) {
                super.onCallEnded(call);
                onHangoutClick();
            }

        });
    }


    @OnClick(R.id.fragment_video_call_hangup_button)
    protected void onHangoutClick(){
        mCall.hangup();
        removeVideoViews();
        vButtonCall.setVisibility(View.VISIBLE);
        vContainer.setVisibility(View.GONE);
    }

    private void addVideoViews() {
        final VideoController vc = mCallManager.getVideoController();
        if (vc != null) {
            vFrame.addView(vc.getRemoteView());
            vFrame.setOnClickListener(v -> vc.toggleCaptureDevicePosition());
            vSelfCamera.addView(vc.getLocalView());
        }
    }

    private void removeVideoViews() {
        VideoController vc = mCallManager.getVideoController();
        if (vc != null) {
           vFrame.removeView(vc.getRemoteView());
           vSelfCamera.removeView(vc.getLocalView());
        }
    }

    public void onStop() {
        super.onStop();
        mCallManager.stopClient();
    }
}
