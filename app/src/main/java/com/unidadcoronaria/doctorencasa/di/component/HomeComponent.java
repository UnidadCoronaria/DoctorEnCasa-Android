package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.di.module.UserModule;
import com.unidadcoronaria.doctorencasa.fragment.HomeFragment;
import com.unidadcoronaria.doctorencasa.service.FCMInstanceIdService;

import dagger.Component;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@PerActivity
@Component(modules = UserModule.class, dependencies = ApplicationComponent.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);

    //void inject(FCMInstanceIdService fcmInstanceIdService);
}
