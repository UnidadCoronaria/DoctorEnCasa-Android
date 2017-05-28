package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.Provider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public interface ProviderService {

    @GET("provider")
    Call<List<Provider>> get();

}
