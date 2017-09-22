package com.unidadcoronaria.doctorencasa.communication;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.ViewGroup;

import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.video.VideoCallListener;

/**
 * Created by AGUSTIN.BALA on 7/30/2017.
 */

public interface VideoCallService {

    void startService(Context context, SinchClientListener mSinchClientListener);

    void stopService();

    void initCall(String userID, VideoCallListener mVideoCallListener);

    void addVideoCallInterface(ViewGroup mLocalView, ViewGroup mRemoteView);

    void removeVideoCallInterface(ViewGroup mLocalView, ViewGroup mRemoteView);
}
