package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.di.module.UserModule;
import com.unidadcoronaria.doctorencasa.fragment.SettingsFragment;

import dagger.Component;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@PerActivity
@Component(modules =  UserModule.class, dependencies = ApplicationComponent.class)
public interface SettingsComponent {

    void inject(SettingsFragment settingsFragment);

    void inject(MainActivity mainActivity);
}
