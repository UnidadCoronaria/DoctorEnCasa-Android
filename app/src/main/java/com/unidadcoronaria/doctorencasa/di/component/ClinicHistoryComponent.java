package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.di.module.ClinicHistoryModule;
import com.unidadcoronaria.doctorencasa.di.module.UserModule;
import com.unidadcoronaria.doctorencasa.fragment.ClinicHistoryFragment;
import com.unidadcoronaria.doctorencasa.fragment.PromotionFragment;

import dagger.Component;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@PerActivity
@Component(modules =  ClinicHistoryModule.class, dependencies = ApplicationComponent.class)
public interface ClinicHistoryComponent {

    void inject(ClinicHistoryFragment fragment);
}
