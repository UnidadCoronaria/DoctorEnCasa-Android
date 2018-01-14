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

    private RankCallUseCase mRankCallUseCase;


    @Inject
    public NewCallPresenter(RankCallUseCase mRankCallUseCase) {
        this.mRankCallUseCase = mRankCallUseCase;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mRankCallUseCase.unsubscribe();
    }


    public void rank(String comment, int ranking){
        VideoCallDTO dto = new VideoCallDTO();
        dto.setComment(comment);
        dto.setScore(ranking);
        mRankCallUseCase.setData(dto);
        mRankCallUseCase.execute(o ->  view.onRankSuccess(), throwable -> view.onRankError());
    }

}
