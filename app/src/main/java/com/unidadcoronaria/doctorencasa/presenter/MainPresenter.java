package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.MainView;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.usecase.database.DeleteAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.database.LoadAffiliateUseCase;
import com.unidadcoronaria.doctorencasa.usecase.network.LogoutUseCase;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private LoadAffiliateUseCase mLoadAffiliateUseCase;
    private DeleteAffiliateUseCase mDeleteAffiliateUseCase;
    private LogoutUseCase mLogoutUseCase;

    @Inject
    public MainPresenter(LoadAffiliateUseCase mLoadAffiliateUseCase, DeleteAffiliateUseCase mDeleteAffiliateUseCase, LogoutUseCase mLogoutUseCase) {
        this.mLoadAffiliateUseCase = mLoadAffiliateUseCase;
        this.mDeleteAffiliateUseCase = mDeleteAffiliateUseCase;
        this.mLogoutUseCase = mLogoutUseCase;
    }

    public void getAffiliate(){
        mLoadAffiliateUseCase.execute(affiliate -> {
            view.onAffiliateRetrieved((Affiliate) affiliate);
        }, throwable -> {
            view.onLoadAffiliateError();
        });
    }


    @Override
    public void onStop(){
        super.onStop();
        mLoadAffiliateUseCase.unsubscribe();
        mDeleteAffiliateUseCase.unsubscribe();
        mLogoutUseCase.unsubscribe();
    }

    public void logout() {
        mDeleteAffiliateUseCase.execute(() -> { SessionUtil.logout(); } , throwable -> view.onDeleteAffiliateError());
    }

}
