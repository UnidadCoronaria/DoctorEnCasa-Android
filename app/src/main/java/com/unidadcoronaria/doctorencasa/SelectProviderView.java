package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Provider;

import java.util.List;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface SelectProviderView extends BaseView {

    void onProviderListRetrieved(List<Provider> providerList);

    void onProviderListError();

}
