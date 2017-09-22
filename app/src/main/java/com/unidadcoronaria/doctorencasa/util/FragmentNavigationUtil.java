package com.unidadcoronaria.doctorencasa.util;


import android.support.v4.app.FragmentManager;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;

/**
 * Created by AGUSTIN.BALA on 5/28/2017.
 */

public class FragmentNavigationUtil {

    //region Fragment navigation
    public static void addFragment(FragmentManager fragmentManager, int fragmentContainer, BaseFragment fragment){
        fragmentManager.beginTransaction()
                .add(fragmentContainer, fragment, fragment.getFragmentTag()).commit();
    }

    public static void addAndHideFragment(String hideTag, FragmentManager fragmentManager, int fragmentContainer, BaseFragment fragment){
        if(fragmentManager.findFragmentByTag(hideTag) != null){
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(hideTag));
        }
        fragmentManager.beginTransaction()
                .add(fragmentContainer, fragment, fragment.getFragmentTag()).commit();
    }

    public static void replaceFragmentWithBackStack(FragmentManager fragmentManager, int fragmentContainer, BaseFragment fragment){
       fragmentManager
                .beginTransaction()
                .addToBackStack(fragment.getFragmentTag())
                .replace(fragmentContainer,
                        fragment, fragment.getFragmentTag()).commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager, int fragmentContainer, BaseFragment fragment){
        fragmentManager
                .beginTransaction()
                .replace(fragmentContainer,
                        fragment, fragment.getFragmentTag()).commit();
    }
    //endregion
}
