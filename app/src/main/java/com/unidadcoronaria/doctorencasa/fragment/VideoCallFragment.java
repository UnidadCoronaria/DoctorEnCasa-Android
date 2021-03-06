package com.unidadcoronaria.doctorencasa.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;
import com.unidadcoronaria.doctorencasa.presenter.VideoCallPresenter;
import com.unidadcoronaria.doctorencasa.util.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */
public class VideoCallFragment extends BaseFragment<VideoCallPresenter> implements VideoCallView {


    public static final String TAG = VideoCallFragment.class.getSimpleName();
    public static final int CALL_RESULT = 111;


    @BindView(R.id.fragment_video_call_delay)
    protected TextView vInQueueDelay;

    @BindView(R.id.fragment_video_call_container)
    protected View vContainer;

    @BindView(R.id.fragment_video_call_text)
    protected TextView vText;

    @BindView(R.id.fragment_video_call_button)
    protected Button vButton;

    @BindView(R.id.fragment_video_cancel_button)
    protected Button vCancelButton;

    @BindView(R.id.fragment_video_call_refresh)
    SwipeRefreshLayout vRefresh;

    @BindView(R.id.fragment_video_call_image)
    ImageView vMainImage;

    @BindView(R.id.fragment_video_call_error_text)
    TextView vErrorText;

    @BindView(R.id.fragment_video_call_separator)
    View vSeparator;

