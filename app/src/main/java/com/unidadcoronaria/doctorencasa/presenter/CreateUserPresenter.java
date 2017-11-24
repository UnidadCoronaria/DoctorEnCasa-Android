package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.usecase.database.SaveUserUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateUserUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validEmailFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validPasswordFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validUsernameFormat;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class CreateUserPresenter extends BasePresenter<AffiliateDataView> {

    private CreateUserUseCase mCreateUserUseCase;
    private SaveUserUseCase mSaveUserUseCase;

    @Inject
    public CreateUserPresenter(CreateUserUseCase mCreateUserUseCase, SaveUserUseCase mSaveUserUseCase) {
        this.mCreateUserUseCase = mCreateUserUseCase;
        this.mSaveUserUseCase = mSaveUserUseCase;
    }

    @Override
    public void onStop(){
        super.onStop();
        this.mCreateUserUseCase.unsubscribe();
        this.mSaveUserUseCase.unsubscribe();
    }

    public void createAccount(Integer affiliateNumber, Integer selectedProvider,
                              String username, String password, String passwordRepeat, String email) {
        if(username.isEmpty()) {
            view.isUsernameEmpty();
            return;
        }

        if(!validUsernameFormat(username)){
            view.invalidUsernameFormat();
            return;
        }

        if(email.isEmpty()) {
            view.isEmailEmpty();
            return;
        }

        if(!validEmailFormat(email)){
            view.invalidEmailFormat();
            return;
        }

        if(password.isEmpty()) {
            view.isPasswordEmpty();
            return;
        }

        if(!validPasswordFormat(password)){
            view.invalidPasswordFormat();
            return;
        }

        if(passwordRepeat.isEmpty()) {
            view.isPasswordRepeatEmpty();
            return;
        }

        if(!validPasswordFormat(passwordRepeat)){
            view.invalidPasswordRepeatFormat();
            return;
        }

        if(!password.equals(passwordRepeat)){
            view.notMatchingPassword();
            return;
        }

        Credential credential = new Credential.Builder().setUsername(username).setPassword(password).setAffiliateId(affiliateNumber)
                                                           .setProviderId(selectedProvider)
                                                                .setEmail(email).build();
        view.onCreateUserStart();
        mCreateUserUseCase.setData(credential);
        mCreateUserUseCase.execute(o ->{
            UserInfo userInfo = (UserInfo) o;
            SessionUtil.saveToken(userInfo.getToken());
            SessionUtil.saveUsername(userInfo.getUser().getUsername());
            view.onSaveAffiliateSuccess(); } , throwable -> view.onSaveAffiliateError());

    }

    @Deprecated
    private void onCreateUserSuccess(UserInfo userInfo) {
        mSaveUserUseCase.setAffiliate(userInfo.getUser());
        mSaveUserUseCase.execute(() -> {
            SessionUtil.saveToken(userInfo.getToken());
            SessionUtil.saveUsername(userInfo.getUser().getUsername());
            view.onSaveAffiliateSuccess();
            }, throwable -> view.onSaveAffiliateError());
    }

}
