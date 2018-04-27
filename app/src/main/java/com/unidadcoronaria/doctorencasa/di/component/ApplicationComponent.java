package com.unidadcoronaria.doctorencasa.di.component;

import com.google.gson.Gson;
import com.unidadcoronaria.doctorencasa.di.module.ApplicationModule;
import com.unidadcoronaria.doctorencasa.di.module.HTTPModule;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class, HTTPModule.class})
public interface ApplicationComponent {

    Retrofit getRetrofit();

    Gson getGson();

    ThreadExecutor getThreadExecutor();

    PostExecutionThread postExecutionThread();
}
