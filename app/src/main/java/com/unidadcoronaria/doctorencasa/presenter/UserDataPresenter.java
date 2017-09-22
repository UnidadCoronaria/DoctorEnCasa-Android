package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.usecase.network.CreateUserUseCase;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class UserDataPresenter extends BasePresenter<AffiliateDataView> {

    private CreateUserUseCase mCreateUserUseCase;

    @Inject
    public UserDataPresenter(CreateUserUseCase mCreateUserUseCase) {
        this.mCreateUserUseCase = mCreateUserUseCase;
    }

    @Override
    public void onStop(){
        super.onStop();
        this.mCreateUserUseCase.unsubscribe();
    }

    public void createAccount(Integer affiliateNumber, Integer selectedProvider,
                                String username, String password) {
        Credential credential = new Credential.Builder(username, password).setAffiliateId(affiliateNumber).setProviderId(selectedProvider).build();
        mCreateUserUseCase.setData(credential);
        mCreateUserUseCase.execute(o -> onCreateUserSuccess(), throwable -> onCreateUserError());
    }

    private void onCreateUserError() {

    }

    private void onCreateUserSuccess() {

    }
}
