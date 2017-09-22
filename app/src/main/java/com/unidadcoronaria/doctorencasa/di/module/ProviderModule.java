package com.unidadcoronaria.doctorencasa.di.module;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.network.rest.ProviderService;
import com.unidadcoronaria.doctorencasa.repository.ProviderRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/31/2017.
 */

@Module
public class ProviderModule {

    @Provides
    @PerActivity
    ProviderRepository provideProviderRepository(ProviderService providerService){
        return new ProviderRepository(providerService);
    }

    @Provides
    @PerActivity
    ProviderService provideProviderService(Retrofit retrofit){
        return retrofit.create(ProviderService.class);
    }

}
