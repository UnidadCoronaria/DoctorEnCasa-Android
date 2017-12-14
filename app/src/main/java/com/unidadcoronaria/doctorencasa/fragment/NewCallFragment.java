package com.unidadcoronaria.doctorencasa.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.video.VideoController;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.NewCallView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.domain.VideoCallStatus;
import com.unidadcoronaria.doctorencasa.presenter.NewCallPresenter;
import com.unidadcoronaria.doctorencasa.streaming.SinchCallManager;
import com.unidadcoronaria.doctorencasa.dialog.RankDialog;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

@RuntimePermissions
public class NewCallFragment extends BaseFragment<NewCallPresenter> implements NewCallView {

    public static final String TAG = NewCallFragment.class.getSimpleName();

    @BindView(R.id.call_frame)
    protected LinearLayout vFrame;

    @BindView(R.id.call_self_camera)
    protected LinearLayout vSelfCamera;

    @BindView(R.id.fragment_video_call_container)
    protected View vContainer;

    @BindView(R.id.rl_error)
    protected View vErrorContainer;

    @BindView(R.id.fragment_new_video_call_calling)
    protected View vStartingContainer;

    @BindView(R.id.fragment_video_call_hangup_button)
    protected View vHangoutButton;

    private SinchCallManager mCallManager = new SinchCallManager();
    private Call mCall;
    public final static String CALL_DESTINATION_KEY = "com.unidadcoronaria.doctorencasa.fragment.newcallfragment.CALL_DESTINATION_KEY";
    public final static String CALL_DESTINATION_ID_KEY = "com.unidadcoronaria.doctorencasa.fragment.newcallfragment.CALL_DESTINATION_ID_KEY";
    private VideoCall mCallDestination;
    private RankDialog mRankDialog =  new RankDialog();
    private int mCallDestinationId;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_new_video_call;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    public static NewCallFragment newInstance(VideoCall callDestination, int mCallDestinationId) {
        NewCallFragment instance = new NewCallFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CALL_DESTINATION_KEY, callDestination);
        bundle.putInt(CALL_DESTINATION_ID_KEY, mCallDestinationId);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected void inject() {
        DaggerVideoCallComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
        if (getArguments() != null && getArguments().containsKey(CALL_DESTINATION_KEY)) {
            mCallDestination = (VideoCall) getArguments().getSerializable(CALL_DESTINATION_KEY);
        }
        if (getArguments() != null && getArguments().containsKey(CALL_DESTINATION_ID_KEY)) {
            mCallDestinationId = getArguments().getInt(CALL_DESTINATION_ID_KEY);
        }
        vStartingContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mCallDestination != null){
            initCallClient();
        } else {
            if(mCallDestinationId != 0){
                 mPresenter.getVideocall(mCallDestinationId);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        bundle.putInt(CALL_DESTINATION_ID_KEY, mCallDestinationId);
        bundle.putSerializable(CALL_DESTINATION_KEY, mCallDestination);
    }

    private void initCallClient() {
        vStartingContainer.setVisibility(View.VISIBLE);
        mCallManager.initClient(SessionUtil.getUsername(), new SinchCallManager.StartFailedListener() {
            @Override
            public void onStartFailed(SinchError error) {
                Log.i(TAG, "Error in onStartFailed");
                //TODO
                //VER COMO MANEJAR LOS REINTENTOS Y UN TIMEOUT PARA QUE NO QUEDE COLGADA LA LLAMADA
                initCallClient();
            }

            @Override
            public void onStopped() {
                Log.i(TAG, "Sinch Client stopped");
                Toast.makeText(getActivity(), "Sinch Client stopped", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStarted() {
                Log.i(TAG, "Sinch Client started");
                NewCallFragmentPermissionsDispatcher.makeCallWithPermissionCheck(NewCallFragment.this);
            }
        });
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        NewCallFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
             Manifest.permission.READ_PHONE_STATE})
    protected void makeCall() {
        mPresenter.start(mCallDestination);
    }


    @OnClick(R.id.fragment_video_call_hangup_button)
    protected void onHangoutClick() {
        mCall.hangup();
    }

    private void addVideoViews() {
        final VideoController vc = mCallManager.getVideoController();
        if (vc != null) {
            vFrame.addView(vc.getRemoteView());
            vFrame.setOnClickListener(v -> vc.toggleCaptureDevicePosition());
            vSelfCamera.addView(vc.getLocalView());
        }
    }

    private void removeVideoViews() {
        VideoController vc = mCallManager.getVideoController();
        if (vc != null) {
            vFrame.removeView(vc.getRemoteView());
            vSelfCamera.removeView(vc.getLocalView());
        }
    }

    public void onStop() {
        super.onStop();
        mCallManager.stopClient();
    }

    //region Permissions Handling
    @SuppressLint("NoCorrespondingNeedsPermission")
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.permissions_need_acepted))
                .setPositiveButton(getString(R.string.yes), (dialog, button) -> request.proceed())
                .setNegativeButton(getString(R.string.no), (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE})
    void showDeniedForCamera() {
        vErrorContainer.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        Toast.makeText(getActivity()
                , getString(R.string.permissions_denied), Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE})
    void showNeverAskForCamera() {
        vErrorContainer.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
        vStartingContainer.setVisibility(View.GONE);
        vContainer.setVisibility(View.GONE);
        Toast.makeText(getActivity(), getString(R.string.permissions_never_ask), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHangupSuccess(VideoCall videoCall) {
        mRankDialog.dismiss();
        mRankDialog.showRankMessage(getActivity(),
                new RankDialog.Callback() {
                    @Override
                    public void onPositiveClick(String comment, int ranking) {
                        mPresenter.rank(mCallDestination.getId(), comment, ranking);
                    }

                    @Override
                    public void onNegativeClick() {
                        getActivity().finish();
                    }
                });
    }

    @Override
    public void onHangupError() {
        new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.error_hangup))
                .setPositiveButton(getString(R.string.cancel), (dialog, button) -> getActivity().finish())
                .setNegativeButton(getString(R.string.retry), (dialog, button) -> mPresenter.hangout(mCallDestination))
                .setCancelable(false)
                .show();

    }

