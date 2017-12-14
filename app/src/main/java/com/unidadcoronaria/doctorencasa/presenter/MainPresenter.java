package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.MainView;
import com.unidadcoronaria.doctorencasa.usecase.network.LogoutUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class MainPresenter extends BasePresenter<MainView> {


    private LogoutUseCase mLogoutUseCase;

    @Inject
    public MainPresenter(LogoutUseCase mLogoutUseCase) {
        this.mLogoutUseCase = mLogoutUseCase;
    }

    public void logout(){
        mLogoutUseCase.execute(() -> {
            SessionUtil.logout();
            view.onLogout();
        }, throwable -> view.onLogoutError());
    }

    @Override
    public void onStop(){
        super.onStop();
        mLogoutUseCase.unsubscribe();
    }

}
