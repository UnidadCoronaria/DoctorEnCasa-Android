package com.unidadcoronaria.doctorencasa.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.LoginView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.ChangePasswordActivity;
import com.unidadcoronaria.doctorencasa.activity.CreateAccountActivity;
import com.unidadcoronaria.doctorencasa.activity.ForgotPasswordActivity;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerLoginComponent;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.presenter.LoginPresenter;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    public static final String TAG = "LoginFragment";
    public static final int CALL_RESULT = 222;

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
        vLogin.setEnabled(false);
        mPresenter.login(vUsername.getText().toString(), vPassword.getText().toString());
    }

    @OnClick(R.id.fragment_login_forgot_password)
    public void forgotPassword(){
        startActivity(ForgotPasswordActivity.getStartIntent(getActivity()));
    }

    @OnClick(R.id.fragment_login_create_account)
    public void createAccount(){
        startActivityForResult(CreateAccountActivity.getStartIntent(getActivity()), CALL_RESULT);
    }

    @Override
    public void onEmptyUsername() {
        vLogin.setEnabled(true);
        vUsernameLayout.requestFocus();
        vUsernameLayout.setError(getString(R.string.can_be_empty));
        vUsernameLayout.setErrorEnabled(true);
        vPasswordLayout.setError(null);
        vPasswordLayout.setErrorEnabled(false);
    }

    @Override
    public void onEmptyPassword() {
        vLogin.setEnabled(true);
        vPasswordLayout.requestFocus();
        vUsernameLayout.setError(null);
        vUsernameLayout.setErrorEnabled(false);
        vPasswordLayout.setError(getString(R.string.can_be_empty));
        vPasswordLayout.setErrorEnabled(true);
    }

    @Override
    public void onLoginError() {
        vLogin.setEnabled(true);
        vUsernameLayout.requestFocus();
        vUsernameLayout.setError(getString(R.string.credential_error));
        vUsernameLayout.setErrorEnabled(true);
        vPasswordLayout.setError(null);
        vPasswordLayout.setErrorEnabled(false);
    }

    @Override
    public void onSaveAffiliateSuccess(UserInfo userInfo, Boolean passwordExpired) {
        resetView();
        if(passwordExpired){
            startActivity(ChangePasswordActivity.getStartIntent(getActivity()));
        } else {
            SessionUtil.saveProvider(userInfo.getUser().getProvider().getId());
            startActivity(MainActivity.getStartIntent(getActivity()));
            getActivity().finish();
        }
    }


    @Override
    public void invalidPasswordFormat() {
        vLogin.setEnabled(true);
        vPasswordLayout.requestFocus();
        vUsernameLayout.setError(null);
        vUsernameLayout.setErrorEnabled(false);
        vPasswordLayout.setError(getString(R.string.error_invalid_password_format));
        vPasswordLayout.setErrorEnabled(true);
    }

    @Override
    public void invalidUsernameFormat() {
        vLogin.setEnabled(true);
        vUsernameLayout.requestFocus();
        vUsernameLayout.setError(getString(R.string.error_invalid_username_format));
        vUsernameLayout.setErrorEnabled(true);
        vPasswordLayout.setError(null);
        vPasswordLayout.setErrorEnabled(false);
    }

    private void resetView(){
        vLogin.setEnabled(true);
        vUsername.setText("");
        vPassword.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CALL_RESULT && resultCode == Activity.RESULT_OK) {
            getActivity().finish();
        }
    }
}
