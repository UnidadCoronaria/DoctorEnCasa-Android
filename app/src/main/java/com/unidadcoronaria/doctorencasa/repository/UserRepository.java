package com.unidadcoronaria.doctorencasa.repository;


import com.unidadcoronaria.doctorencasa.dao.AffiliateDAO;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.network.rest.UserService;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

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
        return Single.fromCallable(() -> mUserService.login(credential).execute().body());
    }

    public Completable logout() {
        return Completable.fromAction(() -> mUserService.logout().execute());
    }

    public Single<UserInfo> createUser(Credential credential) {
        return Single.fromCallable(() ->  mUserService.createUser(credential).execute().body());
    }
}
