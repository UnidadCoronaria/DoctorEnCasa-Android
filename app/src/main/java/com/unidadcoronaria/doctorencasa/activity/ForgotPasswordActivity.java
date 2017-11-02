package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.ForgotPasswordFragment;
import com.unidadcoronaria.doctorencasa.fragment.LoginFragment;

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
        return R.layout.activity_create_account;
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
