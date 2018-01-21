package com.unidadcoronaria.doctorencasa.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.NewCallView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.domain.VideoCallStatus;
import com.unidadcoronaria.doctorencasa.presenter.NewCallPresenter;
import com.unidadcoronaria.doctorencasa.service.SinchService;
import com.unidadcoronaria.doctorencasa.streaming.AudioPlayer;
import com.unidadcoronaria.doctorencasa.streaming.SinchCallManager;
import com.unidadcoronaria.doctorencasa.dialog.RankDialog;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

@RuntimePermissions
public class NewCallFragment extends BaseFragment<NewCallPresenter> implements NewCallView {

    public static final String TAG = NewCallFragment.class.getSimpleName();

    @BindView(R.id.call_frame)
    protected LinearLayout vFrame;

    @BindView(R.id.call_self_camera)
    protected LinearLayout vSelfCamera;

    @BindView(R.id.fragment_video_call_container)
    protected View vContainer;

    @BindView(R.id.rl_error)
    protected View vErrorContainer;

    @BindView(R.id.fragment_new_video_call_calling)
    protected View vStartingContainer;

    @BindView(R.id.fragment_new_video_call_incoming)
    protected View vIncomingContainer;

    @BindView(R.id.fragment_video_call_hangup_button)
    protected View vHangoutButton;

    @BindView(R.id.fragment_video_call_stop_video)
    protected View vStopVideoButton;

    @BindView(R.id.fragment_video_call_mute)
    protected View vMuteButton;

    @BindView(R.id.fragment_new_call_incoming_effect)
    protected AVLoadingIndicatorView vIncomingCallEffect;

    private Call mCall;
    private RankDialog mRankDialog =  new RankDialog();
    private AudioPlayer mAudioPlayer;
    private SinchService.SinchServiceInterface mServiceInterface;


    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_new_video_call;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    public static NewCallFragment newInstance() {
        NewCallFragment instance = new NewCallFragment();
        return instance;
    }

    @Override
    protected void inject() {
        DaggerVideoCallComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
        vProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.removeCallListener(mCallListener);
        }
    }

    @Override
    public void initCall(Call call, SinchService.SinchServiceInterface serviceInterface){
        this.mCall = call;
        this.mServiceInterface = serviceInterface;
        this.mCall.addCallListener(mCallListener);

        if(mCall != null){
           NewCallFragmentPermissionsDispatcher.acceptCallWithPermissionCheck(this);
        }
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        NewCallFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
             Manifest.permission.READ_PHONE_STATE})
    protected void acceptCall() {
        vProgress.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vErrorContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vIncomingContainer.setVisibility(View.VISIBLE);
        mAudioPlayer = new AudioPlayer(getActivity());
        mAudioPlayer.playRingtone();
        vIncomingCallEffect.show();
        //MOSTRAR PANTALLA DE LLAMADA ENTRANTE
    }


    @OnClick(R.id.fragment_video_call_hangup_button)
    protected void onHangoutClick() {
        mCall.hangup();
    }

    @OnClick(R.id.fragment_video_call_answer_button)
    protected void onAnswerClick() {
        mAudioPlayer.stopRingtone();
        mCall.answer();
    }

    @OnClick(R.id.fragment_video_call_decline_button)
    protected void declineClicked() {
        mAudioPlayer.stopRingtone();
        if (mCall != null) {
            mCall.hangup();

        }
        getActivity().finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        //TODO removeVideoViews();
    }

    private void showRankDialog(){
        mRankDialog.dismiss();
        mRankDialog.showRankMessage(getActivity(),
                new RankDialog.Callback() {
                    @Override
                    public void onPositiveClick(String comment, int ranking) {
                        mPresenter.rank(comment, ranking);
                    }

                    @Override
                    public void onNegativeClick() {
                        getActivity().finish();
                    }
                });
    }

    private void addVideoViews() {
        final VideoController vc = mServiceInterface.getVideoController();
        if (vc != null) {
            vFrame.addView(vc.getRemoteView());
            vFrame.setOnClickListener(v -> vc.toggleCaptureDevicePosition());
            vSelfCamera.addView(vc.getLocalView());
            vHangoutButton.setVisibility(View.VISIBLE);
            vStartingContainer.setVisibility(View.GONE);
            vContainer.setVisibility(View.VISIBLE);
            vIncomingContainer.setVisibility(View.GONE);
        }
    }

    private void removeVideoViews() {
        VideoController vc = mServiceInterface.getVideoController();
        if (vc != null) {
            vFrame.removeView(vc.getRemoteView());
            vSelfCamera.removeView(vc.getLocalView());
            vContainer.setVisibility(View.GONE);
            vHangoutButton.setVisibility(View.VISIBLE);
        }
    }


    //region Permissions Handling
    @SuppressLint("NoCorrespondingNeedsPermission")
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.permissions_need_acepted))
                .setPositiveButton(getString(R.string.yes), (dialog, button) -> request.proceed())
                .setNegativeButton(getString(R.string.no), (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE})
    void showDeniedForCamera() {
        vErrorContainer.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        Toast.makeText(getActivity()
                , getString(R.string.permissions_denied), Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE})
    void showNeverAskForCamera() {
        vErrorContainer.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        Toast.makeText(getActivity(), getString(R.string.permissions_never_ask), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRankSuccess() {
        vProgress.setVisibility(View.GONE);
        getActivity().finish();
    }

    @Override
    public void onRankError() {
        vProgress.setVisibility(View.GONE);
        new AlertDialog.Builder(getActivity())
                .setMessage("Hubo un error. Por favor, vuelva a intentarlo.")
                .setPositiveButton(getString(R.string.cancel), (dialog, button) -> getActivity().finish())
                .setNegativeButton(getString(R.string.retry), (dialog, button) -> showRankDialog())
                .setCancelable(false)
                .show();
    }
    //endregion

    private long mCallStart;
    private CallListener mCallListener = new VideoCallListener() {
        @Override
        public void onCallEnded(Call call) {
            mAudioPlayer.stopRingtone();
            vContainer.setVisibility(View.GONE);
            vStartingContainer.setVisibility(View.GONE);
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            getActivity().setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            String endMsg = "Call ended: " + call.getDetails().toString();
            showRankDialog();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            AudioController audioController = mServiceInterface.getAudioController();
            audioController.enableSpeaker();
            mCallStart = System.currentTimeMillis();
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

        @Override
        public void onVideoTrackAdded(Call call) {
            Log.d(TAG, "Video track added");
            addVideoViews();
        }

        @Override
        public void onVideoTrackPaused(Call call) {

        }

        @Override
        public void onVideoTrackResumed(Call call) {

        }
    };
}
