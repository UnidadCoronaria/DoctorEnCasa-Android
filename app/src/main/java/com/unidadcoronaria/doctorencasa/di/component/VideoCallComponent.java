package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.di.module.UserModule;
import com.unidadcoronaria.doctorencasa.fragment.NewCallFragment;
import com.unidadcoronaria.doctorencasa.fragment.VideoCallFragment;

import dagger.Component;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@PerActivity
@Component(modules =  UserModule.class, dependencies = ApplicationComponent.class)
public interface VideoCallComponent {

    void inject(VideoCallFragment fragment);

    void inject(NewCallFragment newCallFragment);
}
