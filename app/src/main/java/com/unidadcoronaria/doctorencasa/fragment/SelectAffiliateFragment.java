package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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


    @BindView(R.id.fragment_create_affiliate_account_affiliate_number)
    protected EditText vAffiliateNumber;

    @BindView(R.id.fragment_create_affiliate_account_affiliate_list)
    protected RecyclerView vAffiliateList;

    @BindView(R.id.fragment_create_affiliate_account_continue)
    protected FloatingActionButton vContinueButton;

    @BindView(R.id.fragment_create_affiliate_account_search)
    protected ImageView vSearchButton;

    private AffiliateAdapter mAffiliateAdapter;
    private int mProvider;
    private CreateAccountView mCallback;


    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_select_affiliate;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    public static SelectAffiliateFragment newInstance(Integer provider){
        SelectAffiliateFragment instance = new SelectAffiliateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PROVIDER_KEY, provider);
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
            mProvider =  getArguments().getInt(PROVIDER_KEY);
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


    @OnClick(R.id.fragment_create_affiliate_account_search)
    public void onSearchClick(){
        vProgress.setVisibility(View.VISIBLE);
        mPresenter.getAffiliateData(vAffiliateNumber.getText().toString(),mProvider);
        hideSoftKeyboard();
    }


    @OnClick(R.id.fragment_create_affiliate_account_back)
    public void onBackClick(){
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.fragment_create_affiliate_account_continue)
    public void onContinueClick(){
        if(mAffiliateAdapter.getSelectedAffiliate() != null){
            Affiliate selectedAffiliate = mAffiliateAdapter.getSelectedAffiliate();
            selectedAffiliate.setProviderId(mProvider);
            mCallback.navigateToCreateUser(selectedAffiliate);
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
        vContinueButton.setVisibility(View.VISIBLE);
        vProgress.setVisibility(View.GONE);
    }


    @Override
    public void onEmptyAffiliateNumber() {
        Log.d("onEmptyAffiliateNumber","There is no user with the affiliate number");
    }

    @Override
    public void onGetAffiliateListError() {
        vProgress.setVisibility(View.GONE);
        Log.e("onGetAffiliateListError","Error getting list of users");
    }

}
