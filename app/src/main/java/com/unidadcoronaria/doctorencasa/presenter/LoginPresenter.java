package com.unidadcoronaria.doctorencasa.presenter;

import android.util.Log;

import com.unidadcoronaria.doctorencasa.LoginView;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.usecase.database.LoadAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.database.SaveAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.LoginUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validEmailFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validPasswordFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validUsernameFormat;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginUseCase mLoginUseCase;
    private SaveAffiliateUseCase mSaveUserUseCase;

    @Inject
    public LoginPresenter(LoginUseCase mLoginUseCase, SaveAffiliateUseCase mSaveUserUseCase) {
        this.mLoginUseCase = mLoginUseCase;
        this.mSaveUserUseCase = mSaveUserUseCase;
    }

    private void onLoginSuccess(UserInfo userInfo) {
        saveAffiliate(userInfo);
    }


    public void login(String username, String password) {

        if (username.isEmpty()) {
            view.onEmptyUsername();
            return;
        }
        if (password.isEmpty()) {
            view.onEmptyPassword();
            return;
        }

        if(!validUsernameFormat(username)){
            view.invalidUsernameFormat();
            return;
        }

        if(!validPasswordFormat(password)){
            view.invalidPasswordFormat();
            return;
        }

        mLoginUseCase.setData(new Credential.Builder().setUsername(username).setPassword(password).build());
        mLoginUseCase.execute(userInfo -> {
            onLoginSuccess((UserInfo) userInfo);
        }, throwable -> {
            Log.e("LoginPresenter", "Error performing login " + throwable.toString());
            view.onLoginError();
        });
    }

    private void saveAffiliate(UserInfo userInfo) {
        mSaveUserUseCase.setAffiliate(userInfo.getUser());
        mSaveUserUseCase.execute(o -> {
            SessionUtil.saveToken(userInfo.getToken());
            SessionUtil.saveUsername(userInfo.getUser().getUsername());
            view.onSaveAffiliateSuccess(userInfo.getUser().getPasswordExpired()); }, throwable -> view.onSaveAffiliateError());
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoginUseCase.unsubscribe();
        mSaveUserUseCase.unsubscribe();
    }

}