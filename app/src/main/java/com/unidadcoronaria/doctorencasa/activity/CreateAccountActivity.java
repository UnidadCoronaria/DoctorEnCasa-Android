package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.CreateAccountView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.fragment.CreateUserFragment;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.SelectAffiliateFragment;
import com.unidadcoronaria.doctorencasa.fragment.SelectProviderFragment;
import com.unidadcoronaria.doctorencasa.fragment.FragmentContainer;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateAccountActivity extends BaseActivity implements FragmentContainer, CreateAccountView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_create_account;
    }

    @Override
    protected BaseFragment getFragment() {
        return new SelectProviderFragment();
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    public static Intent getStartIntent(Context context){
        return new Intent(context, CreateAccountActivity.class);
    }

    @Override
    public void navigateToSelectAffiliate(Integer provider) {
         showAndHideFragment(SelectProviderFragment.TAG, SelectAffiliateFragment.newInstance(provider));
        setToolbarTitle(getResources().getString(R.string.search_affiliate));
    }

    @Override
    public void navigateToCreateUser(Affiliate affiliate) {
        showAndHideFragment(SelectAffiliateFragment.TAG, CreateUserFragment.newInstance(affiliate));
        setToolbarTitle(getResources().getString(R.string.create_account));
    }

    @Override
    public void setBackVisibilityInToolbar(boolean isBackVisible) {
        super.setBackVisibilityInToolbar(isBackVisible);
    }
}
