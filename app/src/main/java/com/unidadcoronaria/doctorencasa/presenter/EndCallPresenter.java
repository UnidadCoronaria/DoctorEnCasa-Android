package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.EndCallView;
import com.unidadcoronaria.doctorencasa.NewCallView;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.RankCallUseCase;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class EndCallPresenter extends BasePresenter<EndCallView> {

    private final RankCallUseCase mRankCallUseCase;

    @Inject
    public EndCallPresenter(RankCallUseCase mRankCallUseCase){
        this.mRankCallUseCase = mRankCallUseCase;
    }

    public void rank(int videocallId, String comment, int ranking){
        VideoCallDTO dto = new VideoCallDTO();
        dto.setVideocallId(videocallId);
        dto.setComment(comment);
        dto.setRanking(ranking);
        mRankCallUseCase.setData(dto);
        mRankCallUseCase.execute(o -> {
            view.onRankSuccess();
        }, throwable -> {
            view.onRankError();
        });
    }

    @Override
    public void onStop(){
        super.onStop();
        mRankCallUseCase.unsubscribe();
    }


}
