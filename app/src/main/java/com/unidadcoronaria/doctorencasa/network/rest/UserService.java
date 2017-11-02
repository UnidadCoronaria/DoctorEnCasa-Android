package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;


/**
 * @author agustin.bala
 * @since 0.0.1
 */
public interface UserService {

    @POST("auth")
    Single<UserInfo> login(@Body Credential credential);

    @PUT("logout")
    Completable logout();

    @POST("user")
    Single<UserInfo> createUser(@Body Credential credential);

    @PUT("user/password/reset")
    Single<UserInfo> forgotPassword(@Body Credential credential);

    @PUT("user/password")
    Single<UserInfo> updatePassword(@Body Credential credential);
}
