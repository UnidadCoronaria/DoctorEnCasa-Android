package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.PromotionView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.BaseActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerPromotionComponent;
import com.unidadcoronaria.doctorencasa.presenter.PromotionPresenter;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class PromotionFragment extends BaseFragment<PromotionPresenter> implements PromotionView {

    public static final String TAG = "PromotionFragment";

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_promotion;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
       DaggerPromotionComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    public static PromotionFragment newInstance() {
        return new PromotionFragment();
    }
}
