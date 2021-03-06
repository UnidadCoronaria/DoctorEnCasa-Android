package com.unidadcoronaria.doctorencasa.di.module;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unidadcoronaria.doctorencasa.BuildConfig;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */

@Module
public class HTTPModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(interceptor).retryOnConnectionFailure(false).readTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).addInterceptor(chain -> {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder().header("Content-Type",
                    "application/json");
            if(!SessionUtil.getToken().isEmpty() ){
                //builder.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZ29uemFsZXoiLCJhdWRpZW5jZSI6IndlYiIsImNyZWF0ZWQiOjE1MTA5NTgzOTk4NjIsImV4cCI6MTUxMTU2MzE5OX0.t8cDFOTAdO1OVLKXg9fOsgaBEfiqP87rhrxiD0i-2IR7xYWKVWYNDzbUYt9YgI1GccQ242zMTOxJUpzfIeBH3Q");
                builder.header("Authorization", SessionUtil.getToken());
            }
            Log.d("Retrofit", "Making request to "+originalRequest.url().toString());
            Request newRequest = builder.build();
            Response response = chain.proceed(newRequest);
            Log.d("Retrofit", "Body of "+originalRequest.url().toString()+ ": "+response.code());
            return response;
        }).addNetworkInterceptor(new StethoInterceptor()).build();
        return new Retrofit.Builder().client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }
}
