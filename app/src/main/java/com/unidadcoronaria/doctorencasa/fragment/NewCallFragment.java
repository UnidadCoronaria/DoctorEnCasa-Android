package com.unidadcoronaria.doctorencasa.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twilio.video.CameraCapturer;
import com.twilio.video.ConnectOptions;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteAudioTrackPublication;
import com.twilio.video.RemoteDataTrack;
import com.twilio.video.RemoteDataTrackPublication;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrack;
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
import com.unidadcoronaria.doctorencasa.util.CameraCapturerCompat;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Arrays;
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

    @BindView(R.id.rl_error_starting)
    protected View vErrorStartingContainer;

    @BindView(R.id.fragment_new_video_call_calling)
    protected View vStartingContainer;

    @BindView(R.id.fragment_new_video_call_incoming)
    protected View vIncomingContainer;

    @BindView(R.id.fragment_video_call_hangup_button)
    protected View vHangoutButton;

    @BindView(R.id.fragment_video_call_switch_video)
    protected View vStopVideoButton;

    @BindView(R.id.fragment_video_call_mute)
    protected View vMuteButton;

    @BindView(R.id.fragment_new_call_incoming_effect)
    protected AVLoadingIndicatorView vIncomingCallEffect;

    @BindView(R.id.fragment_video_call_delay)
    protected TextView vCallRemainingTime;

    @BindView(R.id.fragment_new_video_call_rank_background)
    protected View vRankBackground;

    private RankDialog mRankDialog = new RankDialog();
    private AudioPlayer mAudioPlayer;
    private AudioManager audioManager;
    private Handler mRemainingMinutesHandler = new Handler();
    private int mRemainingMinutes = 3;


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
        audioManager = (AudioManager) App.getInstance().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
        vMuteButton.setSelected(!audioManager.isMicrophoneMute());
        if(SessionUtil.isCallInProgress()){
            roomName = SessionUtil.getRoomName();
            token = SessionUtil.getTwilioToken();
        }
        createAudioAndVideoTracks();
        NewCallFragmentPermissionsDispatcher.acceptCallWithPermissionCheck(this);
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
        SessionUtil.finishCall();
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
        vErrorStartingContainer.setVisibility(View.GONE);
        mAudioPlayer = new AudioPlayer(getActivity());
        mAudioPlayer.playRingtone();
        vIncomingCallEffect.show();
        onCallProgressing();
    }


    @OnClick(R.id.fragment_video_call_hangup_button)
    protected void onHangoutClick() {
        if (room != null) {
            room.disconnect();
        }
    }

    @OnClick(R.id.fragment_video_call_answer_button)
    protected void onAnswerClick() {
        try {
            mAudioPlayer.stopRingtone();
            vMuteButton.setSelected(audioManager.isMicrophoneMute());
            connectToRoom(roomName);
            SessionUtil.finishCall();
        } catch (Exception e) {
            vProgress.setVisibility(View.GONE);
            vStartingContainer.setVisibility(View.GONE);
            vErrorContainer.setVisibility(View.GONE);
            vContainer.setVisibility(View.GONE);
            vIncomingContainer.setVisibility(View.GONE);
            vRankBackground.setVisibility(View.GONE);
            vIncomingCallEffect.hide();
            vErrorStartingContainer.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.fragment_video_call_decline_button)
    protected void declineClicked() {
        SessionUtil.finishCall();
        mAudioPlayer.stopRingtone();
        getActivity().finish();
    }

    @OnClick(R.id.fragment_video_call_switch_video)
    protected void onSwitchCamera() {
        if (cameraCapturerCompat != null) {
            CameraCapturer.CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
            cameraCapturerCompat.switchCamera();
            vSelfCamera.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
        }
    }

    protected void onStopVideo(){
        if (localVideoTrack != null) {
            boolean enable = !localVideoTrack.isEnabled();
            localVideoTrack.enable(enable);
            //change button status
        }
    }

    @OnClick(R.id.fragment_video_call_mute)
    protected void onMuteClick() {
        if (localAudioTrack != null) {
            boolean enable = !localAudioTrack.isEnabled();
            localAudioTrack.enable(enable);
            vMuteButton.setSelected(!enable);
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
                .setPositiveButton(getString(R.string.yes), (dialog, button) -> request.proceed())
                .setNegativeButton(getString(R.string.no), (dialog, button) -> request.cancel())
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
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));
            });
            alertDialog.show();
        }

    }

    private void callToCentral() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String tel = "tel:";
        if (SessionUtil.getProvider() == 1) {
            tel += "1142577777";
        } else {
            if (SessionUtil.getProvider() == 2) {
                tel += "1157265166";
            } else if (SessionUtil.getProvider() == 3) {
                tel += "1147330043";
            }
        }
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
        Log.d(TAG, "Call ended");
        SessionUtil.finishCall();
        mAudioPlayer.stopRingtone();
        vContainer.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vIncomingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vHangoutButton.setVisibility(View.VISIBLE);
        mRemainingMinutesHandler.removeCallbacksAndMessages(null);
        showRankDialog();
    }

    private void onCallEstablished() {
        Log.d(TAG, "Call established");
        SessionUtil.finishCall();
        vHangoutButton.setVisibility(View.VISIBLE);
        vStartingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
        vIncomingContainer.setVisibility(View.GONE);
        vRankBackground.setVisibility(View.GONE);
        vStopVideoButton.setSelected(true);
        vMuteButton.setSelected(true);
        mRemainingMinutesHandler.removeCallbacksAndMessages(null);
    }

    public void onCallProgressing() {
        Log.d(TAG, "Call progressing");
        //vCallRemainingTime.setText(getString(R.string.remaining_time_to_answer, "3 minutos"));
        mRemainingMinutesHandler.postDelayed(() -> {
           if(SessionUtil.isCallInProgress()){
               SessionUtil.finishCall();
               Toast.makeText(App.getInstance(), getString(R.string.no_response), Toast.LENGTH_LONG).show();
               getActivity().finish();
           }
        }, 60 * 1000);
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
    private boolean disconnectedFromOnDestroy;
    private int previousAudioMode;
    private boolean previousMicrophoneMute;
    private String remoteParticipantIdentity;


    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {
                localParticipant = room.getLocalParticipant();

                for (RemoteParticipant remoteParticipant : room.getRemoteParticipants()) {
                    addRemoteParticipant(remoteParticipant);
                    break;
                }
                onCallEstablished();
            }

            @Override
            public void onConnectFailure(Room room, TwilioException e) {
                configureAudio(false);
                onCallEnded();
            }

            @Override
            public void onDisconnected(Room room, TwilioException e) {
                Log.e("RoomListener", "Disconnected call");
                localParticipant = null;
                NewCallFragment.this.room = null;
                // Only reinitialize the UI if disconnect was not called from onDestroy()
                if (!disconnectedFromOnDestroy) {
                    configureAudio(false);
                    onCallEnded();
                }
            }

            @Override
            public void onParticipantConnected(Room room, RemoteParticipant remoteParticipant) {
                addRemoteParticipant(remoteParticipant);

            }

            @Override
            public void onParticipantDisconnected(Room room, RemoteParticipant remoteParticipant) {
                removeRemoteParticipant(remoteParticipant);
            }

            @Override
            public void onRecordingStarted(Room room) {
                /*
                 * Indicates when media shared to a Room is being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.d(TAG, "onRecordingStarted");
            }

            @Override
            public void onRecordingStopped(Room room) {
                /*
                 * Indicates when media shared to a Room is no longer being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.d(TAG, "onRecordingStopped");
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
        if (localVideoTrack == null) {
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

    private void dismissVideoTrack(){
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

    private void destroyVideoTracks(){
        /*
         * Always disconnect from the room before leaving the Activity to
         * ensure any memory allocated to the Room resource is freed.
         */
        if (room != null && room.getState() != RoomState.DISCONNECTED) {
            room.disconnect();
            disconnectedFromOnDestroy = true;
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
                                    i -> { })
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
        Log.e("RoomListener", "Added remote participant "+ remoteParticipant.getIdentity());
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
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Only render video tracks that are subscribed to
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                addRemoteParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack());
            }
        }

        /*
         * Start listening for participant events
         */
        remoteParticipant.setListener(remoteParticipantListener());
    }

    /*
     * Set primary view as renderer for participant video track
     */
    private void addRemoteParticipantVideo(VideoTrack videoTrack) {
        Log.e("RoomListener", "Adding remote participant video");
        vFrame.setMirror(false);
        videoTrack.addRenderer(vFrame);
    }

    /*
     * Called when remote participant leaves the room
     */
    private void removeRemoteParticipant(RemoteParticipant remoteParticipant) {
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
        if(room.getRemoteParticipants().size() == 0){
            onCallEnded();
        }
    }

    private void removeParticipantVideo(VideoTrack videoTrack) {
        videoTrack.removeRenderer(vFrame);
    }

    private RemoteParticipant.Listener remoteParticipantListener() {
        return new RemoteParticipant.Listener() {
            @Override
            public void onAudioTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG, String.format("onAudioTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));
            }

            @Override
            public void onAudioTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG, String.format("onAudioTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));
            }

            @Override
            public void onDataTrackPublished(RemoteParticipant remoteParticipant,
                                             RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG, String.format("onDataTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onDataTrackUnpublished(RemoteParticipant remoteParticipant,
                                               RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG, String.format("onDataTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onVideoTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG, String.format("onVideoTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));
            }

            @Override
            public void onVideoTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG, String.format("onVideoTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));
            }

            @Override
            public void onAudioTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteAudioTrackPublication remoteAudioTrackPublication,
                                               RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG, String.format("onAudioTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));
            }

            @Override
            public void onAudioTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                 RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG, String.format("onAudioTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));
            }

            @Override
            public void onAudioTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG, String.format("onAudioTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onDataTrackSubscribed(RemoteParticipant remoteParticipant,
                                              RemoteDataTrackPublication remoteDataTrackPublication,
                                              RemoteDataTrack remoteDataTrack) {
                Log.i(TAG, String.format("onDataTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                RemoteDataTrackPublication remoteDataTrackPublication,
                                                RemoteDataTrack remoteDataTrack) {
                Log.i(TAG, String.format("onDataTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                      RemoteDataTrackPublication remoteDataTrackPublication,
                                                      TwilioException twilioException) {
                Log.i(TAG, String.format("onDataTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onVideoTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteVideoTrackPublication remoteVideoTrackPublication,
                                               RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG, String.format("onVideoTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));
                addRemoteParticipantVideo(remoteVideoTrack);
            }

            @Override
            public void onVideoTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                 RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG, String.format("onVideoTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));
                removeParticipantVideo(remoteVideoTrack);
            }

            @Override
            public void onVideoTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG, String.format("onVideoTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onAudioTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onAudioTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onVideoTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }

            @Override
            public void onVideoTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }
        };
    }

}
