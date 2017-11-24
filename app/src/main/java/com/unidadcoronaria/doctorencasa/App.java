package com.unidadcoronaria.doctorencasa;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.unidadcoronaria.doctorencasa.di.component.ApplicationComponent;
import com.unidadcoronaria.doctorencasa.di.component.DaggerApplicationComponent;

/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public class App extends MultiDexApplication {

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
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}