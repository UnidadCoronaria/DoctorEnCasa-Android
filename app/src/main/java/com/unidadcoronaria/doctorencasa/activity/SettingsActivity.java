package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.EndCallFragment;
import com.unidadcoronaria.doctorencasa.fragment.SettingsFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SettingsActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    protected BaseFragment getFragment() {
        return SettingsFragment.newInstance();
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackVisibilityInToolbar(true);
        setToolbarTitle(getString(R.string.settings));
    }

}
