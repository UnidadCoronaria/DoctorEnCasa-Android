package com.unidadcoronaria.doctorencasa.presenter;

import android.os.Handler;

import com.unidadcoronaria.doctorencasa.SplashView;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SplashPresenter extends BasePresenter<SplashView> {

    @Inject
    public SplashPresenter() {
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            if(SessionUtil.isAuthenticated()) {
                view.userAuthenticated();
            } else {
                view.onEmptyAffiliate();
            }
        },2000);

    }

}