    @Override
    public void onStartSuccess(VideoCall videoCall) {
        vProgress.setVisibility(View.GONE);
        vContainer.setVisibility(View.VISIBLE);
        mCall = mCallManager.makeCall(mCallDestination.getDoctor().getUsername(), new SinchCallManager.SinchCallListener() {
            @Override
            public void onCallEstablished(Call call) {
                super.onCallEstablished(call);
                addVideoViews();
                vHangoutButton.setVisibility(View.VISIBLE);
                vStartingContainer.setVisibility(View.GONE);
            }


            @Override
            public void onCallEnded(Call call) {
                super.onCallEnded(call);
                vStartingContainer.setVisibility(View.GONE);
                removeVideoViews();
                vContainer.setVisibility(View.GONE);
                mPresenter.hangout(mCallDestination);
            }

        });
    }

    @Override
    public void onStartError() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Error iniciando la llamada. Por favor,vuelva a intentarlo.")
                .setPositiveButton(getString(R.string.cancel), (dialog, button) -> getActivity().finish())
                .setNegativeButton(getString(R.string.retry), (dialog, button) -> mPresenter.start(mCallDestination))
                .setCancelable(false)
                .show();
    }

    @Override
    public void onCallUnavailableError() {
        Toast.makeText(getActivity(), "Ya no es posible realizar la llamada", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRankSuccess() {
        vProgress.setVisibility(View.GONE);
        getActivity().finish();
    }

    @Override
    public void onRankError() {
        vProgress.setVisibility(View.GONE);
        new AlertDialog.Builder(getActivity())
                .setMessage("Hubo un error. Por favor, vuelva a intentarlo.")
                .setPositiveButton(getString(R.string.cancel), (dialog, button) -> getActivity().finish())
                .setNegativeButton(getString(R.string.retry), (dialog, button) -> onHangupSuccess(mCallDestination))
                .setCancelable(false)
                .show();
    }

    @Override
    public void onGetVideocallSuccess(VideoCall videoCall) {
        if(VideoCallStatus.LISTA_ATENCION.equals(videoCall.getStatus())){
            mCallDestination = videoCall;
            initCallClient();
        } else {
            new AlertDialog.Builder(getActivity())
                    .setMessage("La llamada no está disponible")
                    .setPositiveButton(getString(R.string.accept), (dialog, button) -> getActivity().finish())
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    public void onGetVideocallError() {
        vStartingContainer.setVisibility(View.GONE);
        new AlertDialog.Builder(getActivity())
                .setMessage("Hubo un error. Por favor, vuelva a intentarlo.")
                .setPositiveButton(getString(R.string.cancel), (dialog, button) -> getActivity().finish())
                .setNegativeButton(getString(R.string.retry), (dialog, button) -> mPresenter.getVideocall(mCallDestinationId))
                .setCancelable(false)
                .show();
    }
    //endregion
}
