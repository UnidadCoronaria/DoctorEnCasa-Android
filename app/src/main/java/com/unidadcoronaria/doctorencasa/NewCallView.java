package com.unidadcoronaria.doctorencasa;

import com.twilio.video.VideoTrack;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface NewCallView extends BaseView {

    void onRankSuccess(int ranking);

    void onRankError();

    void addRemoteParticipantVideo(VideoTrack videoTrack);

    void removeParticipantVideo(VideoTrack videoTrack);

}
