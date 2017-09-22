package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.User;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface MainView extends BaseView {

    void onAffiliateRetrieved(Affiliate affiliate);

    void onLoadAffiliateError();

    void onDeleteAffiliate();

    void onDeleteAffiliateError();
}
