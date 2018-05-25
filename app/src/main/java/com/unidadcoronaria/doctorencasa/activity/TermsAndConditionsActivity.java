package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.ChangePasswordFragment;
import com.unidadcoronaria.doctorencasa.fragment.TermsAndConditionsFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class TermsAndConditionsActivity extends BaseActivity {

    public static Intent getStartIntent(Context context){
        return new Intent(context, TermsAndConditionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.terms));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_terms_and_conditions;
    }

    @Override
    protected BaseFragment getFragment() {
        return TermsAndConditionsFragment.newInstance();
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }


    public void logout() {
        finish();
    }


}
