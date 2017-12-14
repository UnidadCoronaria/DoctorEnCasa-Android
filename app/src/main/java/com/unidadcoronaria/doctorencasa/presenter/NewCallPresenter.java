package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.NewCallView;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.GetVideocallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.HangupUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.RankCallUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.StartCallUseCase;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class NewCallPresenter extends BasePresenter<NewCallView> {

    private HangupUseCase mHangupUseCase;
    private StartCallUseCase mStartCallUseCase;
    private RankCallUseCase mRankCallUseCase;
    private GetVideocallUseCase mGetVideocallUseCase;


    @Inject
    public NewCallPresenter(HangupUseCase mHangupUseCase, StartCallUseCase mStartCallUseCase,
                            RankCallUseCase mRankCallUseCase, GetVideocallUseCase mGetVideocallUseCase) {
        this.mHangupUseCase = mHangupUseCase;
        this.mStartCallUseCase = mStartCallUseCase;
        this.mRankCallUseCase = mRankCallUseCase;
        this.mGetVideocallUseCase = mGetVideocallUseCase;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mHangupUseCase.unsubscribe();
        this.mStartCallUseCase.unsubscribe();
        this.mRankCallUseCase.unsubscribe();
        this.mGetVideocallUseCase.unsubscribe();
    }

    public void start(VideoCall mCallDestination) {
        VideoCallDTO dto = new VideoCallDTO();
        dto.setVideocallId(mCallDestination.getId());
        mStartCallUseCase.setData(dto);
        //TODO devolver error especifico cuando la llamada expiró
        mStartCallUseCase.execute(o -> view.onStartSuccess((VideoCall) o),
                    throwable -> { if(true)view.onStartError(); else view.onCallUnavailableError(); });
    }

    public void hangout(VideoCall mCallDestination) {
        VideoCallDTO dto = new VideoCallDTO();
        dto.setVideocallId(mCallDestination.getId());
        mHangupUseCase.setData(dto);
        mHangupUseCase.execute(o -> view.onHangupSuccess((VideoCall) o), throwable -> view.onHangupError());
    }


    public void rank(int videocallId, String comment, int ranking){
        VideoCallDTO dto = new VideoCallDTO();
        dto.setVideocallId(videocallId);
        dto.setComment(comment);
        dto.setScore(ranking);
        mRankCallUseCase.setData(dto);
        mRankCallUseCase.execute(o ->  view.onRankSuccess(), throwable -> view.onRankError());
    }

    public void getVideocall(int mCallDestinationId) {
        mGetVideocallUseCase.setData(mCallDestinationId);
        mGetVideocallUseCase.execute(o ->
            view.onGetVideocallSuccess((VideoCall) o), throwable ->
            view.onGetVideocallError()
        );
    }
}
