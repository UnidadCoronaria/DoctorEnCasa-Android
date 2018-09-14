package com.unidadcoronaria.doctorencasa.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;
import com.twilio.video.CameraCapturer;
import com.twilio.video.ConnectOptions;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrackPublication;
import com.twilio.video.Room;
import com.twilio.video.RoomState;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;
import com.twilio.video.VideoRenderer;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.NewCallView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.dialog.RankDialog;
import com.unidadcoronaria.doctorencasa.presenter.NewCallPresenter;
import com.unidadcoronaria.doctorencasa.streaming.AudioPlayer;
import com.unidadcoronaria.doctorencasa.streaming.RemoteParticipantListener;
import com.unidadcoronaria.doctorencasa.util.CameraCapturerCompat;
import com.unidadcoronaria.doctorencasa.util.ProviderUtil;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.Collections;

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
    protected VideoView vFrame;

    @BindView(R.id.call_self_camera)
    protected VideoView vSelfCamera;

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

    @BindView(R.id.fragment_video_call_switch_video)
    protected View vSwitchVideoButton;

    @BindView(R.id.fragment_video_call_container_all_button)
    protected View vButtonContainer;

    @BindView(R.id.fragment_video_call_mute)
    protected View vMuteButton;

    @BindView(R.id.fragment_new_call_incoming_effect)
    protected RippleBackground vIncomingCallEffect;

    @BindView(R.id.fragment_video_call_delay)
    protected TextView vCallRemainingTime;

    @BindView(R.id.fragment_new_video_call_rank_background)
    protected View vRankBackground;

    private RankDialog mRankDialog = new RankDialog();
    private AudioPlayer mAudioPlayer;
    private AudioManager audioManager;
    private Handler mRemainingMinutesHandler = new Handler();
    private Handler mHideButtonsHandler = new Handler();


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
        vIncomingCallEffect.startRippleAnimation();
        vProgress.setVisibility(View.VISIBLE);
        audioManager = (AudioManager) App.getInstance().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
        vMuteButton.setSelected(audioManager.isMicrophoneMute());
        if (SessionUtil.isCallPending()) {
            roomName = SessionUtil.getRoomName();
            token = SessionUtil.getTwilioToken();
            createAudioAndVideoTracks();
            NewCallFragmentPermissionsDispatcher.acceptCallWithPermissionCheck(this);
        } else {
            showSingleOptionDialog(getString(R.string.videocall_not_available));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeVideoTrack();
    }

    @Override
    public void onDestroy() {
        destroyVideoTracks();
        mRemainingMinutesHandler.removeCallbacksAndMessages(null);
        mHideButtonsHandler.removeCallbacksAndMessages(null);
        if (room != null) {
            room.disconnect();
        }
        super.onDestroy();
    }


    @Override
    public void onPause() {
        dismissVideoTrack();
        super.onPause();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        NewCallFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE})
    protected void acceptCall() {
        vProgress.setVisibility(View.GONE);
        vRankBackground.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vErrorContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vIncomingContainer.setVisibility(View.VISIBLE);
        mAudioPlayer = new AudioPlayer(getActivity());
        mAudioPlayer.playRingtone();
        onCallProgressing();
    }


    @OnClick(R.id.fragment_video_call_hangup_button)
    protected void onHangoutClick() {
        if (room != null) {
            room.disconnect();
        }
        vIncomingCallEffect.stopRippleAnimation();
        onCallEnded();
    }

    @OnClick(R.id.fragment_video_call_answer_button)
    protected void onAnswerClick() {
        vIncomingCallEffect.stopRippleAnimation();
        mAudioPlayer.stopRingtone();
        vMuteButton.setSelected(audioManager.isMicrophoneMute());
        connectToRoom(roomName);
        SessionUtil.finishCall();
    }

    @OnClick(R.id.call_frame)
    protected void onVideoClick() {
        if (vButtonContainer.getVisibility() == View.INVISIBLE) {
            showInAnimation(vButtonContainer);
        }
        startHideButtonsHandler();
    }

    @OnClick(R.id.fragment_video_call_decline_button)
    protected void declineClicked() {
        SessionUtil.finishCall();
        mAudioPlayer.stopRingtone();
        finishWithError(Activity.RESULT_OK);
    }

    @OnClick(R.id.fragment_video_call_switch_video)
    protected void onSwitchCamera() {
        if (cameraCapturerCompat != null) {
            CameraCapturer.CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
            cameraCapturerCompat.switchCamera();
            vSelfCamera.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
            startHideButtonsHandler();
        }
    }

    @OnClick(R.id.fragment_video_call_stop_video)
    protected void onStopVideo() {
        if (localVideoTrack != null) {
            boolean enable = !localVideoTrack.isEnabled();
            localVideoTrack.enable(enable);
            vStopVideoButton.setSelected(enable);
            startHideButtonsHandler();
        }
    }

    @OnClick(R.id.fragment_video_call_mute)
    protected void onMuteClick() {
        if (localAudioTrack != null) {
            boolean enable = !localAudioTrack.isEnabled();
            localAudioTrack.enable(enable);
            vMuteButton.setSelected(enable);
            startHideButtonsHandler();
        }
    }


    private void showRankDialog() {
        vRankBackground.setVisibility(View.VISIBLE);
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


    //region Permissions Handling
    @SuppressLint("NoCorrespondingNeedsPermission")
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.permissions_need_acepted))
                .setPositiveButton(getString(R.string.yes).toUpperCase(), (dialog, button) -> request.proceed())
                .setNegativeButton(getString(R.string.no).toUpperCase(), (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS})
    void showDeniedForCamera() {
        vRankBackground.setVisibility(View.GONE);
        vErrorContainer.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        Toast.makeText(getActivity()
                , getString(R.string.permissions_denied), Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS})
    void showNeverAskForCamera() {
        vRankBackground.setVisibility(View.GONE);
        vErrorContainer.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        Toast.makeText(getActivity(), getString(R.string.permissions_never_ask), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRankSuccess(int ranking) {
        vProgress.setVisibility(View.GONE);
        if (ranking > 3) {
            getActivity().finish();
        } else {
            AlertDialog.Builder dialogConfirmBuilder = new AlertDialog.Builder(getActivity()).setMessage("Calificaste la consulta negativamente, te gustaría comunicarte con una operadora para solicitar una visita domiciliaria?")
                    .setPositiveButton("No", (dialog, which) -> getActivity().finish())
                    .setNegativeButton("Si", (dialog, button) -> callToCentral()).setCancelable(false);

            AlertDialog alertDialog = dialogConfirmBuilder.create();
            alertDialog.setOnShowListener(dialog -> {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            });
            alertDialog.show();
        }

    }

    private void callToCentral() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String tel = "tel:" + ProviderUtil.getTelephoneNumber();

        callIntent.setData(Uri.parse(tel));
        startActivity(callIntent);
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

    private void onCallEnded() {
        mAudioPlayer.stopRingtone();
        vContainer.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vIncomingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vHangoutButton.setVisibility(View.VISIBLE);
        mRemainingMinutesHandler.removeCallbacksAndMessages(null);
        mHideButtonsHandler.removeCallbacksAndMessages(null);
        showRankDialog();
    }

    private void onCallEstablished() {
        vHangoutButton.setVisibility(View.VISIBLE);
        vStartingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
        vIncomingContainer.setVisibility(View.GONE);
        vRankBackground.setVisibility(View.GONE);
        vStopVideoButton.setSelected(true);
        vMuteButton.setSelected(true);
        vSwitchVideoButton.setSelected(true);
        mRemainingMinutesHandler.removeCallbacksAndMessages(null);
        startHideButtonsHandler();
    }

    public void onCallProgressing() {
        Log.d(TAG, "Call progressing");
        //vCallRemainingTime.setText(getString(R.string.remaining_time_to_answer, "3 minutos"));
        mRemainingMinutesHandler.postDelayed(() -> {
            if (SessionUtil.isCallPending()) {
                SessionUtil.finishCall();
                finishWithError(Activity.RESULT_FIRST_USER);
            }
        }, 30 * 1000);
    }

    private void finishWithError(int error) {
        Intent returnIntent = new Intent();
        getActivity().setResult(error, returnIntent);
        getActivity().finish();
    }

    private void startHideButtonsHandler() {
        mHideButtonsHandler.removeCallbacksAndMessages(null);
        mHideButtonsHandler.postDelayed(() -> {
            showOutAnimation(vButtonContainer);
        }, 3 * 1000);
    }

    private void showInAnimation(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void showOutAnimation(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }

    private void resetView() {
        vContainer.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vIncomingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vHangoutButton.setVisibility(View.GONE);
        vRankBackground.setVisibility(View.VISIBLE);
        mRemainingMinutesHandler.removeCallbacksAndMessages(null);
        mHideButtonsHandler.removeCallbacksAndMessages(null);
    }

    private void showSingleOptionDialog(String text) {
        resetView();
        new AlertDialog.Builder(getActivity())
                .setMessage(text)
                .setPositiveButton(getString(R.string.accept).toUpperCase(), (dialog, button) -> {
                    startActivity(MainActivity.getStartIntent(getActivity()));
                    getActivity().finish();
                })
                .show();
    }

    // region TWILIO
    /*
     * Audio and video tracks can be created with names. This feature is useful for categorizing
     * tracks of participants. For example, if one participant publishes a video track with
     * ScreenCapturer and CameraCapturer with the names "screen" and "camera" respectively then
     * other participants can use RemoteVideoTrack#getName to determine which video track is
     * produced from the other participant's screen or camera.
     */
    private static final String LOCAL_AUDIO_TRACK_NAME = "mic";
    private static final String LOCAL_VIDEO_TRACK_NAME = "camera";
    //public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzZlYWEyZjQxYjMzNzU2YzM2NTE4MzVlODIyODI0MDAxLTE1MzAxOTk4MzgiLCJpc3MiOiJTSzZlYWEyZjQxYjMzNzU2YzM2NTE4MzVlODIyODI0MDAxIiwic3ViIjoiQUNiNjM5NGFmNmU0YTNlMzE1NDQxMDA3MGIwNjJkNmM1ZSIsImV4cCI6MTUzMDIwMzQzOCwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiYWd1c3RpbiIsInZpZGVvIjp7fX19.kzjFMMhxQSYdLnURGJ7n0bUje5LZsroFSisprBuNNUc";
    //public static final String ROOM_NAME = "jblanco";
    private String token;
    private String roomName;
    private LocalAudioTrack localAudioTrack;
    private LocalVideoTrack localVideoTrack;
    private VideoRenderer localVideoView;
    private Room room;
    private CameraCapturerCompat cameraCapturerCompat;
    private LocalParticipant localParticipant;
    private int previousAudioMode;
    private boolean previousMicrophoneMute;
    private String remoteParticipantIdentity;


    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {
                localParticipant = room.getLocalParticipant();
                Log.d("onConnected", "onConnected");
                if (room.getRemoteParticipants().isEmpty()) {
                    Log.d("onConnected", "No remote participants");
                    showSingleOptionDialog(getString(R.string.videocall_not_available));
                    SessionUtil.finishCall();
                    room.disconnect();
                } else {
                    for (RemoteParticipant remoteParticipant : room.getRemoteParticipants()) {
                        Log.d("onConnected", "Adding remote participant "+remoteParticipant.getIdentity());
                        addRemoteParticipant(remoteParticipant);
                        break;
                    }
                    onCallEstablished();
                }
            }

            @Override
            public void onConnectFailure(Room room, TwilioException e) {
                Log.d("onConnectFailure", "onConnectFailure");
                SessionUtil.finishCall();
                mAudioPlayer.stopRingtone();
                configureAudio(false);
                if (e.getCode() == TwilioException.ACCESS_TOKEN_EXPIRED_EXCEPTION) {
                    Log.d("onConnectFailure", "Token expired");
                    showSingleOptionDialog(getString(R.string.videocall_not_available));
                } else {
                    Log.d("onConnectFailure", "Unknown issue "+e.getCode());
                    showSingleOptionDialog(getString(R.string.error_starting_call));
                }
            }

            @Override
            public void onDisconnected(Room room, TwilioException e) {
                if(e != null)
                Log.d("onDisconnected", "Disconnected "+e.getCode());
                localParticipant = null;
                NewCallFragment.this.room = null;
                configureAudio(false);
            }

            @Override
            public void onParticipantConnected(Room room, RemoteParticipant remoteParticipant) {
                addRemoteParticipant(remoteParticipant);

            }

            @Override
            public void onParticipantDisconnected(Room room, RemoteParticipant remoteParticipant) {
                Log.d("onParticipantDisco", "onParticipantDisconnected");
                removeRemoteParticipant(remoteParticipant);
            }

            @Override
            public void onRecordingStarted(Room room) {
            }

            @Override
            public void onRecordingStopped(Room room) {


            }
        };
    }

    public void connectToRoom(String roomName) {
        configureAudio(true);
        ConnectOptions.Builder connectOptionsBuilder = new ConnectOptions.Builder(token)
                .roomName(roomName);

        /*
         * Add local audio track to connect options to share with participants.
         */
        if (localAudioTrack != null) {
            connectOptionsBuilder
                    .audioTracks(Collections.singletonList(localAudioTrack));
        }

        /*
         * Add local video track to connect options to share with participants.
         */
        if (localVideoTrack != null) {
            connectOptionsBuilder.videoTracks(Collections.singletonList(localVideoTrack));
        }

        room = Video.connect(getActivity(), connectOptionsBuilder.build(), roomListener());

    }

    private void createAudioAndVideoTracks() {
        // Share your microphone
        localAudioTrack = LocalAudioTrack.create(getContext(), true, LOCAL_AUDIO_TRACK_NAME);

        // Share your camera
        cameraCapturerCompat = new CameraCapturerCompat(getContext(), getAvailableCameraSource());
        localVideoTrack = LocalVideoTrack.create(getContext(),
                true,
                cameraCapturerCompat.getVideoCapturer(),
                LOCAL_VIDEO_TRACK_NAME);
        vSelfCamera.setMirror(true);
        localVideoTrack.addRenderer(vSelfCamera);
        localVideoView = vSelfCamera;
    }

    private CameraCapturer.CameraSource getAvailableCameraSource() {
        return (CameraCapturer.isSourceAvailable(CameraCapturer.CameraSource.FRONT_CAMERA)) ?
                (CameraCapturer.CameraSource.FRONT_CAMERA) :
                (CameraCapturer.CameraSource.BACK_CAMERA);
    }

    private void resumeVideoTrack() {
        /*
         * If the local video track was released when the app was put in the background, recreate.
         */
        if (room != null && localVideoTrack == null) {
            localVideoTrack = LocalVideoTrack.create(getContext(),
                    true,
                    cameraCapturerCompat.getVideoCapturer(),
                    LOCAL_VIDEO_TRACK_NAME);
            localVideoTrack.addRenderer(localVideoView);
            // If connected to a Room then share the local video track.
            if (localParticipant != null) {
                localParticipant.publishTrack(localVideoTrack);
            }
        }
    }

    private void dismissVideoTrack() {
        /*
         * Release the local video track before going in the background. This ensures that the
         * camera can be used by other applications while this app is in the background.
         */
        if (localVideoTrack != null) {
            /*
             * If this local video track is being shared in a Room, unpublish from room before
             * releasing the video track. Participants will be notified that the track has been
             * unpublished.
             */
            if (localParticipant != null) {
                localParticipant.unpublishTrack(localVideoTrack);
            }

            localVideoTrack.release();
            localVideoTrack = null;
        }
    }

    private void destroyVideoTracks() {
        /*
         * Always disconnect from the room before leaving the Activity to
         * ensure any memory allocated to the Room resource is freed.
         */
        Log.d("destroyVideo", "destroyVideo");
        if (room != null && room.getState() != RoomState.DISCONNECTED) {
            room.disconnect();
        }

        /*
         * Release the local audio and video tracks ensuring any memory allocated to audio
         * or video is freed.
         */
        if (localAudioTrack != null) {
            localAudioTrack.release();
            localAudioTrack = null;
        }
        if (localVideoTrack != null) {
            localVideoTrack.release();
            localVideoTrack = null;
        }
    }

    private void configureAudio(boolean enable) {
        if (enable) {
            previousAudioMode = audioManager.getMode();
            // Request audio focus before making any device switch
            requestAudioFocus();
            /*
             * Use MODE_IN_COMMUNICATION as the default audio mode. It is required
             * to be in this mode when playout and/or recording starts for the best
             * possible VoIP performance. Some devices have difficulties with
             * speaker mode if this is not set.
             */
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            /*
             * Always disable microphone mute during a WebRTC call.
             */
            previousMicrophoneMute = audioManager.isMicrophoneMute();
            audioManager.setMicrophoneMute(false);
        } else {
            audioManager.setMode(previousAudioMode);
            audioManager.abandonAudioFocus(null);
            audioManager.setMicrophoneMute(previousMicrophoneMute);
        }
    }

    private void requestAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            AudioFocusRequest focusRequest =
                    new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                            .setAudioAttributes(playbackAttributes)
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(
                                    i -> {
                                    })
                            .build();
            audioManager.requestAudioFocus(focusRequest);
        } else {
            audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
    }

    /*
     * Called when remote participant joins the room
     */
    private void addRemoteParticipant(RemoteParticipant remoteParticipant) {
        Log.d("RoomListener", "Added remote participant " + remoteParticipant.getIdentity());
        /*
         * This app only displays video for one additional participant per Room
         */
        if (remoteParticipantIdentity != null) {
            //Se conecta el auditor
            //Toast.makeText(getActivity(), "Multiple participants are not currently support in this UI", Toast.LENGTH_LONG).show();
            return;
        }
        remoteParticipantIdentity = remoteParticipant.getIdentity();
        /*
         * Add remote participant renderer
         */
        if (remoteParticipant.getRemoteVideoTracks().size() > 0) {
            Log.d("RoomListener", "getRemoteVideoTracks");
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Only render video tracks that are subscribed to
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                Log.d("RoomListener", "addRemoteParticipantVideo");
                addRemoteParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack());
            }
        }

        /*
         * Start listening for participant events
         */
        remoteParticipant.setListener(new RemoteParticipantListener(this));
    }

    /*
     * Set primary view as renderer for participant video track
     */
    @Override
    public void addRemoteParticipantVideo(VideoTrack videoTrack) {
        Log.d("RoomListener", "Adding remote participant video");
        vFrame.setMirror(false);
        videoTrack.addRenderer(vFrame);
    }

    /*
     * Called when remote participant leaves the room
     */
    public void removeRemoteParticipant(RemoteParticipant remoteParticipant) {
        Log.d("RoomListener", "removeRemoteParticipant");
        if (!remoteParticipant.getIdentity().equals(remoteParticipantIdentity)) {
            return;
        }

        /*
         * Remove remote participant renderer
         */
        if (!remoteParticipant.getRemoteVideoTracks().isEmpty()) {
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Remove video only if subscribed to participant track
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                removeParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack());
            }
        }
        onCallEnded();
    }

    @Override
    public void removeParticipantVideo(VideoTrack videoTrack) {
        videoTrack.removeRenderer(vFrame);
    }


}
