package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.HomeFragment;
import com.unidadcoronaria.doctorencasa.fragment.SplashFragment;

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
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }


}
