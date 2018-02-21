package com.unidadcoronaria.doctorencasa.presenter;

import android.os.Handler;
import android.support.annotation.CallSuper;
import android.util.Log;

import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateCallHistoryUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetQueueStatusUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetVideocallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.HangupUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.RankCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.StartCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.UpdateFCMTokenUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class VideoCallPresenter extends BasePresenter<VideoCallView> {

    private static final String TAG = "VideoCallPresenter";
    private GetAffiliateCallHistoryUseCase mGetAffiliateCallHistoryUseCase;
    private GetQueueStatusUseCase mGetQueueStatusUseCase;
    private CreateCallUseCase mCreateCallUseCase;
    private UpdateFCMTokenUseCase mUpdateFCMTokenUseCase;
    private RankCallUseCase mRankCallUseCase;

    final Handler handler = new Handler();
    private Boolean isListeningUpdates = Boolean.FALSE;

    @Inject
    public VideoCallPresenter(GetAffiliateCallHistoryUseCase mGetAffiliateCallHistoryUseCase,
                              GetQueueStatusUseCase mGetQueueStatusUseCase,
                              CreateCallUseCase mCreateCallUseCase,
                              UpdateFCMTokenUseCase mUpdateFCMTokenUseCase,
                              RankCallUseCase mRankCallUseCase) {
        this.mGetAffiliateCallHistoryUseCase = mGetAffiliateCallHistoryUseCase;
        this.mGetQueueStatusUseCase = mGetQueueStatusUseCase;
        this.mCreateCallUseCase = mCreateCallUseCase;
        this.mUpdateFCMTokenUseCase = mUpdateFCMTokenUseCase;
        this.mRankCallUseCase = mRankCallUseCase;
    }


    @Override
    public void onResume() {
        super.onResume();
        view.onGetDataStart();
        sendTokenToServer();
        listenQueueUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isListeningUpdates) {
            handler.removeCallbacksAndMessages(null);
            isListeningUpdates = false;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        mGetQueueStatusUseCase.unsubscribe();
        mCreateCallUseCase.unsubscribe();
        mGetAffiliateCallHistoryUseCase.unsubscribe();
        mUpdateFCMTokenUseCase.unsubscribe();
        mRankCallUseCase.unsubscribe();
    }

    public void listenQueueUpdates() {
        if (!isListeningUpdates) {
            isListeningUpdates = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 30 * 1000);
                    getAffiliateHistory();
                }
            }, 0); // first run instantly
        }
    }

    public void stopListeningQueueUpdates() {
        if (isListeningUpdates) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void sendTokenToServer() {
        if (!SessionUtil.getFCMToken().isEmpty() && !SessionUtil.getSavedFCMToken()) {
            mUpdateFCMTokenUseCase.setData(SessionUtil.getFCMToken());
            mUpdateFCMTokenUseCase.execute(() -> {
                        Log.i(TAG, "Success saving FCM Token");
                        SessionUtil.saveSavedFCMToken(true);
                    },
                    throwable -> Log.e(TAG, "Error saving FCM Token", throwable));
        }
    }

    public void getAffiliateHistory() {
        mGetAffiliateCallHistoryUseCase.execute(o -> {
            view.onGetAffiliateCallHistorySuccess((AffiliateCallHistory) o);
        }, throwable -> {
            handleException(throwable, () -> {
                view.onGetAffiliateCallHistoryError();
                return null;
            });
        });
    }

    public void getQueueStatus() {
        mGetQueueStatusUseCase.execute(o ->
                        view.onGetQueueStatusSuccess((Queue) o)
                , throwable ->
                        handleException(throwable, () -> {
                            view.onGetQueueStatusError();
                            return null;
                        }));
    }


    public void initCall() {
        view.onGetDataStart();
        mCreateCallUseCase.execute(o -> {
            view.onInitCallSuccess((VideoCall) o);
        }, throwable -> {
            handleException(throwable, () -> {
                view.onInitCallError();
                return null;
            });
        });
    }

    private void handleException(Throwable throwable, Callable func) {
        try {
            if (throwable instanceof HttpException) {
                // We had non-2XX http error
                HttpException e = (HttpException) throwable;
                if (e.response().code() == 408) {
                    SessionUtil.logout();
                    view.logout();
                    return;
                }
            }
            func.call();
        } catch (Exception e) {
            Log.e("VideoCallPresenter", e.getMessage());
        }
    }

}
