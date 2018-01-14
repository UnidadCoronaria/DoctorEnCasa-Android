package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.SplashView;
import com.unidadcoronaria.doctorencasa.activity.LoginActivity;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerSplashComponent;
import com.unidadcoronaria.doctorencasa.presenter.SplashPresenter;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SplashFragment extends BaseFragment<SplashPresenter> implements SplashView {


    public static final String TAG = "SplashFragment";

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_splash;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerSplashComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    @Override
    public void userAuthenticated() {
        startActivity(MainActivity.getStartIntent(getContext()));
        getActivity().finish();
    }

    @Override
    public void onEmptyAffiliate() {
        startActivity(LoginActivity.getStartIntent(getContext()));
        getActivity().finish();
    }
}
