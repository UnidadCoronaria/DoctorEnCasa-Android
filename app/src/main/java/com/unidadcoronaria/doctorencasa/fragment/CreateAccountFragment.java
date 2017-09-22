package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateAccountView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.presenter.CreateAccountPresenter;

import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateAccountFragment extends BaseFragment<CreateAccountPresenter> implements CreateAccountView {

    public static final String TAG = "CreateAccountFragment";

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_create_account;
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

    @OnClick(R.id.fragment_create_account_type_affiliate)
    public void onAffiliateButtonClick(){
        mPresenter.onAffiliateClick();
    }

    @OnClick(R.id.fragment_create_account_type_non_affiliate)
    public void onNonAffiliateButtonClick(){
        mPresenter.onNonAffiliateClick();
    }

    @Override
    public void onAffiliateClick() {
        this.callback.replaceFragment(new CreateAffiliateAccountFragment());
    }

    @Override
    public void onNonAffiliateClick() {
        this.callback.replaceFragment(new CreateNonAffiliateAccountFragment());
    }
}

