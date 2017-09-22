package com.unidadcoronaria.doctorencasa.presenter;

import android.os.Handler;
import android.util.Log;

import com.unidadcoronaria.doctorencasa.SplashView;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.usecase.database.SaveAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SplashPresenter extends BasePresenter<SplashView> {

    private GetAffiliateUseCase mGetUserUseCase;
    private SaveAffiliateUseCase mSaveUserUseCase;

    @Inject
    public SplashPresenter(GetAffiliateUseCase getAffiliateUseCase, SaveAffiliateUseCase mSaveUserUseCase) {
        this.mGetUserUseCase = getAffiliateUseCase;
        this.mSaveUserUseCase = mSaveUserUseCase;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            if(SessionUtil.isAuthenticated()) {
                // Update User Information using current token
                getAffiliate();
            }
            else {
                // User not logged in, redirect to login screen
                view.onEmptyAffiliate();
            }
        },2000);

    }


    private void getAffiliate(){
        mGetUserUseCase.execute(affiliate -> saveAffiliate((Affiliate) affiliate), throwable -> {
            Log.e("SplashPresenter", "Error getting affiliate info from API");
            view.onGetAffiliateError();
        });
    }

    private void saveAffiliate(Affiliate affiliate){
        mSaveUserUseCase.setAffiliate(affiliate);
        mSaveUserUseCase.execute(o -> view.onAffiliateUpdated(), throwable -> {
            Log.e("SplashPresenter", "Error saving user to DB");
            view.onGetAffiliateError();
        });
    }

    @Override
    public void onStop(){
        super.onStop();
        mGetUserUseCase.unsubscribe();
        mSaveUserUseCase.unsubscribe();
    }

}