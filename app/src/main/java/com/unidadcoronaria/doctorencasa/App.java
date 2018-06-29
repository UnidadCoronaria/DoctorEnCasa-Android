package com.unidadcoronaria.doctorencasa;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.unidadcoronaria.doctorencasa.di.component.ApplicationComponent;
import com.unidadcoronaria.doctorencasa.di.component.DaggerApplicationComponent;

import hotchemi.android.rate.AppRate;
import io.fabric.sdk.android.Fabric;

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
            Fabric.with(this, new Crashlytics());
        }

        AppRate.with(this)
                .setInstallDays(10) // default 10, 0 means install day.
                .setLaunchTimes(10) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                //.setOnClickButtonListener(which -> startActivity(TermsAndConditionsActivity.getStartIntent(MainActivity.this)))
                .setTitle(R.string.rate_app)
                .setTextLater(R.string.remember_later)
                .setTextNever(R.string.no_rate)
                .setTextRateNow(R.string.rate_now)
                .setMessage(R.string.rate_message)
                //.setView(view)
                .monitor();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}