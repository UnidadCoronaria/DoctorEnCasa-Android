package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.HomeFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class MainActivity extends BaseNavActivity {

    public static Intent getStartIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseFragment getFragment() {
        return new HomeFragment();
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
