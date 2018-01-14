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
            onLogout();
        }
        setBackVisibilityInToolbar(false);
        setToolbarTitle(getString(R.string.app_name));
        DaggerSettingsComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
        mPresenter.setView(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_password) {
            startActivity(ChangePasswordActivity.getStartIntent(this));
            return true;
        }
        //Add Below if you want to do actions when you click action_home
        else if (id == R.id.action_logout) {
            mPresenter.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogout() {
        Intent intent = LoginActivity.getStartIntent(getApplicationContext());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLogoutError() {
        new AlertDialog.Builder(this)
                .setMessage("Error cerrando sesión. Por favor,vuelva a intentarlo.")
                .setPositiveButton(getString(R.string.cancel), (dialog, button) -> dialog.dismiss())
                .setNegativeButton(getString(R.string.retry), (dialog, button) -> mPresenter.logout())
                .setCancelable(false)
                .show();
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
