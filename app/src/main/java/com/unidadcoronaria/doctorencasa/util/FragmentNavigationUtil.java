package com.unidadcoronaria.doctorencasa.util;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

    public static Fragment getFragment(FragmentManager fragmentManager, String fragmentTag){
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    public static void addAndHideFragment(String hideTag, FragmentManager fragmentManager, int fragmentContainer, BaseFragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentManager.findFragmentByTag(hideTag) != null){
            fragmentTransaction.hide(fragmentManager.findFragmentByTag(hideTag));
        }
        fragmentTransaction
                .add(fragmentContainer, fragment, fragment.getFragmentTag()).addToBackStack(fragment.getFragmentTag()).commit();
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
