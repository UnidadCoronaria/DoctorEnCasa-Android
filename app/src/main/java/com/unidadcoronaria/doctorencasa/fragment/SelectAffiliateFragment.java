package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateAccountView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.SelectAffiliateView;
import com.unidadcoronaria.doctorencasa.adapter.AffiliateAdapter;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.presenter.SelectAffiliatePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class SelectAffiliateFragment extends BaseFragment<SelectAffiliatePresenter> implements SelectAffiliateView {

    public static final String TAG = "SelectAffiliateFragment";
    public static final String PROVIDER_KEY = " com.unidadcoronaria.doctorencasa.fragment.SelectAffiliateFragment.PROVIDER_KEY";


    @BindView(R.id.fragment_select_affiliate_account_affiliate_number)
    protected EditText vAffiliateNumber;

    @BindView(R.id.fragment_select_affiliate_account_affiliate_number_layout)
    protected TextInputLayout vAffiliateNumberLayout;

    @BindView(R.id.fragment_select_affiliate_account_affiliate_list)
    protected RecyclerView vAffiliateList;

    @BindView(R.id.fragment_select_affiliate_account_continue)
    protected FloatingActionButton vContinueButton;

    @BindView(R.id.fragment_select_affiliate_account_search)
    protected ImageView vSearchButton;

    private AffiliateAdapter mAffiliateAdapter;
    private Provider mProvider;
    private CreateAccountView mCallback;


    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_select_affiliate;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    public static SelectAffiliateFragment newInstance(Provider provider){
        SelectAffiliateFragment instance = new SelectAffiliateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROVIDER_KEY, provider);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected void inject() {
        DaggerCreateAccountComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(PROVIDER_KEY)){
            mProvider = (Provider) getArguments().getSerializable(PROVIDER_KEY);
        }
        mPresenter.setView(this);
        vAffiliateList.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        mCallback.setToolbarTitle(getResources().getString(R.string.search_affiliate));
        mCallback.setBackVisibilityInToolbar(false);
    }


    @OnClick(R.id.fragment_select_affiliate_account_search)
    public void onSearchClick(){
        vAffiliateNumberLayout.setError(null);
        vAffiliateNumberLayout.setErrorEnabled(false);
        vProgress.setVisibility(View.VISIBLE);
        mPresenter.getAffiliateData(vAffiliateNumber.getText().toString(), mProvider.getId());
        hideSoftKeyboard();
    }


    @OnClick(R.id.fragment_select_affiliate_account_back)
    public void onBackClick(){
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.fragment_select_affiliate_account_continue)
    public void onContinueClick(){
        if(mAffiliateAdapter.getSelectedUser() != null ){
            if(!mAffiliateAdapter.getSelectedUser().isUser()) {
                Affiliate selectedAffiliate = mAffiliateAdapter.getSelectedUser();
                selectedAffiliate.setProvider(mProvider);
                mCallback.navigateToCreateUser(selectedAffiliate);
            } else {
                Toast.makeText(getActivity(), "El afiliado seleccionado ya posee una cuenta.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Seleccione un afiliado para el cual creará la cuenta.", Toast.LENGTH_LONG).show();
        }
    }

    private void hideSoftKeyboard() {
        View view = this.getView().getRootView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onAffiliateListRetrieved(List<Affiliate> affiliateList) {
        mAffiliateAdapter = new AffiliateAdapter(affiliateList);
        vAffiliateList.setAdapter(mAffiliateAdapter);
        vAffiliateList.setVisibility(View.VISIBLE);
        vContinueButton.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
        vAffiliateNumberLayout.setError(null);
        vAffiliateNumberLayout.setErrorEnabled(false);
    }


    @Override
    public void onEmptyAffiliateNumber() {
        Log.d("onEmptyAffiliateNumber","There is no user with the affiliate number");
        vProgress.setVisibility(View.GONE);
        vAffiliateList.setVisibility(View.GONE);
        vAffiliateNumberLayout.setError(getString(R.string.no_affiliate_number));
        vAffiliateNumberLayout.setErrorEnabled(true);
    }

    @Override
    public void onGetAffiliateListError() {
        Log.e("onGetAffiliateListError","Error getting list of users");
        vProgress.setVisibility(View.GONE);
        vAffiliateList.setVisibility(View.GONE);
        vAffiliateNumberLayout.setError(getString(R.string.error_getting_affiliate_list));
        vAffiliateNumberLayout.setErrorEnabled(true);
    }

}

