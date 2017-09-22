package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;

import java.util.List;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface CreateAffiliateAccountView extends BaseView {

    void onAffiliateListRetrieved(List<Affiliate> affiliateList);

    void onEmptyAffiliateNumber();

    void onGetAffiliateListError();

    void onProviderListRetrieved(List<Provider> providerList);

    void onProviderListError();

}
