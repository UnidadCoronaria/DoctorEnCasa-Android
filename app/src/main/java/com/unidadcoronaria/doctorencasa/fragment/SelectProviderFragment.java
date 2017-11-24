package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateAccountView;
import com.unidadcoronaria.doctorencasa.SelectProviderView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.adapter.ProviderAdapter;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.presenter.SelectProviderPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SelectProviderFragment extends BaseFragment<SelectProviderPresenter> implements SelectProviderView, ProviderAdapter.Callback {

    public static final String TAG = "SelectProviderFragment";


    @BindView(R.id.fragment_create_affiliate_account_provider_list)
    protected RecyclerView vProviderList;

    @BindView(R.id.fragment_create_affiliate_account_continue)
    protected FloatingActionButton vContinueButton;


    private ProviderAdapter vProviderAdapter;
    private CreateAccountView mCallback;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_select_provider;
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
        vProgress.setVisibility(View.VISIBLE);
        vProviderList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof CreateAccountView){
            mCallback = (CreateAccountView) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mCallback.setToolbarTitle(getResources().getString(R.string.choose_provider));
        mCallback.setBackVisibilityInToolbar(true);
    }

    @OnClick(R.id.fragment_create_affiliate_account_continue)
    public void onContinueClick(){
        if(vProviderAdapter.getSelectedProvider() != null){
            mCallback.navigateToSelectAffiliate(vProviderAdapter.getSelectedProvider());
        } else {
            Toast.makeText(getActivity(), "Seleccione la empresa a la cual se encuentra afiliada.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onProviderListRetrieved(List<Provider> providerList) {
        vProviderAdapter = new ProviderAdapter(providerList, this);
        vProviderList.setAdapter(vProviderAdapter);
        vProgress.setVisibility(View.GONE);
    }

    @Override
    public void onProviderListError() {
        vProgress.setVisibility(View.GONE);
        Log.e("onProviderListError","Error getting list of provider");
    }

    @Override
    public void onItemSelected() {
        vContinueButton.setVisibility(View.VISIBLE);
    }
}

