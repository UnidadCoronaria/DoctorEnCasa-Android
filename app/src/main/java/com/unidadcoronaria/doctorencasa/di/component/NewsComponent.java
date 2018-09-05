package com.unidadcoronaria.doctorencasa.di.component;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.fragment.NewsFragment;

import dagger.Component;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface NewsComponent {

    void inject(NewsFragment fragment);

}
