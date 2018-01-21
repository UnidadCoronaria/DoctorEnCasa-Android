package com.unidadcoronaria.doctorencasa.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.Window;
import android.view.WindowManager;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.ClinicHistoryDetailFragment;
import com.unidadcoronaria.doctorencasa.fragment.SplashFragment;
import com.unidadcoronaria.doctorencasa.service.SinchService;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryDetailActivity extends BaseActivity {

    public static final String CLINIC_HISTORY_KEY = "CLINIC_HISTORY";
    private ClinicHistory mClinicHistory;

    @Override
    protected int getLayout() {
        return R.layout.activity_clinic_history;
    }

    @Override
    protected BaseFragment getFragment() {
        return ClinicHistoryDetailFragment.getInstance(mClinicHistory);
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mClinicHistory = (ClinicHistory) getIntent().getSerializableExtra(CLINIC_HISTORY_KEY);
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.app_name));
        setBackVisibilityInToolbar(true);
    }



}
