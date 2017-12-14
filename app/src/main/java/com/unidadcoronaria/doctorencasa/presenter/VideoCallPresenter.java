package com.unidadcoronaria.doctorencasa.presenter;

import android.os.Handler;
import android.support.annotation.CallSuper;
import android.util.Log;

import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateCallHistoryUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetQueueStatusUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.HangupUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.UpdateFCMTokenUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class VideoCallPresenter extends BasePresenter<VideoCallView> {

    private static final String TAG = "VideoCallPresenter";
    private GetAffiliateCallHistoryUseCase mGetAffiliateCallHistoryUseCase;
    private GetQueueStatusUseCase mGetQueueStatusUseCase;
    private CreateCallUseCase mCreateCallUseCase;
    private UpdateFCMTokenUseCase mUpdateFCMTokenUseCase;
    final Handler handler = new Handler();

    @Inject
    public VideoCallPresenter(GetAffiliateCallHistoryUseCase mGetAffiliateCallHistoryUseCase,
                              GetQueueStatusUseCase mGetQueueStatusUseCase,
                              CreateCallUseCase mCreateCallUseCase,
                              UpdateFCMTokenUseCase mUpdateFCMTokenUseCase){
        this.mGetAffiliateCallHistoryUseCase = mGetAffiliateCallHistoryUseCase;
        this.mGetQueueStatusUseCase = mGetQueueStatusUseCase;
        this.mCreateCallUseCase = mCreateCallUseCase;
        this.mUpdateFCMTokenUseCase = mUpdateFCMTokenUseCase;
    }


    @Override
    public void onResume() {
        super.onResume();
        view.onGetDataStart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 30 * 1000);
                getAffiliateHistory();
            }
        }, 0); // first run instantly
        sendTokenToServer();
    }

    private void sendTokenToServer() {
        if(!SessionUtil.getFCMToken().isEmpty() && !SessionUtil.getSavedFCMToken()){
            mUpdateFCMTokenUseCase.setData(SessionUtil.getFCMToken());
            mUpdateFCMTokenUseCase.execute(() -> {
                Log.i(TAG, "Success saving FCM Token");
                SessionUtil.saveSavedFCMToken(true);},
                    throwable -> Log.e(TAG, "Error saving FCM Token", throwable));
        }
    }

    private void getAffiliateHistory() {
        mGetAffiliateCallHistoryUseCase.execute(o -> {
            view.onGetAffiliateCallHistorySuccess((AffiliateCallHistory) o);
        }, throwable -> {
            view.onGetAffiliateCallHistoryError();
        });
    }

    public void getQueueStatus(){
        mGetQueueStatusUseCase.execute(o ->
            view.onGetQueueStatusSuccess((Queue)o)
        , throwable ->
            view.onGetQueueStatusError());
    }

    @Override
    public void onStop() {
        super.onStop();
        mGetQueueStatusUseCase.unsubscribe();
        mCreateCallUseCase.unsubscribe();
        mGetAffiliateCallHistoryUseCase.unsubscribe();
        handler.removeCallbacksAndMessages(null);
    }

    public void initCall() {
        view.onInitCallStart();
        mCreateCallUseCase.execute(o -> {
            view.onInitCallSuccess((VideoCall)o);
        }, throwable -> {
            view.onInitCallError();
        });
    }
}
