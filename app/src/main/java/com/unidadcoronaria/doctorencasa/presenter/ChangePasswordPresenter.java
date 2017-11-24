package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.ChangePasswordView;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.usecase.network.UpdateAffiliateUseCase;

import javax.inject.Inject;

import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validPasswordFormat;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {

    private UpdateAffiliateUseCase mUpdateAffiliateUseCase;

    @Inject
    public ChangePasswordPresenter(UpdateAffiliateUseCase mUpdateAffiliateUseCase) {
        this.mUpdateAffiliateUseCase = mUpdateAffiliateUseCase;
    }

    @Override
    public void onStop(){
        super.onStop();
        this.mUpdateAffiliateUseCase.unsubscribe();
    }

    public void changePassword(String currentPassword, String newPassword, String newPasswordRepeat) {

        if(currentPassword.isEmpty()) {
            view.isCurrentPasswordEmpty();
            return;
        }

        if(!validPasswordFormat(currentPassword)){
            view.invalidCurrentPasswordFormat();
            return;
        }

        if(newPassword.isEmpty()) {
            view.isNewPasswordEmpty();
            return;
        }

        if(!validPasswordFormat(newPassword)){
            view.invalidNewPasswordFormat();
            return;
        }

        if(newPasswordRepeat.isEmpty()) {
            view.isNewPasswordRepeatEmpty();
            return;
        }

        if(!validPasswordFormat(newPasswordRepeat)){
            view.invalidNewPasswordRepeatFormat();
            return;
        }

        if(!newPassword.equals(newPasswordRepeat)){
            view.notMatchingPassword();
            return;
        }

        Credential credential = new Credential.Builder().setPassword(currentPassword).setNewPassword(newPassword).build();
        view.onChangePasswordStart();
        mUpdateAffiliateUseCase.setData(credential);
        mUpdateAffiliateUseCase.execute(o -> view.onChangePasswordSuccess(), throwable -> view.onChangePasswordError());

    }

}
