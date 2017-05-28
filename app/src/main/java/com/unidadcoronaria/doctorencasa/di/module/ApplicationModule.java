package com.unidadcoronaria.doctorencasa.di.module;

import android.arch.persistence.room.Room;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.database.DoctorEnCasaDB;
import com.unidadcoronaria.doctorencasa.threading.AsyncRunner;
import com.unidadcoronaria.doctorencasa.threading.JobExecutor;

import java.util.concurrent.Executor;

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
    Executor provideThreadExecutor(){
        return new JobExecutor();
    }

    @Provides
    @Singleton
    AsyncRunner provideAsyncRunner(Executor executor){
        return new AsyncRunner(executor);
    }
}
