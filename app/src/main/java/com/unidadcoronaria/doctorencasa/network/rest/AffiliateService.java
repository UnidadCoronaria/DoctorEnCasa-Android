package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * @author agustin.bala
 * @since 0.0.1
 */
public interface AffiliateService {

    @GET("provider/{providerId}/group/{affiliateNumber}")
    Single<List<Affiliate>> getAffiliateData(@Path("providerId") int providerId, @Path("affiliateNumber") String affiliateNumber);

    @GET("user")
    Single<Affiliate> getAffiliate();

    @POST("auth")
    Single<UserInfo> login(@Body Credential credential);

    @POST("user/logout")
    Completable logout();

    @POST("user")
    Single<UserInfo> createUser(@Body Credential credential);

    @PUT("user/password/reset")
    Single<GenericResponseDTO> forgotPassword(@Body Credential credential);

    @PUT("user/password")
    Single<UserInfo> updatePassword(@Body Credential credential);

    @PUT("user/updateTokenGCM")
    Completable updateFCMToken(@Header("TokenGCM") String fcmToken);

    @GET("user/history")
    Single<AffiliateCallHistory> getAffiliateCallHistory();

    @GET("provider/{providerId}/group/{mGroupId}/owner")
    Single<Affiliate> getGroupOwner(@Path("providerId") int mProviderId, @Path("mGroupId") String mGroupId);
}
