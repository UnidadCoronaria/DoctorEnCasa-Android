package com.unidadcoronaria.doctorencasa.di.module;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unidadcoronaria.doctorencasa.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().retryOnConnectionFailure(false).readTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).addInterceptor(chain -> {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder().header("Content-Type",
                    "application/json");
            Log.d("Retrofit", "Making request to "+originalRequest.url().toString());
            Request newRequest = builder.build();
            Response response = chain.proceed(newRequest);
            Log.d("Retrofit", "Body of "+originalRequest.url().toString()+ ": "+response.code());
            return response;
        }).addNetworkInterceptor(new StethoInterceptor()).build();
        return new Retrofit.Builder().client(okHttpClient)
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
