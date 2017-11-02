package com.unidadcoronaria.doctorencasa.streaming;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

public interface CallManager <T,Y>{

    void initClient(String userName, SinchCallManager.StartFailedListener mListener);

    boolean isStarted();

    void resumeListeningIncomingCalls();

    void stopListeningIncomingCalls();

    void stopClient();

    T makeCall(String userId, Y callback);

    T getCall(String callId, Y callback);

}
