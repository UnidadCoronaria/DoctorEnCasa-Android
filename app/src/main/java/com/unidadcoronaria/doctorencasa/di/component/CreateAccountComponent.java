package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.di.module.ProviderModule;
import com.unidadcoronaria.doctorencasa.di.module.UserModule;
import com.unidadcoronaria.doctorencasa.fragment.CreateAccountFragment;
import com.unidadcoronaria.doctorencasa.fragment.CreateAffiliateAccountFragment;
import com.unidadcoronaria.doctorencasa.fragment.CreateNonAffiliateAccountFragment;
import com.unidadcoronaria.doctorencasa.fragment.AffiliateDataFragment;

import dagger.Component;

/**
 * Created by AGUSTIN.BALA on 6/2/2017.
 */

@PerActivity
@Component(modules = {UserModule.class, ProviderModule.class}, dependencies = ApplicationComponent.class)
public interface CreateAccountComponent {
    void inject(CreateAccountFragment fragment);

    void inject(CreateAffiliateAccountFragment fragment);

    void inject(CreateNonAffiliateAccountFragment fragment);

    void inject(AffiliateDataFragment userDataFragment);
}
