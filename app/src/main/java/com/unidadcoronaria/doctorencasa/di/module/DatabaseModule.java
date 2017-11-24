package com.unidadcoronaria.doctorencasa.di.module;

import android.arch.persistence.room.Room;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.BuildConfig;
import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.database.DoctorEnCasaDB;
import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
public class DatabaseModule {


     @Provides
     DoctorEnCasaDB provideDB(){
         return Room.databaseBuilder(App.getInstance(),
               DoctorEnCasaDB.class, DoctorEnCasaDB.DATABASE_NAME).build();
     }

     @Provides
     UserDAO provideUserDAO(DoctorEnCasaDB db){
        return db.userDAO();
     }


}
