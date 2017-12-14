package com.unidadcoronaria.doctorencasa.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.unidadcoronaria.doctorencasa.App;
//import com.unidadcoronaria.doctorencasa.di.component.DaggerHomeComponent;
import com.unidadcoronaria.doctorencasa.di.component.DaggerHomeComponent;
import com.unidadcoronaria.doctorencasa.usecase.network.UpdateFCMTokenUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;
import com.unidadcoronaria.doctorencasa.util.SharedPreferencesHelper;

import javax.inject.Inject;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by AGUSTIN.BALA on 11/12/2016.
 */

public class FCMInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "FCMInstanceIdService";

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Token: " + fcmToken);
        SessionUtil.saveFCMToken(fcmToken);
    }

}
