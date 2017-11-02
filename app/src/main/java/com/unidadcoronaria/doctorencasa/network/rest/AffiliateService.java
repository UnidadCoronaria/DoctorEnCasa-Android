package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

public interface AffiliateService {

    @GET("provider/{providerId}/affiliate/{affiliateNumber}")
    Single<List<Affiliate>> getAffiliateData(@Path("providerId") int providerId, @Path("affiliateNumber") String affiliateNumber);

    @GET("user")
    Single<User> getAffiliate();

}


