package com.unidadcoronaria.doctorencasa.streaming;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.video.VideoCallListener;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

public interface CallManager <T>{

    SinchClient initClient(String userName, SinchCallManager.StartFailedListener mListener, CallClientListener mCallClientListener);

    boolean isStarted();

    void stopClient();

    T makeCall(String userId, VideoCallListener callback);

    T getCall(String callId, VideoCallListener callback);

}
