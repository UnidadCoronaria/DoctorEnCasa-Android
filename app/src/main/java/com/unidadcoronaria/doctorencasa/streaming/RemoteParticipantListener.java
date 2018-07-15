package com.unidadcoronaria.doctorencasa.streaming;

import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteAudioTrackPublication;
import com.twilio.video.RemoteDataTrack;
import com.twilio.video.RemoteDataTrackPublication;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.RemoteVideoTrackPublication;
import com.twilio.video.TwilioException;
import com.unidadcoronaria.doctorencasa.NewCallView;

public class RemoteParticipantListener implements RemoteParticipant.Listener {

    private NewCallView mCallback;

    public RemoteParticipantListener(NewCallView callback) {
        this.mCallback = callback;
    }

    @Override
    public void onAudioTrackPublished(RemoteParticipant remoteParticipant,
                                      RemoteAudioTrackPublication remoteAudioTrackPublication) {

    }

    @Override
    public void onAudioTrackUnpublished(RemoteParticipant remoteParticipant,
                                        RemoteAudioTrackPublication remoteAudioTrackPublication) {
    }

    @Override
    public void onDataTrackPublished(RemoteParticipant remoteParticipant,
                                     RemoteDataTrackPublication remoteDataTrackPublication) {
    }

    @Override
    public void onDataTrackUnpublished(RemoteParticipant remoteParticipant,
                                       RemoteDataTrackPublication remoteDataTrackPublication) {
    }

    @Override
    public void onVideoTrackPublished(RemoteParticipant remoteParticipant,
                                      RemoteVideoTrackPublication remoteVideoTrackPublication) {
    }

    @Override
    public void onVideoTrackUnpublished(RemoteParticipant remoteParticipant,
                                        RemoteVideoTrackPublication remoteVideoTrackPublication) {
    }

    @Override
    public void onAudioTrackSubscribed(RemoteParticipant remoteParticipant,
                                       RemoteAudioTrackPublication remoteAudioTrackPublication,
                                       RemoteAudioTrack remoteAudioTrack) {
    }

    @Override
    public void onAudioTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                         RemoteAudioTrackPublication remoteAudioTrackPublication,
                                         RemoteAudioTrack remoteAudioTrack) {
    }

    @Override
    public void onAudioTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                               RemoteAudioTrackPublication remoteAudioTrackPublication,
                                               TwilioException twilioException) {
    }

    @Override
    public void onDataTrackSubscribed(RemoteParticipant remoteParticipant,
                                      RemoteDataTrackPublication remoteDataTrackPublication,
                                      RemoteDataTrack remoteDataTrack) {
    }

    @Override
    public void onDataTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                        RemoteDataTrackPublication remoteDataTrackPublication,
                                        RemoteDataTrack remoteDataTrack) {
    }

    @Override
    public void onDataTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                              RemoteDataTrackPublication remoteDataTrackPublication,
                                              TwilioException twilioException) {
    }

    @Override
    public void onVideoTrackSubscribed(RemoteParticipant remoteParticipant,
                                       RemoteVideoTrackPublication remoteVideoTrackPublication,
                                       RemoteVideoTrack remoteVideoTrack) {
        mCallback.addRemoteParticipantVideo(remoteVideoTrack);
    }

    @Override
    public void onVideoTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                         RemoteVideoTrackPublication remoteVideoTrackPublication,
                                         RemoteVideoTrack remoteVideoTrack) {
        mCallback.removeParticipantVideo(remoteVideoTrack);
    }

    @Override
    public void onVideoTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                               RemoteVideoTrackPublication remoteVideoTrackPublication,
                                               TwilioException twilioException) {
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
}
