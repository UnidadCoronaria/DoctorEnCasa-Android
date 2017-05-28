package com.unidadcoronaria.doctorencasa.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.BaseActivity;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.viewmodel.LoginViewModel;

import butterknife.BindView;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";
    private static final Integer USER_ID = 1;

    @BindView(R.id.fragment_login_username)
    protected TextView vUsername;
    @BindView(R.id.fragment_login_password)
    protected TextView vPassword;
    @BindView(R.id.fragment_login_create_account)
    protected Button vCreateAccount;
    @BindView(R.id.fragment_login_button)
    protected Button vLogin;

    private BaseActivity callback;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.callback = (BaseActivity) context;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoginViewModel.Factory factory = new LoginViewModel.Factory(USER_ID);
        final LoginViewModel mLoginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        mLoginViewModel.getUser().observe(this, user -> {
            if(user != null){
                vUsername.setText(user.getName());
            }
        });
        getLifecycle().addObserver(mLoginViewModel);
        vLogin.setOnClickListener(v -> mLoginViewModel.createUser(new User(USER_ID,"Usuario agregado","")));
        vCreateAccount.setOnClickListener(v -> callback.replaceFragment(new CreateAccountFragment()));
    }

}
