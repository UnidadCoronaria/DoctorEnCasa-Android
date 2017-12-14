package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.LoginFragment;
import com.unidadcoronaria.doctorencasa.fragment.NewCallFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginActivity extends BaseActivity {

    public static Intent getStartIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected BaseFragment getFragment() {
        return new LoginFragment();
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
