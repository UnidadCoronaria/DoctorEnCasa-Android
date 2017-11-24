package com.unidadcoronaria.doctorencasa.di.module;

import com.unidadcoronaria.doctorencasa.di.PerActivity;
import com.unidadcoronaria.doctorencasa.network.rest.VideoCallService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by AGUSTIN.BALA on 5/22/2017.
 */

@Module
public class VideoCallModule {

    @Provides
    @PerActivity
    VideoCallService provideVideoCallService(Retrofit retrofit){
        return retrofit.create(VideoCallService.class);
    }

}
