package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * @author agustin.bala
 * @since 0.0.1
 */
public interface UserService {

    @POST("auth")
    Call<UserInfo> login(@Body Credential credential);

    @PUT("logout")
    Call<Void> logout();

    @POST("user")
    Call<UserInfo> createUser(@Body Credential credential);

}
