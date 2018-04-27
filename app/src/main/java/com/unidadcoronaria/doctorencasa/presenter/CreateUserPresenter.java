package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateUserUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetGroupOwnerUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

import retrofit2.adapter.rxjava2.HttpException;

import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validEmailFormat;
import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validPasswordFormat;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class CreateUserPresenter extends BasePresenter<AffiliateDataView> {

    private CreateUserUseCase mCreateUserUseCase;
    private GetGroupOwnerUseCase mGetGroupOwnerUseCase;

    @Inject
    public CreateUserPresenter(CreateUserUseCase mCreateUserUseCase, GetGroupOwnerUseCase mGetGroupOwnerUseCase) {
        this.mCreateUserUseCase = mCreateUserUseCase;
        this.mGetGroupOwnerUseCase = mGetGroupOwnerUseCase;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mCreateUserUseCase.unsubscribe();
        this.mGetGroupOwnerUseCase.unsubscribe();
    }

    public void createAccount(Integer affiliateNumber, Integer selectedProvider,
                              String username, String password, String passwordRepeat, String email) {

        if (email.isEmpty()) {
            view.isEmailEmpty();
            return;
        }

        if (!validEmailFormat(email)) {
            view.invalidEmailFormat();
            return;
        }

        if (password.isEmpty()) {
            view.isPasswordEmpty();
            return;
        }

        if (!validPasswordFormat(password)) {
            view.invalidPasswordFormat();
            return;
        }

        if (passwordRepeat.isEmpty()) {
            view.isPasswordRepeatEmpty();
            return;
        }

        if (!validPasswordFormat(passwordRepeat)) {
            view.invalidPasswordRepeatFormat();
            return;
        }

        if (!password.equals(passwordRepeat)) {
            view.notMatchingPassword();
            return;
        }

        Credential credential = new Credential.Builder().setUsername(username).setPassword(password).setGroupNumberId(affiliateNumber)
                .setProviderId(selectedProvider)
                .setEmail(email).build();
        view.onCreateUserStart();
        mCreateUserUseCase.setData(credential);
        mCreateUserUseCase.execute(o -> {
            UserInfo userInfo = (UserInfo) o;
            SessionUtil.saveToken(userInfo.getToken());
            SessionUtil.saveUsername(userInfo.getUser().getUsername());
            SessionUtil.saveProvider(userInfo.getUser().getProvider().getId());
            view.onCreateUserSuccess();
        }, throwable -> {
            GenericResponseDTO errorResponse= gson.fromJson(((HttpException) throwable).response().errorBody().string(), GenericResponseDTO.class);
            view.onCreateUserError(errorResponse);
        });

    }

    public void getAffiliateGroupData(String affiliateGroupNumber, int mProviderId) {
        if (affiliateGroupNumber != null && !affiliateGroupNumber.isEmpty()) {
            mGetGroupOwnerUseCase.setData(mProviderId, affiliateGroupNumber);
            mGetGroupOwnerUseCase.execute(o -> view.onGroupOwnerRetrieved((Affiliate) o), throwable -> view.onGroupOwnerError());
            return;
        }
        view.onEmptyAffiliateNumber();
    }
}
