package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Provider;

import java.util.List;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface SelectAffiliateView extends BaseView {

    void onAffiliateListRetrieved(List<Affiliate> affiliateList);

    void onEmptyAffiliateNumber();

    void onGetAffiliateListError();

}
