package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface CreateAccountView extends BaseView {

    void navigateToSelectAffiliate(Integer provider);

    void navigateToCreateUser(Affiliate affiliate);

    void setToolbarTitle(String title);

    void setBackVisibilityInToolbar(boolean isBackVisible);
}
