package com.unidadcoronaria.doctorencasa.presenter;

import android.os.Handler;
import android.support.annotation.CallSuper;

import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateCallHistoryUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetQueueStatusUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.HangupUseCase;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class VideoCallPresenter extends BasePresenter<VideoCallView> {

    private GetAffiliateCallHistoryUseCase mGetAffiliateCallHistoryUseCase;
    private GetQueueStatusUseCase mGetQueueStatusUseCase;
    private CreateCallUseCase mCreateCallUseCase;
    final Handler handler = new Handler();

    @Inject
    public VideoCallPresenter(GetAffiliateCallHistoryUseCase mGetAffiliateCallHistoryUseCase,
                              GetQueueStatusUseCase mGetQueueStatusUseCase,
                              CreateCallUseCase mCreateCallUseCase){
        this.mGetAffiliateCallHistoryUseCase = mGetAffiliateCallHistoryUseCase;
        this.mGetQueueStatusUseCase = mGetQueueStatusUseCase;
        this.mCreateCallUseCase = mCreateCallUseCase;
    }


    @Override
    public void onResume() {
        super.onResume();
        view.onGetDataStart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 30 * 1000); // every 2 minutes
                getAffiliateHistory();
            }
        }, 0); // first run instantly

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
