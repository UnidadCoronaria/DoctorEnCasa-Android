package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface AffiliateDataView extends BaseView {

    void isPasswordEmpty();

    void isPasswordRepeatEmpty();

    void invalidPasswordFormat();

    void invalidPasswordRepeatFormat();

    void notMatchingPassword();

    void isEmailEmpty();

    void invalidEmailFormat();

    void onCreateUserSuccess();

    void onCreateUserError(GenericResponseDTO errorResponse);

    void onCreateUserStart();

    void onGroupOwnerRetrieved(Affiliate affiliate);

    void onEmptyAffiliateNumber();

    void onGroupOwnerError();

    void notAcceptedTerms();

}
