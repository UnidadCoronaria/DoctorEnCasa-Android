package com.unidadcoronaria.doctorencasa.di.module;

import com.unidadcoronaria.doctorencasa.dao.AffiliateDAO;
import com.unidadcoronaria.doctorencasa.database.DoctorEnCasaDB;
import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.network.rest.AffiliateService;
import com.unidadcoronaria.doctorencasa.network.rest.UserService;
import com.unidadcoronaria.doctorencasa.presenter.LoginPresenter;
import com.unidadcoronaria.doctorencasa.presenter.SplashPresenter;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.usecase.database.LoadAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.LoginUseCase;
import com.unidadcoronaria.doctorencasa.usecase.database.SaveAffiliateUseCase;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */

@Module
public class UserModule {

    @Provides
    @PerActivity
    UserService provideUserService(Retrofit retrofit){
        return retrofit.create(UserService.class);
    }

    @Provides
    @PerActivity
    AffiliateService provideAffiliateService(Retrofit retrofit){
        return retrofit.create(AffiliateService.class);
    }

    @Provides
    @PerActivity
    UserRepository provideUserRepository(UserService userService){
        return new UserRepository(userService);
    }

    @Provides
    @PerActivity
    AffiliateDAO provideUserDAO(DoctorEnCasaDB db){
       return db.affiliateDAO();
    }


    @Provides
    @PerActivity
    LoginPresenter provideLoginPresenter(LoadAffiliateUseCase mGetUserUseCase, LoginUseCase mLoginUseCase, SaveAffiliateUseCase mSaveUserUseCase){
        return new LoginPresenter(mGetUserUseCase, mLoginUseCase, mSaveUserUseCase);
    }

    @Provides
    @PerActivity
    SplashPresenter provideSplashPresenter(GetAffiliateUseCase mLoadAffiliateUseCase, SaveAffiliateUseCase mSaveUserUseCase){
        return new SplashPresenter(mLoadAffiliateUseCase, mSaveUserUseCase);
    }
}
