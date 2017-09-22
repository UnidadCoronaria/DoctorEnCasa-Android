package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.User;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface LoginView extends BaseView {

    void onAffiliateRetrieved();

    void onEmptyUsername();

    void onEmptyPassword();

    void onLoginError();

    void onSaveAffiliateSuccess();

    void onSaveAffiliateError();
}
