package com.unidadcoronaria.doctorencasa.di.module;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.network.rest.AffiliateService;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */

@Module
public class UserModule {

    @Provides
    @PerActivity
    AffiliateService provideUserService(Retrofit retrofit){
        return retrofit.create(AffiliateService.class);
    }

    @Provides
    @PerActivity
    AffiliateRepository provideUserRepository(AffiliateService affiliateService){
        return new AffiliateRepository(affiliateService);
    }

}
