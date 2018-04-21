package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.unidadcoronaria.doctorencasa.LoadableActivity;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.ForgotPasswordFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ForgotPasswordActivity extends BaseActivity {

    public static Intent getStartIntent(Context context){
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.recover_password));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected BaseFragment getFragment() {
        return new ForgotPasswordFragment();
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }



}
