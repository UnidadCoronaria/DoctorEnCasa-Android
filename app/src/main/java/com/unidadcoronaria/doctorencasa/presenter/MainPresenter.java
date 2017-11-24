package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.MainView;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.usecase.network.GetAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.LogoutUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private GetAffiliateUseCase mGetAffiliateUseCase;
    private LogoutUseCase mLogoutUseCase;

    @Inject
    public MainPresenter(GetAffiliateUseCase mGetAffiliateUseCase, LogoutUseCase mLogoutUseCase) {
        this.mGetAffiliateUseCase = mGetAffiliateUseCase;
        this.mLogoutUseCase = mLogoutUseCase;
    }

    public void getAffiliate(){
        mGetAffiliateUseCase.execute(user -> {
            view.onAffiliateRetrieved((Affiliate) user);
        }, throwable -> {
            view.onGetAffiliateError();
        });
    }


    @Override
    public void onStop(){
        super.onStop();
        mLogoutUseCase.unsubscribe();
        mGetAffiliateUseCase.unsubscribe();
    }

    public void logout() {
        mLogoutUseCase.execute(() -> {
            view.onLogout();
            SessionUtil.logout();
        }, throwable -> view.onLogoutError());

    }

}
