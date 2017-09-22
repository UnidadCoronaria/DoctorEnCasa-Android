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

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoadAffiliateUseCase mLoadAffiliateUseCase;
    private LoginUseCase mLoginUseCase;
    private SaveAffiliateUseCase mSaveUserUseCase;

    @Inject
    public LoginPresenter(LoadAffiliateUseCase mLoadAffiliateUseCase, LoginUseCase mLoginUseCase, SaveAffiliateUseCase mSaveUserUseCase) {
        this.mLoadAffiliateUseCase = mLoadAffiliateUseCase;
        this.mLoginUseCase = mLoginUseCase;
        this.mSaveUserUseCase = mSaveUserUseCase;
    }

    public void getAffiliate() {
        mLoadAffiliateUseCase.execute(o -> onLoadAffiliateSuccess((Affiliate) o));
    }

    public void login(String username, String password) {
        if (username == null || username.isEmpty()) {
            view.onEmptyUsername();
            return;
        }
        if (password == null || password.isEmpty()) {
            view.onEmptyPassword();
            return;
        }
        mLoginUseCase.setData(new Credential.Builder(username, password).build());
        mLoginUseCase.execute(userInfo -> {
            onLoginSuccess((UserInfo) userInfo);
        }, throwable -> {
            Log.e("LoginPresenter", "Error performing login " + throwable.toString());
            view.onLoginError();
        });
    }


    private void onLoadAffiliateSuccess(Affiliate affiliate) {
        if (SessionUtil.getToken() != null && !SessionUtil.getToken().isEmpty() && affiliate != null) {
            view.onAffiliateRetrieved();
        }
    }

    private void onLoginSuccess(UserInfo userInfo) {
        SessionUtil.saveToken(userInfo.getToken());
        SessionUtil.saveUsername(userInfo.getUser().getUsername());
        saveAffiliate(userInfo.getUser());
    }

    private void saveAffiliate(Affiliate affiliate) {
        mSaveUserUseCase.setAffiliate(affiliate);
        mSaveUserUseCase.execute(o -> view.onSaveAffiliateSuccess(), throwable -> view.onSaveAffiliateError());
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoadAffiliateUseCase.unsubscribe();
        mLoginUseCase.unsubscribe();
        mSaveUserUseCase.unsubscribe();
    }

}