package com.unidadcoronaria.doctorencasa;

import android.app.Application;

import com.unidadcoronaria.doctorencasa.di.component.ApplicationComponent;
import com.unidadcoronaria.doctorencasa.di.component.DaggerApplicationComponent;
import com.unidadcoronaria.doctorencasa.di.component.DaggerLoginComponent;
import com.unidadcoronaria.doctorencasa.di.component.LoginComponent;
import com.unidadcoronaria.doctorencasa.di.module.ApplicationModule;
import com.unidadcoronaria.doctorencasa.di.module.HTTPModule;
import com.unidadcoronaria.doctorencasa.di.module.LoginModule;

/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public class App extends Application {

    private ApplicationComponent applicationComponent;
    private static App INSTANCE;
    //region Public Static Implementation
    public static App getInstance() {
        return INSTANCE;
    }
    //endregion

    //region Application
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        applicationComponent = DaggerApplicationComponent.builder().build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}