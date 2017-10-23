package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.LoginView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.CreateAccountActivity;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerLoginComponent;
import com.unidadcoronaria.doctorencasa.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    public static final String TAG = "LoginFragment";


    @BindView(R.id.rl_progress)
    protected RelativeLayout vProgress;
    @BindView(R.id.fragment_login_username)
    protected TextView vUsername;
    @BindView(R.id.fragment_login_username_layout)
    protected TextInputLayout vUsernameLayout;
    @BindView(R.id.fragment_login_password)
    protected TextView vPassword;
    @BindView(R.id.fragment_login_password_layout)
    protected TextInputLayout vPasswordLayout;
    @BindView(R.id.fragment_login_create_account)
    protected TextView vCreateAccount;
    @BindView(R.id.fragment_login_button)
    protected Button vLogin;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerLoginComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    @OnClick(R.id.fragment_login_button)
    public void attemptLogin(){
        vProgress.setVisibility(View.VISIBLE);
        mPresenter.login(vUsername.getText().toString(), vPassword.getText().toString());
    }

    @OnClick(R.id.fragment_login_create_account)
    public void createAccount(){
        startActivity(CreateAccountActivity.getStartIntent(getActivity()));
    }

    @Override
    public void onEmptyUsername() {
        vProgress.setVisibility(View.GONE);
        vUsernameLayout.requestFocus();
        vUsernameLayout.setError(getString(R.string.can_be_empty));
        vUsernameLayout.setErrorEnabled(true);
        vPasswordLayout.setError(null);
        vPasswordLayout.setErrorEnabled(false);
    }

    @Override
    public void onEmptyPassword() {
        vProgress.setVisibility(View.GONE);
        vPasswordLayout.requestFocus();
        vUsernameLayout.setError(null);
        vUsernameLayout.setErrorEnabled(false);
        vPasswordLayout.setError(getString(R.string.can_be_empty));
        vPasswordLayout.setErrorEnabled(true);
    }

    @Override
    public void onLoginError() {
        vProgress.setVisibility(View.GONE);
        vUsernameLayout.requestFocus();
        vUsernameLayout.setError(getString(R.string.credential_error));
        vUsernameLayout.setErrorEnabled(true);
        vPasswordLayout.setError(null);
        vPasswordLayout.setErrorEnabled(false);
    }

    @Override
    public void onSaveAffiliateSuccess() {
        startActivity(MainActivity.getStartIntent(getActivity()));
        getActivity().finish();
    }

    @Override
    public void onSaveAffiliateError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error guardando la información del usuario. Por favor, intentelo nuevamente.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void invalidPasswordFormat() {
        vProgress.setVisibility(View.GONE);
        vPasswordLayout.requestFocus();
        vUsernameLayout.setError(null);
        vUsernameLayout.setErrorEnabled(false);
        vPasswordLayout.setError(getString(R.string.error_invalid_password_format));
        vPasswordLayout.setErrorEnabled(true);
    }

    @Override
    public void invalidUsernameFormat() {
        vProgress.setVisibility(View.GONE);
        vUsernameLayout.requestFocus();
        vUsernameLayout.setError(getString(R.string.error_invalid_username_format));
        vUsernameLayout.setErrorEnabled(true);
        vPasswordLayout.setError(null);
        vPasswordLayout.setErrorEnabled(false);
    }

}
