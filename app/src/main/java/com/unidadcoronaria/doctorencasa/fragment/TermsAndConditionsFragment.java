package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.TermsAndConditionsView;
import com.unidadcoronaria.doctorencasa.di.component.DaggerSettingsComponent;
import com.unidadcoronaria.doctorencasa.presenter.TermsAndConditionsPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class TermsAndConditionsFragment extends BaseFragment<TermsAndConditionsPresenter> implements TermsAndConditionsView{

    public static final String TAG = "TermsAndConditionsFragment";

    @BindView(R.id.fragment_terms_and_conditions_detail)
    protected TextView vDetail;

    public static TermsAndConditionsFragment newInstance() {
        TermsAndConditionsFragment instance = new TermsAndConditionsFragment();
        return instance;
    }

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_terms_and_conditions;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerSettingsComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
        vDetail.setText(Html.fromHtml(getString(R.string.terms_detail)));
    }

    @OnClick(R.id.fragment_terms_and_conditions_accept)
    public void onAcceptClick(){
        getActivity().finish();
    }

}

