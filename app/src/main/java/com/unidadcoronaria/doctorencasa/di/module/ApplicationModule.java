package com.unidadcoronaria.doctorencasa.di.module;

import android.arch.persistence.room.Room;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.database.DoctorEnCasaDB;
import com.unidadcoronaria.doctorencasa.usecase.executor.JobExecutor;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;
import com.unidadcoronaria.doctorencasa.usecase.executor.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    DoctorEnCasaDB provideDB(){
        return Room.databaseBuilder(App.getInstance(),
                DoctorEnCasaDB.class, DoctorEnCasaDB.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

}
