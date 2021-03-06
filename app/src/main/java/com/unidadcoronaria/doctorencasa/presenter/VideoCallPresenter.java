package com.unidadcoronaria.doctorencasa.presenter;

import android.os.Handler;
import android.util.Log;

import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.CancelCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateCallHistoryUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetQueueStatusUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.RankCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.UpdateFCMTokenUseCase;
import com.unidadcoronaria.doctorencasa.util.ErrorUtil;
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
    private RankCallUseCase mRankCallUseCase;
    private CancelCallUseCase mCancelCallUseCase;

    final Handler handler = new Handler();
    private Boolean isListeningUpdates = Boolean.FALSE;

    @Inject
    public VideoCallPresenter(GetAffiliateCallHistoryUseCase mGetAffiliateCallHistoryUseCase,
                              GetQueueStatusUseCase mGetQueueStatusUseCase,
                              CreateCallUseCase mCreateCallUseCase,
                              UpdateFCMTokenUseCase mUpdateFCMTokenUseCase,
                              RankCallUseCase mRankCallUseCase,
                              CancelCallUseCase cancelCallUseCase) {
        this.mGetAffiliateCallHistoryUseCase = mGetAffiliateCallHistoryUseCase;
        this.mGetQueueStatusUseCase = mGetQueueStatusUseCase;
        this.mCreateCallUseCase = mCreateCallUseCase;
        this.mUpdateFCMTokenUseCase = mUpdateFCMTokenUseCase;
        this.mRankCallUseCase = mRankCallUseCase;
        this.mCancelCallUseCase = cancelCallUseCase;
    }


    @Override
    public void onResume() {
        super.onResume();
        view.onGetDataStart();
        handler.removeCallbacksAndMessages(null);
        sendTokenToServer();
        listenQueueUpdates();
        getAffiliateHistory();
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
        mCancelCallUseCase.unsubscribe();
    }

    public void listenQueueUpdates() {
        if (!isListeningUpdates) {
            isListeningUpdates = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getAffiliateHistory();
                    handler.postDelayed(this, 30 * 1000);
                }
            }, 1000); // first run instantly
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
            checkTokenExpired(throwable, () -> {
                view.onGetAffiliateCallHistoryError();
                return null;
            });
        });
    }

    public void getQueueStatus() {
        mGetQueueStatusUseCase.execute(o ->
                        view.onGetQueueStatusSuccess((Queue) o)
                , throwable ->
                        checkTokenExpired(throwable, () -> {
                            view.onGetQueueStatusError();
                            return null;
                        }));
    }


    public void initCall() {
        view.onGetDataStart();
        mCreateCallUseCase.execute(o -> {
            view.onInitCallSuccess((VideoCall) o);
        }, throwable -> {
            checkTokenExpired(throwable, () -> {
                view.onInitCallError(ErrorUtil.getError(throwable));
                return null;
            });
        });
    }

    public void cancelCall(Integer mVideocallId) {
        view.onGetDataStart();
        VideoCallDTO dto = new VideoCallDTO();
        dto.setVideocallId(mVideocallId);
        mCancelCallUseCase.setCurrentVideocall(dto);
        mCancelCallUseCase.execute(o ->
                        view.onCancelSuccess()
                , throwable ->
                    view.onCancelError()
                );
    }
}
