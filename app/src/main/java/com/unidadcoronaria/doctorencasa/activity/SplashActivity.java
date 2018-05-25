package com.unidadcoronaria.doctorencasa.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.SplashFragment;

import hotchemi.android.rate.AppRate;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected BaseFragment getFragment() {
        return new SplashFragment();
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        AppRate.with(this)
                .setInstallDays(10) // default 10, 0 means install day.
                .setLaunchTimes(10) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                //.setOnClickButtonListener(which -> startActivity(TermsAndConditionsActivity.getStartIntent(MainActivity.this)))
                .setTitle(R.string.rate_app)
                .setTextLater(R.string.remember_later)
                .setTextNever(R.string.no_rate)
                .setTextRateNow(R.string.rate_now)
                .setMessage(R.string.rate_message)
                //.setView(view)
                .monitor();
    }


}
