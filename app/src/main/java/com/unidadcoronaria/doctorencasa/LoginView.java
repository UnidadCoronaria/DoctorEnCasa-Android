package com.unidadcoronaria.doctorencasa;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface LoginView extends BaseView {

    void onEmptyUsername();

    void onEmptyPassword();

    void onLoginError();

    void onSaveAffiliateSuccess(Boolean passwordExpired);

    void onSaveAffiliateError();

    void invalidPasswordFormat();

    void invalidUsernameFormat();
}
