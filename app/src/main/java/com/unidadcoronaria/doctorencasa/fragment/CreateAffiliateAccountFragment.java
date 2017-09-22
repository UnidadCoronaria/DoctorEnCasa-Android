package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateAffiliateAccountView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.adapter.ProviderAdapter;
import com.unidadcoronaria.doctorencasa.adapter.AffiliateAdapter;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.presenter.CreateAffiliateAccountPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateAffiliateAccountFragment extends BaseFragment<CreateAffiliateAccountPresenter> implements CreateAffiliateAccountView {

    public static final String TAG = "CreateAffiliateAccountFragment";

    @BindView(R.id.fragment_create_affiliate_account_affiliate_number)
    protected EditText vAffiliateNumber;

    @BindView(R.id.fragment_create_affiliate_account_provider_list)
    protected RecyclerView vProviderList;

    @BindView(R.id.fragment_create_affiliate_account_affiliate_list)
    protected RecyclerView vAffiliateList;

    @BindView(R.id.fragment_create_affiliate_account_search)
    protected Button vSearchButton;

    private ProviderAdapter vProviderAdapter;
    private AffiliateAdapter mAffiliateAdapter;


    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_create_affiliate_account;
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
        vProviderList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.onStart();
    }


    @OnClick(R.id.fragment_create_affiliate_account_search)
    public void onGetAffiliateDataClick(){
        if(mAffiliateAdapter.getSelectedAffiliate() != null ){
            callback.replaceFragment(AffiliateDataFragment.newIntance(mAffiliateAdapter.getSelectedAffiliate()));
        } else {
            mPresenter.getAffiliateData(vAffiliateNumber.getText().toString(),vProviderAdapter.getSelectedProvider());
        }
    }

    @Override
    public void onAffiliateListRetrieved(List<Affiliate> affiliateList) {
        mAffiliateAdapter = new AffiliateAdapter(affiliateList);
        vAffiliateList.setAdapter(mAffiliateAdapter);
        vSearchButton.setText(getString(R.string.continue_to_next_screen));
    }


    @Override
    public void onEmptyAffiliateNumber() {
        Log.d("onEmptyAffiliateNumber","There is no user with the affiliate number");
    }

    @Override
    public void onGetAffiliateListError() {
        Log.e("onGetAffiliateListError","Error getting list of users");
    }

    @Override
    public void onProviderListRetrieved(List<Provider> providerList) {
        vProviderAdapter = new ProviderAdapter(providerList);
        vProviderList.setAdapter(vProviderAdapter);
    }

    @Override
    public void onProviderListError() {
        Log.e("onProviderListError","Error getting list of provider");
    }
}

