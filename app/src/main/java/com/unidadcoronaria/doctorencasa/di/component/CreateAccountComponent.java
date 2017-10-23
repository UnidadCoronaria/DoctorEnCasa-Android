package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.di.module.ProviderModule;
import com.unidadcoronaria.doctorencasa.di.module.UserModule;
import com.unidadcoronaria.doctorencasa.fragment.SelectAffiliateFragment;
import com.unidadcoronaria.doctorencasa.fragment.SelectProviderFragment;
import com.unidadcoronaria.doctorencasa.fragment.CreateUserFragment;

import dagger.Component;

/**
 * Created by AGUSTIN.BALA on 6/2/2017.
 */

@PerActivity
@Component(modules = {UserModule.class, ProviderModule.class}, dependencies = ApplicationComponent.class)
public interface CreateAccountComponent {
    void inject(SelectProviderFragment fragment);

    void inject(CreateUserFragment userDataFragment);

    void inject(SelectAffiliateFragment selectAffiliateFragment);
}
