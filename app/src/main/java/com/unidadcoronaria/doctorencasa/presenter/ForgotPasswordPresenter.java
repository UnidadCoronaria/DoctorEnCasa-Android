package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.ForgotPasswordView;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.ForgotPasswordUseCase;
import com.unidadcoronaria.doctorencasa.util.ErrorUtil;
import com.unidadcoronaria.doctorencasa.util.ValidationUtil;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView> {

    private ForgotPasswordUseCase mForgotPasswordUseCase;

    @Inject
    public ForgotPasswordPresenter(ForgotPasswordUseCase mForgotPasswordUseCase) {
        this.mForgotPasswordUseCase = mForgotPasswordUseCase;
    }

    @Override
    public void onStop() {
        super.onStop();
        mForgotPasswordUseCase.unsubscribe();
    }

    public void forgotPassword(String email) {
        if (email.isEmpty()) {
            view.onEmptyEmail();
            return;
        }

        if (!ValidationUtil.validEmailFormat(email)) {
            view.onInvalidFormatEmail();
            return;
        }

        Credential credential = new Credential.Builder().setEmail(email).build();
        mForgotPasswordUseCase.setData(credential);
        mForgotPasswordUseCase.execute(o -> view.onForgotPasswordSuccess(),
                throwable ->
                    checkTokenExpired(throwable, () -> {
                        view.onForgotPasswordError(ErrorUtil.getError(throwable));
                        return null;
                    })
                );
    }
}