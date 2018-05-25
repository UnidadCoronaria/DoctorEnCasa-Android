package com.unidadcoronaria.doctorencasa.presenter;

import com.unidadcoronaria.doctorencasa.TermsAndConditionsView;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;
import com.unidadcoronaria.doctorencasa.usecase.network.UpdateAffiliateUseCase;

import javax.inject.Inject;

import retrofit2.adapter.rxjava2.HttpException;

import static com.unidadcoronaria.doctorencasa.util.ValidationUtil.validPasswordFormat;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class TermsAndConditionsPresenter extends BasePresenter<TermsAndConditionsView> {

    @Inject
    public TermsAndConditionsPresenter() {
    }

}
