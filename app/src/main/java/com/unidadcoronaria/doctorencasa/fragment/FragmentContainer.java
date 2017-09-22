package com.unidadcoronaria.doctorencasa.fragment;

/**
 * Created by AGUSTIN.BALA on 6/30/2017.
 */

public interface FragmentContainer {

    void replaceFragment(BaseFragment baseFragment);

    void showAndHideFragment(String hideTag, BaseFragment baseFragment);
}
