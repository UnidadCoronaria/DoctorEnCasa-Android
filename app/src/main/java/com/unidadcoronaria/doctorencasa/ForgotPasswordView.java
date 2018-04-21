package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface ForgotPasswordView extends BaseView {

    void onEmptyEmail();

    void onInvalidFormatEmail();

    void onForgotPasswordError(GenericResponseDTO errorResponse);

    void onForgotPasswordSuccess();
}
