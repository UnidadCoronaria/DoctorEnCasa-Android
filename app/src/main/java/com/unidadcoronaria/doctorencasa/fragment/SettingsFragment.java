package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.EndCallView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.SettingsView;
import com.unidadcoronaria.doctorencasa.activity.ChangePasswordActivity;
import com.unidadcoronaria.doctorencasa.activity.LoginActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerSettingsComponent;
import com.unidadcoronaria.doctorencasa.presenter.EndCallPresenter;
import com.unidadcoronaria.doctorencasa.presenter.SettingsPresenter;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsView {

    public static final String TAG = "SettingsFragment";

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_settings;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
       DaggerSettingsComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    public static BaseFragment newInstance() {
        SettingsFragment instance = new SettingsFragment();
        return instance;
    }

    @OnClick(R.id.fragment_settings_change_pasword_button)
    public void onChangePasswordClick(){
        getActivity().startActivity(ChangePasswordActivity.getStartIntent(getActivity()));
    }


    @OnClick(R.id.fragment_settings_logout_button)
    public void onLogoutClick(){
        SessionUtil.logout();
        Intent intent = LoginActivity.getStartIntent(getActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
