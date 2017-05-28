package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.di.UserScope;
import com.unidadcoronaria.doctorencasa.di.module.HTTPModule;
import com.unidadcoronaria.doctorencasa.di.module.LoginModule;
import com.unidadcoronaria.doctorencasa.fragment.LoginFragment;
import com.unidadcoronaria.doctorencasa.viewmodel.CreateAccountViewModel;
import com.unidadcoronaria.doctorencasa.viewmodel.LoginViewModel;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@UserScope
@Component(modules = LoginModule.class, dependencies = ApplicationComponent.class)
public interface LoginComponent {
    void inject(LoginViewModel loginViewModel);
    void inject(CreateAccountViewModel createAccountViewModel);
}
