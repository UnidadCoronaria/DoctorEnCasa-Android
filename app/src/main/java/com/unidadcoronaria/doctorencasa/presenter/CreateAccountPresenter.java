package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.CreateAccountView;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class CreateAccountPresenter extends BasePresenter<CreateAccountView> {

    @Inject
    public CreateAccountPresenter() {
    }

    public void onAffiliateClick(){
        view.onAffiliateClick();
    }

    public void onNonAffiliateClick(){
        view.onNonAffiliateClick();
    }

}