    private Integer mVideocallId;

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
        vRefresh.setOnRefreshListener(() -> {
            vRefresh.setRefreshing(true);
            if(vProgress.getVisibility() == View.GONE){
                vProgress.setVisibility(View.VISIBLE);
            }
            vContainer.setVisibility(View.GONE);
            mPresenter.getAffiliateHistory();
        });
        vRefresh.setColorSchemeResources(R.color.colorAccent);
    }

    @OnClick(R.id.fragment_video_call_button)
    protected void onButtonClick() {
        mPresenter.initCall();
    }

    @OnClick(R.id.fragment_video_cancel_button)
    protected void onCancelButtonClick() {
        AlertDialog.Builder dialogConfirmBuilder = new AlertDialog.Builder(getActivity()).setMessage(R.string.cancel_call_confirm)
                    .setPositiveButton(R.string.yes,
                (dialog, which) -> {
                    mPresenter.cancelCall(mVideocallId);
                    dialog.dismiss();
                }).setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss()).setCancelable(false);

        AlertDialog alertDialog = dialogConfirmBuilder.create();
        alertDialog.setOnShowListener(dialog ->  {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
        });
        alertDialog.show();
    }

    @Override
    public void onGetAffiliateCallHistorySuccess(AffiliateCallHistory affiliateCallHistory) {
        vProgress.setVisibility(View.GONE);
        vMainImage.setImageResource(R.drawable.ic_doctor_main);
        vText.setVisibility(View.VISIBLE);
        vInQueueDelay.setVisibility(View.VISIBLE);
        vSeparator.setVisibility(View.VISIBLE);
        vErrorText.setVisibility(View.GONE);
        if (affiliateCallHistory != null && affiliateCallHistory.getLastVideocall() != null) {
            if(VideoCallStatus.FINALIZADA.equals(affiliateCallHistory.getLastVideocall().getStatus())
                        || VideoCallStatus.EXPIRADA.equals(affiliateCallHistory.getLastVideocall().getStatus())
                           || VideoCallStatus.CERRADA.equals(affiliateCallHistory.getLastVideocall().getStatus())
                    || VideoCallStatus.CANCELADA.equals(affiliateCallHistory.getLastVideocall().getStatus())) {
                vText.setText(getString(R.string.new_consult));
                vButton.setVisibility(View.VISIBLE);
                vCancelButton.setVisibility(View.GONE);
                mVideocallId = null;
            } else {
                vButton.setVisibility(View.GONE);
                mVideocallId = affiliateCallHistory.getLastVideocall().getId();
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
                    vText.setText(getString(R.string.there_is_a_doctor_for_you));
                }
            }
        } else {
            if(affiliateCallHistory != null){
                vText.setText(getString(R.string.new_consult));
                vButton.setVisibility(View.VISIBLE);
            } else {
                vText.setText(R.string.history_error);
            }
        }
        mPresenter.getQueueStatus();
        if (vRefresh.isRefreshing()){
            vRefresh.setRefreshing(false);
        }
    }

    private void callInQueueView() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vButton.setVisibility(View.GONE);
        vCancelButton.setVisibility(View.VISIBLE);
        vText.setText(Html.fromHtml(getString(R.string.already_in_queue)));
    }

    private void callReadyView() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vCancelButton.setVisibility(View.GONE);
        vText.setText(getString(R.string.there_is_a_doctor_for_you));
    }

    private void callInProgress() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vCancelButton.setVisibility(View.GONE);
        vText.setText(getString(R.string.there_is_a_call_in_progress));
    }

    @Override
    public void onGetAffiliateCallHistoryError() {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vText.setVisibility(View.GONE);
        vErrorText.setVisibility(View.VISIBLE);
        vSeparator.setVisibility(View.GONE);
        vButton.setVisibility(View.GONE);
        vCancelButton.setVisibility(View.GONE);
        vInQueueDelay.setVisibility(View.GONE);
        vMainImage.setImageResource(R.drawable.no_connection);
        if (vRefresh.isRefreshing()){
            vRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onGetQueueStatusSuccess(Queue queue) {
        if(vProgress.getVisibility() == View.VISIBLE){
            vProgress.setVisibility(View.GONE);
        }
        vContainer.setVisibility(View.VISIBLE);
        vInQueueDelay.setVisibility(View.VISIBLE);
        if(queue.getWaitTime() > 0){
            vInQueueDelay.setText(Html.fromHtml(getString(R.string.in_queue_delay, DateUtil.getWaitingTime(getActivity(), queue.getWaitTime()))));
        } else if(queue.getWaitTime() == 0){
            vInQueueDelay.setText(getString(R.string.in_queue_no_delay));
        } else {
            vInQueueDelay.setText(getString(R.string.in_queue_no_doctor));
        }
    }


    @Override
    public void onGetQueueStatusError() {
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
        vInQueueDelay.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataStart() {
        vProgress.setVisibility(View.VISIBLE);
        vContainer.setVisibility(View.GONE);
    }

    @Override
    public void onInitCallSuccess(VideoCall videoCall) {
        vProgress.setVisibility(View.GONE);
        mVideocallId = videoCall.getId();
        callInQueueView();
    }


    @Override
    public void onInitCallError(GenericResponseDTO responseDTO) {
        if(responseDTO.getCode() == 1005){
            vProgress.setVisibility(View.GONE);
            AlertDialog.Builder dialogConfirmBuilder = new AlertDialog.Builder(getActivity()).setMessage(R.string.videocall_service_disabled).setPositiveButton(R.string.ok,
                    (dialog, which) -> {
                        if(vProgress.getVisibility() == View.GONE){
                            vProgress.setVisibility(View.VISIBLE);
                        }
                        vContainer.setVisibility(View.GONE);
                        mPresenter.getAffiliateHistory();
                    }).setCancelable(false);

            AlertDialog alertDialog = dialogConfirmBuilder.create();
            alertDialog.setOnShowListener(dialog -> alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent)));
            alertDialog.show();
        } else {
            Toast.makeText(getActivity(), "Hubo un error iniciando la consulta, por favor volvé a intentarlo.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancelSuccess() {
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
        mPresenter.getAffiliateHistory();
    }

    @Override
    public void onCancelError() {
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
        AlertDialog.Builder dialogConfirmBuilder = new AlertDialog.Builder(getActivity()).setMessage(R.string.error_cancelling).setPositiveButton(R.string.ok,
                (dialog, which) -> {
                  dialog.dismiss();
                }).setCancelable(false);

        AlertDialog alertDialog = dialogConfirmBuilder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent)));
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CALL_RESULT) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK){
                new AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.call_rejectec))
                        .setPositiveButton(getString(R.string.accept), (dialog, button) ->  mPresenter.getAffiliateHistory())
                        .show();
            } else {
                if(resultCode == Activity.RESULT_FIRST_USER){
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.no_response))
                            .setPositiveButton(getString(R.string.accept), (dialog, button) ->  mPresenter.getAffiliateHistory())
                            .show();
                } else {
                    mPresenter.getAffiliateHistory();
                }
            }
        } else {
            mPresenter.getAffiliateHistory();
        }
    }

    public void startCallActivity(){
        startActivityForResult(NewCallActivity.getStartIntent(getActivity()), CALL_RESULT);
    }

}