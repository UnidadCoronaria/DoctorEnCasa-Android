package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;

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

    void onChangePasswordSuccess(UserInfo userInfo);

    void onChangePasswordError(GenericResponseDTO errorResponse);

    void onChangePasswordStart();

}
