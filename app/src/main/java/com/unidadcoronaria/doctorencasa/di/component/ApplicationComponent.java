package com.unidadcoronaria.doctorencasa.di.component;

import com.google.gson.Gson;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.activity.LoginActivity;
import com.unidadcoronaria.doctorencasa.database.DoctorEnCasaDB;
import com.unidadcoronaria.doctorencasa.di.module.ApplicationModule;
import com.unidadcoronaria.doctorencasa.di.module.HTTPModule;
import com.unidadcoronaria.doctorencasa.di.module.LoginModule;
import com.unidadcoronaria.doctorencasa.fragment.LoginFragment;
import com.unidadcoronaria.doctorencasa.threading.AsyncRunner;
import com.unidadcoronaria.doctorencasa.viewmodel.LoginViewModel;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@Component(modules = {ApplicationModule.class, HTTPModule.class})
@Singleton
public interface ApplicationComponent {

    DoctorEnCasaDB getDB();

    Retrofit getRetrofit();

    Gson getGson();

    AsyncRunner provideAsyncRunner(Executor executor);

    Executor provideThreadExecutor();
}
