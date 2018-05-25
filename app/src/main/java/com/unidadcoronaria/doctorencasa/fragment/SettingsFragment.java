package com.unidadcoronaria.doctorencasa.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.SettingsView;
import com.unidadcoronaria.doctorencasa.activity.ChangePasswordActivity;
import com.unidadcoronaria.doctorencasa.activity.TermsAndConditionsActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerSettingsComponent;
import com.unidadcoronaria.doctorencasa.presenter.SettingsPresenter;

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

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @OnClick(R.id.fragment_settings_logout_button)
    public void logoutClick(){
        logout();
    }

    @OnClick(R.id.fragment_settings_change_pasword_button)
    public void changePassword(){
        startActivity(ChangePasswordActivity.getStartIntent(getActivity()));
    }

    @OnClick(R.id.fragment_settings_rate_app_button)
    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }

    @OnClick(R.id.fragment_settings_terms_button)
    public void termsAndConditions(){
        startActivity(TermsAndConditionsActivity.getStartIntent(getActivity()));
    }
}
