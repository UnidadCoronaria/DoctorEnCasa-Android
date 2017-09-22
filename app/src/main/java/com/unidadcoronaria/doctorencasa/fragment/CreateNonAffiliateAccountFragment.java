package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateNonAffiliateAccountView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.presenter.CreateNonAffiliateAccountPresenter;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateNonAffiliateAccountFragment extends BaseFragment<CreateNonAffiliateAccountPresenter> implements CreateNonAffiliateAccountView {

    public static final String TAG = "CreateNonAffiliateAccountFragment";


    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_create_non_affiliate_account;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerCreateAccountComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
        mPresenter.onStart();
    }

}

