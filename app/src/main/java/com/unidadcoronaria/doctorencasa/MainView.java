package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface MainView extends BaseView {

    void onAffiliateRetrieved(Affiliate affiliate);

    void onGetAffiliateError();

    void onLogout();

    void onLogoutError();
}
