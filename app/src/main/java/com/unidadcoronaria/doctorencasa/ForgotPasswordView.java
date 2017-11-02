package com.unidadcoronaria.doctorencasa;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface ForgotPasswordView extends BaseView {

    void onEmptyEmail();

    void onInvalidFormatEmail();

    void onForgotPasswordError();

    void onForgotPasswordSuccess();
}
