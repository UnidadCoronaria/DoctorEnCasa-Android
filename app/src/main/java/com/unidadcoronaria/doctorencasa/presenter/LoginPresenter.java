package com.unidadcoronaria.doctorencasa.presenter;

import android.util.Log;

import com.unidadcoronaria.doctorencasa.LoginView;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.usecase.database.SaveUserUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.LoginUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validPasswordFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validUsernameFormat;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginUseCase mLoginUseCase;
    private SaveUserUseCase mSaveUserUseCase;

    @Inject
    public LoginPresenter(LoginUseCase mLoginUseCase, SaveUserUseCase mSaveUserUseCase) {
        this.mLoginUseCase = mLoginUseCase;
        this.mSaveUserUseCase = mSaveUserUseCase;
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
        mLoginUseCase.execute(o -> {
            UserInfo userInfo = (UserInfo) o;
            SessionUtil.saveToken(userInfo.getToken());
            SessionUtil.saveUsername(userInfo.getUser().getUsername());
            view.onSaveAffiliateSuccess(userInfo.getUser().getPasswordExpired());
        }, throwable -> {
            Log.e("LoginPresenter", "Error performing login " + throwable.toString());
            view.onLoginError();
        });
    }

    @Deprecated
    private void saveAffiliate(UserInfo userInfo) {
        mSaveUserUseCase.setAffiliate(userInfo.getUser());
        mSaveUserUseCase.execute(() -> {
            SessionUtil.saveToken(userInfo.getToken());
            SessionUtil.saveUsername(userInfo.getUser().getUsername());
            view.onSaveAffiliateSuccess(userInfo.getUser().getPasswordExpired());
             }, throwable -> view.onSaveAffiliateError());
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoginUseCase.unsubscribe();
        mSaveUserUseCase.unsubscribe();
    }

}