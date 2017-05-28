package com.unidadcoronaria.doctorencasa.di.module;

import android.arch.persistence.room.Room;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.dao.ProviderDAO;
import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.database.DoctorEnCasaDB;
import com.unidadcoronaria.doctorencasa.di.UserScope;
import com.unidadcoronaria.doctorencasa.network.rest.ProviderService;
import com.unidadcoronaria.doctorencasa.repository.ProviderRepository;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.threading.AsyncRunner;
import com.unidadcoronaria.doctorencasa.threading.JobExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */

@Module
public class LoginModule {

    @Provides
    @UserScope
    ProviderService provideProviderService(Retrofit retrofit){
        return retrofit.create(ProviderService.class);
    }

    @Provides
    @UserScope
    ProviderRepository provideProviderRepository(ProviderService providerService, ProviderDAO providerDAO, AsyncRunner asyncRunner){
        return new ProviderRepository(providerService, providerDAO, asyncRunner);
    }

    @Provides
    @UserScope
    UserRepository provideUserRepository(UserDAO userDAO){
        return new UserRepository(userDAO);
    }

    @Provides
    @UserScope
    UserDAO provideUserDAO(DoctorEnCasaDB db){
       return db.userDAO();
    }

    @Provides
    @UserScope
    ProviderDAO provideProviderDAO(DoctorEnCasaDB db){
        return db.providerDAO();
    }
}
