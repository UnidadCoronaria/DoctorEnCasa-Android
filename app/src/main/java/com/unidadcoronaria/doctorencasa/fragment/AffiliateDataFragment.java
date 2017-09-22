package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.presenter.UserDataPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class AffiliateDataFragment extends BaseFragment<UserDataPresenter> implements AffiliateDataView {

    public static final String TAG = "UserDataFragment";
    public static final String AFFILIATE_KEY = " com.unidadcoronaria.doctorencasa.fragment.UserDataFragment.AFFILIATE_KEY";

    @BindView(R.id.fragment_affiliate_username)
    protected EditText vUsername;

    @BindView(R.id.fragment_affiliate_password)
    protected EditText vPassword;

    @BindView(R.id.fragment_affiliate_name)
    protected EditText vName;

    @BindView(R.id.fragment_affiliate_email)
    protected EditText vEmail;

    @BindView(R.id.fragment_affiliate_phone)
    protected EditText vPhone;

    @BindView(R.id.fragment_affiliate_birthdate)
    protected EditText vBirthDate;

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Affiliate mAffiliate;

    public static AffiliateDataFragment newIntance(Affiliate affiliate){
        AffiliateDataFragment instance =  new AffiliateDataFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AFFILIATE_KEY, affiliate);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_affiliate_data;
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
        if(getArguments() != null && getArguments().containsKey(AFFILIATE_KEY)){
            mAffiliate = (Affiliate) getArguments().get(AFFILIATE_KEY);
            setAffiliateData();
        }
    }

    private void setAffiliateData() {
        vName.setText(mAffiliate.getFirstname()+" "+mAffiliate.getLastname());
        vEmail.setText(mAffiliate.getEmail());
        vPhone.setText(mAffiliate.getCellphone());
        Calendar birthday = Calendar.getInstance();
        birthday.setTimeInMillis(mAffiliate.getBirthDate());
        vBirthDate.setText(mSimpleDateFormat.format(birthday.getTime()));
    }


    @OnClick(R.id.fragment_affiliate_create)
    public void onCreateAccountClick() {
        mPresenter.createAccount(mAffiliate.getAffiliateId(), mAffiliate.getProviderId(),
                vUsername.getText().toString(), vPassword.getText().toString());
    }


}

