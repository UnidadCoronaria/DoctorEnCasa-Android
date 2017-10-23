package com.unidadcoronaria.doctorencasa;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface AffiliateDataView extends BaseView {

    void isUsernameEmpty();

    void isPasswordEmpty();

    void isPasswordRepeatEmpty();

    void invalidUsernameFormat();

    void invalidPasswordFormat();

    void invalidPasswordRepeatFormat();

    void notMatchingPassword();

    void isEmailEmpty();

    void invalidEmailFormat();

}
