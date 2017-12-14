package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.VideoCall;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface NewCallView extends BaseView {

    void onHangupSuccess(VideoCall videoCall);

    void onHangupError();

    void onStartSuccess(VideoCall videoCall);

    void onStartError();

    void onCallUnavailableError();

    void onRankSuccess();

    void onRankError();

    void onGetVideocallSuccess(VideoCall videoCall);

    void onGetVideocallError();
}
