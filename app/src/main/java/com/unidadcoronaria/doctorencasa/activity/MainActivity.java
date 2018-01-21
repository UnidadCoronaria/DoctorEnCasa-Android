package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.sinch.android.rtc.SinchError;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.MainView;
import com.unidadcoronaria.doctorencasa.di.component.DaggerSettingsComponent;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.VideoCallFragment;
import com.unidadcoronaria.doctorencasa.presenter.MainPresenter;
import com.unidadcoronaria.doctorencasa.streaming.SinchCallManager;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class MainActivity extends BaseNavActivity implements MainView, SinchCallManager.StartFailedListener {


    @Inject
    MainPresenter mPresenter;


    public static Intent getStartIntent(Context context){
        return new Intent(context, MainActivity.class);
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
        if(!SessionUtil.isAuthenticated()){
            logout();
        }
        setBackVisibilityInToolbar(false);
        setToolbarTitle(getString(R.string.app_name));
        DaggerSettingsComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
        mPresenter.setView(this);
    }

    private void logout() {
        SessionUtil.logout();
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    protected void onServiceConnected() {
        if(getSinchServiceInterface() != null){
            getSinchServiceInterface().setStartListener(this);
            getSinchServiceInterface().startClient(SessionUtil.getUsername());
        }
    }

    @Override
    public void onStartFailed(SinchError error) {
        Log.e("MainActivity", "onStartFailed");
    }

    @Override
    public void onStopped() {
        Log.e("MainActivity", "onStopped");
    }

    @Override
    public void onStarted() {
        Log.e("MainActivity", "onStarted");
    }
}
