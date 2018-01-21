package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.domain.VideoCallStatus;
import com.unidadcoronaria.doctorencasa.presenter.VideoCallPresenter;
import com.unidadcoronaria.doctorencasa.util.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */
public class VideoCallFragment extends BaseFragment<VideoCallPresenter> implements VideoCallView {


    public static final String TAG = VideoCallFragment.class.getSimpleName();


    @BindView(R.id.fragment_video_call_delay)
    protected TextView vInQueueDelay;

    @BindView(R.id.fragment_video_call_container)
    protected View vContainer;

    @BindView(R.id.fragment_video_call_text)
    protected TextView vText;

    @BindView(R.id.fragment_video_call_button)
    protected Button vButton;



    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_video_call;
    }

    public static VideoCallFragment newInstance() {
        return new VideoCallFragment();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerVideoCallComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    @OnClick(R.id.fragment_video_call_button)
    protected void onButtonClick() {
        mPresenter.initCall();
    }

    @Override
    public void onGetAffiliateCallHistorySuccess(AffiliateCallHistory affiliateCallHistory) {
        vProgress.setVisibility(View.GONE);
        if (affiliateCallHistory != null && affiliateCallHistory.getLastVideocall() != null) {
            if(VideoCallStatus.FINALIZADA.equals(affiliateCallHistory.getLastVideocall().getStatus())
                        || VideoCallStatus.EXPIRADA.equals(affiliateCallHistory.getLastVideocall().getStatus())
                           || VideoCallStatus.CERRADA.equals(affiliateCallHistory.getLastVideocall().getStatus())) {
                vText.setText(getString(R.string.new_consult));
                vButton.setVisibility(View.VISIBLE);
            } else {
                vButton.setVisibility(View.GONE);
                if(VideoCallStatus.EN_COLA.equals(affiliateCallHistory.getLastVideocall().getStatus())){
                    //Si hay call activa y esta en cola
                    callInQueueView();
                } else {
                    if(VideoCallStatus.LISTA_ATENCION.equals(affiliateCallHistory.getLastVideocall().getStatus())){
                        // Si hay call activa y esta lista para atenderse
                        callReadyView();
                    } else {
                        if(VideoCallStatus.EN_PROGRESO.equals(affiliateCallHistory.getLastVideocall().getStatus())){
                            // Si hay call activa y esta en proceso
                            callInProgress();
                        }
                    }
                }
            }
        } else {
            vText.setText(R.string.history_error);
        }
        mPresenter.getQueueStatus();
    }

    private void callInQueueView() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vButton.setVisibility(View.GONE);
        vText.setText(Html.fromHtml(getString(R.string.already_in_queue)));
    }

    private void callReadyView() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vText.setText(getString(R.string.there_is_a_doctor_for_you));
    }

    private void callInProgress() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vText.setText(getString(R.string.there_is_a_call_in_progress));
    }

    @Override
    public void onGetAffiliateCallHistoryError() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vText.setText(R.string.history_error);
        vButton.setVisibility(View.GONE);
        mPresenter.getQueueStatus();
    }

    @Override
    public void onGetQueueStatusSuccess(Queue queue) {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vInQueueDelay.setText(Html.fromHtml(getString(R.string.in_queue_delay, DateUtil.getWaitingTime(getActivity(), queue.getWaitTime()))));
    }


    @Override
    public void onGetQueueStatusError() {
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetDataStart() {
        vProgress.setVisibility(View.VISIBLE);
        vContainer.setVisibility(View.GONE);
    }

    @Override
    public void onInitCallSuccess(VideoCall videoCall) {
        vProgress.setVisibility(View.GONE);
        callInQueueView();
    }


    @Override
    public void onInitCallError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error iniciando la consulta, por favor volvé a intentarlo.", Toast.LENGTH_LONG).show();
    }

}