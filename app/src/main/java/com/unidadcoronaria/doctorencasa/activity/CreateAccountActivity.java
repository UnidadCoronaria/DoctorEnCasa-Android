package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.CreateAccountFragment;
import com.unidadcoronaria.doctorencasa.fragment.FragmentContainer;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateAccountActivity extends BaseActivity implements FragmentContainer {

    @Override
    protected int getLayout() {
        return R.layout.activity_create_account;
    }

    @Override
    protected BaseFragment getFragment() {
        return new CreateAccountFragment();
    }

    public static Intent getStartIntent(Context context){
        return new Intent(context, CreateAccountActivity.class);
    }

}
