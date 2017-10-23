package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

public interface AffiliateService {

    @GET("provider/{providerId}/affiliate/{affiliateNumber}")
    Call<List<Affiliate>> getAffiliateData(@Path("providerId") int providerId, @Path("affiliateNumber") String affiliateNumber);

    @GET("user")
    Call<Affiliate> getAffiliate();

}


