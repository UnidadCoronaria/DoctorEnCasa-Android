package com.unidadcoronaria.doctorencasa.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.MainView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerSettingsComponent;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.VideoCallFragment;
import com.unidadcoronaria.doctorencasa.presenter.MainPresenter;
import com.unidadcoronaria.doctorencasa.util.FragmentNavigationUtil;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

import hotchemi.android.rate.AppRate;
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
public class MainActivity extends BaseNavActivity implements MainView {


    @Inject
    MainPresenter mPresenter;


    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseFragment getFragment() {
        return new VideoCallFragment();
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SessionUtil.isAuthenticated()) {
            logout();
        }
        setBackVisibilityInToolbar(false);
        setToolbarTitle(getString(R.string.app_name));
        DaggerSettingsComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
        mPresenter.setView(this);
    }

    private void checkRateApp() {
        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SessionUtil.isCallPending()) {
            checkRateApp();
        }
        MainActivityPermissionsDispatcher.checkPermissionsWithPermissionCheck(this);
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method

        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE})
    protected void checkPermissions() {
        if (SessionUtil.isCallPending()) {
            VideoCallFragment instance = (VideoCallFragment) FragmentNavigationUtil.getFragment(getSupportFragmentManager(), VideoCallFragment.TAG);
            if (instance != null) {
                instance.startCallActivity();
            }
        }
    }

    //region Permissions Handling
    @SuppressLint("NoCorrespondingNeedsPermission")
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.permissions_need_acepted))
                .setPositiveButton(getString(R.string.yes), (dialog, button) -> request.proceed())
                .setNegativeButton(getString(R.string.no), (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS})
    void showDeniedForCamera() {
        Toast.makeText(this
                , getString(R.string.permissions_denied), Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS})
    void showNeverAskForCamera() {
        Toast.makeText(this, getString(R.string.permissions_never_ask), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logout() {
        SessionUtil.logout();
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}