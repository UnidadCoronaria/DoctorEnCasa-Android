package com.unidadcoronaria.doctorencasa.di.module;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.network.rest.AffiliateService;
import com.unidadcoronaria.doctorencasa.network.rest.ClinicHistoryService;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.repository.ClinicHistoryRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */

@Module
public class ClinicHistoryModule {

    @Provides
    @PerActivity
    ClinicHistoryService provideClinicHistoryService(Retrofit retrofit){
        return retrofit.create(ClinicHistoryService.class);
    }

    @Provides
    @PerActivity
    ClinicHistoryRepository provideClinicHistoryRepository(ClinicHistoryService clinicHistoryService){
        return new ClinicHistoryRepository(clinicHistoryService);
    }

}
