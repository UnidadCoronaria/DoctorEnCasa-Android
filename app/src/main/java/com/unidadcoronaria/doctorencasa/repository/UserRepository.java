package com.unidadcoronaria.doctorencasa.repository;


import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.network.rest.UserService;


import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by AGUSTIN.BALA on 5/25/2017.
 */

public class UserRepository {

    private final UserService mUserService;


    @Inject
    public UserRepository(UserService mUserService) {
        this.mUserService = mUserService;
    }


    public Single<UserInfo> login(Credential credential) {
        return mUserService.login(credential);
    }

    public Completable logout() {
        return mUserService.logout();
    }

    public Single<UserInfo> createUser(Credential credential) {
        return mUserService.createUser(credential);
    }

    public Single<UserInfo> forgotPassword(Credential credential) {
        return mUserService.forgotPassword(credential);
    }

    public Single<UserInfo> updateUser(Credential credential) {
        return mUserService.updatePassword(credential);
    }

}
