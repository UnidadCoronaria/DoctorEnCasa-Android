package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.ChangePasswordFragment;
import com.unidadcoronaria.doctorencasa.fragment.ForgotPasswordFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ChangePasswordActivity extends BaseActivity {

    public static Intent getStartIntent(Context context){
        return new Intent(context, ChangePasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.change_password));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected BaseFragment getFragment() {
        return ChangePasswordFragment.newInstance();
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }


}
