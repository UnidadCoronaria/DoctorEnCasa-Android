package com.unidadcoronaria.doctorencasa.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unidadcoronaria.doctorencasa.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */

@Module
public class HTTPModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }
}
