package com.unidadcoronaria.doctorencasa.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.unidadcoronaria.doctorencasa.App;
//import com.unidadcoronaria.doctorencasa.di.component.DaggerHomeComponent;
import com.unidadcoronaria.doctorencasa.usecase.network.UpdateFCMTokenUseCase;
import com.unidadcoronaria.doctorencasa.util.SharedPreferencesHelper;

import javax.inject.Inject;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by AGUSTIN.BALA on 11/12/2016.
 */

public class FCMInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "FCMInstanceIdService";

    //@Inject
    UpdateFCMTokenUseCase mUpdateFCMTokenUseCase;


    public FCMInstanceIdService() {
       // DaggerHomeComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Token: " + fcmToken);
        SharedPreferencesHelper.putString(App.getInstance(), "FCM_TOKEN", fcmToken);
       // sendTokenToServer(fcmToken);
    }

    private void sendTokenToServer(String fcmToken) {
        mUpdateFCMTokenUseCase.setData(fcmToken);
        mUpdateFCMTokenUseCase.execute(() -> Log.i(TAG, "Success saving FCM Token"),
                        throwable -> Log.e(TAG, "Error saving FCM Token", throwable));
    }

}
