package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * @author agustin.bala
 * @since 0.0.1
 */
public interface AffiliateService {

    @GET("provider/{providerId}/affiliate/{affiliateNumber}")
    Single<List<Affiliate>> getAffiliateData(@Path("providerId") int providerId, @Path("affiliateNumber") String affiliateNumber);

    @GET("user")
    Single<Affiliate> getAffiliate();

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

    @PUT("user/fcmToken/{fcmToken}")
    Completable updateFCMToken(@Path("fcmToken") String fcmToken);

    @GET("user/history")
    Single<AffiliateCallHistory> getAffiliateCallHistory();



}
