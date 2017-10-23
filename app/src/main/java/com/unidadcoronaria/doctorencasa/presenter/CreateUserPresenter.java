package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateUserUseCase;

import javax.inject.Inject;

import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validEmailFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validPasswordFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validUsernameFormat;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class CreateUserPresenter extends BasePresenter<AffiliateDataView> {

    private CreateUserUseCase mCreateUserUseCase;

    @Inject
    public CreateUserPresenter(CreateUserUseCase mCreateUserUseCase) {
        this.mCreateUserUseCase = mCreateUserUseCase;
    }

    @Override
    public void onStop(){
        super.onStop();
        this.mCreateUserUseCase.unsubscribe();
    }

    public void createAccount(Integer affiliateNumber, Integer selectedProvider,
                              String username, String password, String passwordRepeat, String email) {
        if(username.isEmpty()) {
            view.isUsernameEmpty();
            return;
        }

        if(password.isEmpty()) {
            view.isPasswordEmpty();
            return;
        }

        if(passwordRepeat.isEmpty()) {
            view.isPasswordRepeatEmpty();
            return;
        }

        if(email.isEmpty()) {
            view.isEmailEmpty();
            return;
        }

        if(!validUsernameFormat(username)){
            view.invalidUsernameFormat();
            return;
        }

        if(!validEmailFormat(email)){
            view.invalidEmailFormat();
            return;
        }

        if(!validPasswordFormat(password)){
            view.invalidPasswordFormat();
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

        Credential credential = new Credential.Builder(username, password).setAffiliateId(affiliateNumber)
                                                           .setProviderId(selectedProvider)
                                                                .setEmail(email).build();
        mCreateUserUseCase.setData(credential);
        mCreateUserUseCase.execute(o -> onCreateUserSuccess(), throwable -> onCreateUserError());

    }

    private void onCreateUserError() {
        String a = "";
    }

    private void onCreateUserSuccess() {
        String a = "";
    }
}
