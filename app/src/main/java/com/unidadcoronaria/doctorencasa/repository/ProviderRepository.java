package com.unidadcoronaria.doctorencasa.repository;

import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.network.rest.ProviderService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ProviderRepository {

    private ProviderService mProviderService;

    @Inject
    public ProviderRepository(ProviderService mProviderService) {
        this.mProviderService = mProviderService;
    }

    public Single<List<Provider>> getProviderList() {
        return mProviderService.get();
    }

}
