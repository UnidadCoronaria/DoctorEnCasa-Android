package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.MainView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.LoginActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerHomeComponent;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class HomeFragment extends BaseFragment<MainPresenter> implements MainView{

    @BindView(R.id.fragment_main_text)
    TextView vText;

    public static final String TAG = "MainFragment";

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerHomeComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
        mPresenter.getAffiliate();
    }

    public void onAffiliateRetrieved(Affiliate user){
        vText.setText(user.getFirstName()+" "+user.getLastName());
    }

    @Override
    public void onLoadAffiliateError() {
        vText.setText("No se pudieron obtener los datos del usuario");
    }

    @Override
    public void onDeleteAffiliate() {
        startActivity(LoginActivity.getStartIntent(getActivity()));
        getActivity().finish();
    }

    @Override
    public void onDeleteAffiliateError() {

    }


    @OnClick(R.id.fragment_main_logout)
    public void onLogoutClick(){
        mPresenter.logout();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
