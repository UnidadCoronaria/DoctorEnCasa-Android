package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.activity.NewCallActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.domain.VideoCallStatus;
import com.unidadcoronaria.doctorencasa.presenter.VideoCallPresenter;
import com.unidadcoronaria.doctorencasa.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class VideoCallFragment extends BaseFragment<VideoCallPresenter> implements VideoCallView {


    public static final String TAG = VideoCallFragment.class.getSimpleName();

    @BindView(R.id.fragment_video_call_history)
    protected View vCallHistory;

    @BindView(R.id.fragment_video_queue_status)
    protected View vQueueStatus;

    @BindView(R.id.fragment_video_call_in_queue)
    protected View vInQueue;

    @BindView(R.id.fragment_video_call_ready)
    protected View vCallReady;

    @BindView(R.id.fragment_video_call_in_progress)
    protected View vCallInProgress;

    @BindView(R.id.fragment_video_call_last_call_detail)
    protected TextView vLastCallDetail;

    @BindView(R.id.fragment_video_calls_quantity)
    protected TextView vCallsQuantity;

    @BindView(R.id.fragment_video_call_delay)
    protected TextView vDelay;

    @BindView(R.id.fragment_video_call_doctors_attending)
    protected TextView vDoctorsAttending;

    @BindView(R.id.fragment_video_call_in_queue_delay)
    protected TextView vInQueueDelay;

    @BindViews({R.id.fragment_video_call_history, R.id.fragment_video_queue_status, R.id.fragment_video_call_in_queue,
            R.id.fragment_video_call_ready, R.id.fragment_video_call_in_progress})
    protected List<View> mAllViews;

    private VideoCall mCurrentVideoCall;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_video_call;
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

    public static VideoCallFragment newInstance() {
        return new VideoCallFragment();
    }

    @OnClick(R.id.fragment_video_call_init_call)
    protected void onInitCallClick() {
        mPresenter.initCall();
    }

    @OnClick(R.id.fragment_video_call_button_ready)
    protected void onMakeCallClick() {
        if(mCurrentVideoCall != null && VideoCallStatus.LISTA_ATENCION.equals(mCurrentVideoCall.getStatus())
                && mCurrentVideoCall.getDoctor() != null){
            startActivity(NewCallActivity.newInstance(getActivity(), mCurrentVideoCall));
        }
    }

    @Override
    public void onGetAffiliateCallHistorySuccess(AffiliateCallHistory affiliateCallHistory) {
        vProgress.setVisibility(View.GONE);
        if (affiliateCallHistory != null && affiliateCallHistory.getLastVideocall() != null) {
            showHistoryView(affiliateCallHistory);
            if(VideoCallStatus.FINALIZADA.equals(affiliateCallHistory.getLastVideocall().getStatus())
                        || VideoCallStatus.EXPIRADA.equals(affiliateCallHistory.getLastVideocall().getStatus())) {
                //Si no hay call activa
                mPresenter.getQueueStatus();
            } else {
                mCurrentVideoCall = affiliateCallHistory.getLastVideocall();
                if(VideoCallStatus.EN_COLA.equals(affiliateCallHistory.getLastVideocall().getStatus())){
                    //Si hay call activa y esta en cola
                    callInQueueView(affiliateCallHistory.getLastVideocall());
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
            vCallHistory.setVisibility(View.GONE);
            vLastCallDetail.setText(R.string.history_error);
            mPresenter.getQueueStatus();
        }
    }

    private void callInQueueView(VideoCall videoCall) {
        vQueueStatus.setVisibility(View.GONE);
        vCallReady.setVisibility(View.GONE);
        vCallInProgress.setVisibility(View.GONE);
        vInQueue.setVisibility(View.VISIBLE);
        if(videoCall != null) {
            vInQueueDelay.setText(getString(R.string.in_queue_delay, DateUtil.getWaitingTime(getActivity(), videoCall.getWaitTime())));
        }

    }

    private void callReadyView() {
        vInQueue.setVisibility(View.GONE);
        vQueueStatus.setVisibility(View.GONE);
        vCallInProgress.setVisibility(View.GONE);
        vCallReady.setVisibility(View.VISIBLE);
    }

    private void callInProgress() {
        vInQueue.setVisibility(View.GONE);
        vQueueStatus.setVisibility(View.GONE);
        vCallReady.setVisibility(View.GONE);
        vCallInProgress.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "Ya estas en una llamada activa", Toast.LENGTH_LONG).show();
    }

    private void showHistoryView(AffiliateCallHistory affiliateCallHistory) {
        vCallHistory.setVisibility(View.VISIBLE);
        Date convertedDate = new Date();
        convertedDate.setTime(affiliateCallHistory.getLastVideocall().getDate());
        String doctorInformation = "";
        if(affiliateCallHistory.getLastVideocall().getDoctor() != null){
            doctorInformation =  affiliateCallHistory.getLastVideocall().getDoctor().getFirstName()+ " "+
                    affiliateCallHistory.getLastVideocall().getDoctor().getLastName();
        }
        vLastCallDetail.setText(getString(R.string.last_call_detail,
                DateUtil.getConvertedDayString(convertedDate),
                doctorInformation,
                DateUtil.getWaitingTime(getActivity(), affiliateCallHistory.getLastVideocall().getEndDate() -  affiliateCallHistory.getLastVideocall().getStartDate())));
        if (affiliateCallHistory.getQueries() > 1) {
            vCallsQuantity.setText(getString(R.string.calls_quantity, affiliateCallHistory.getQueries()));
        } else {
            vCallsQuantity.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetAffiliateCallHistoryError() {
        vProgress.setVisibility(View.GONE);
        vCallHistory.setVisibility(View.GONE);
        vLastCallDetail.setText(R.string.history_error);
        mPresenter.getQueueStatus();
    }

    @Override
    public void onGetQueueStatusSuccess(Queue queue) {
        vProgress.setVisibility(View.GONE);
        vInQueue.setVisibility(View.GONE);
        vCallReady.setVisibility(View.GONE);
        if (queue != null) {
            vQueueStatus.setVisibility(View.VISIBLE);
            if(queue.getWaitTime() <= TimeUnit.MILLISECONDS.toMinutes(1)){
                vDelay.setText(R.string.available_doctor);
                vDoctorsAttending.setVisibility(View.GONE);
            } else {
                vDelay.setText(getString(R.string.queue_delay, DateUtil.getWaitingTime(getActivity(), queue.getWaitTime())));
                vDoctorsAttending.setVisibility(View.VISIBLE);
                vDoctorsAttending.setText(getResources().getQuantityString(R.plurals.doctor_attending, queue.getDoctorsAttending(), queue.getDoctorsAttending()));
            }

        } else {
            vQueueStatus.setVisibility(View.GONE);
        }
    }


    @Override
    public void onGetQueueStatusError() {
        vProgress.setVisibility(View.GONE);
        vQueueStatus.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataStart() {
        vProgress.setVisibility(View.VISIBLE);
        ButterKnife.apply(mAllViews, (ButterKnife.Action<View>) (view, index) -> view.setVisibility(View.GONE));
    }

    @Override
    public void onInitCallSuccess(VideoCall videoCall) {
        vProgress.setVisibility(View.GONE);
        callInQueueView(videoCall);
    }


    @Override
    public void onInitCallError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error iniciando la consulta, por favor volvé a intentarlo.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitCallStart() {
        vProgress.setVisibility(View.VISIBLE);
    }
}