package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.Provider;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface CreateAccountView extends BaseView {


    void navigateToCreateUser(Provider provider);

    void setToolbarTitle(String title);

    void setBackVisibilityInToolbar(boolean isBackVisible);
}
