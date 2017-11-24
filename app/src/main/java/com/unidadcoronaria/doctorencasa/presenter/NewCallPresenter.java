package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.NewCallView;
import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.HangupUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.StartCallUseCase;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class NewCallPresenter extends BasePresenter<NewCallView> {

    private HangupUseCase mHangupUseCase;
    private StartCallUseCase mStartCallUseCase;

    @Inject
    public NewCallPresenter(HangupUseCase mHangupUseCase, StartCallUseCase mStartCallUseCase){
        this.mHangupUseCase = mHangupUseCase;
        this.mStartCallUseCase = mStartCallUseCase;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mHangupUseCase.unsubscribe();
        this.mStartCallUseCase.unsubscribe();
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
}
