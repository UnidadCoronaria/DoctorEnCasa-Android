package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.User;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface SplashView extends BaseView {

    void onAffiliateUpdated();

    void onGetAffiliateError();

    void onEmptyAffiliate();

}
