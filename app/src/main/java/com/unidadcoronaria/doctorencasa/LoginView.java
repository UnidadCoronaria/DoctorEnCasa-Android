package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.UserInfo;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface LoginView extends BaseView {

    void onEmptyUsername();

    void onEmptyPassword();

    void onLoginError();

    void onSaveAffiliateSuccess(UserInfo userInfo, Boolean passwordExpired);

    void invalidPasswordFormat();

    void invalidUsernameFormat();

}