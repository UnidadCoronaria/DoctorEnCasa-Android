package com.unidadcoronaria.doctorencasa;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface ChangePasswordView extends BaseView {

    void isNewPasswordEmpty();

    void isCurrentPasswordEmpty();

    void isNewPasswordRepeatEmpty();

    void invalidNewPasswordFormat();

    void invalidCurrentPasswordFormat();

    void invalidNewPasswordRepeatFormat();

    void notMatchingPassword();

    void onChangePasswordSuccess();

    void onChangePasswordError();

    void onChangePasswordStart();

}
