package com.unidadcoronaria.doctorencasa.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.viewmodel.LoginViewModel;

import butterknife.BindView;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginFragment extends BaseFragment {

    private LoginViewModel mLoginViewModel;
    @BindView(R.id.user_name)
    protected TextView mTextView;
    @BindView(R.id.provider)
    protected TextView mTextViewProvider;
    @BindView(R.id.modify)
    protected Button mButtonUpdate;
    @BindView(R.id.create_button)
    protected Button mButton;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.init();
        mLoginViewModel.getProviderList().observe(this, list -> {
            mTextViewProvider.setText("");
            for (Provider provider: list) {
                mTextViewProvider.setText(mTextViewProvider.getText() + provider.getName());
            }
        });

        mButton.setOnClickListener(v -> mLoginViewModel.createUser(new User(1,"","")));
    }

}
