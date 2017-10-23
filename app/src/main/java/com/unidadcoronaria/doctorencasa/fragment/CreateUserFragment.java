package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateAccountView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.presenter.CreateUserPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateUserFragment extends BaseFragment<CreateUserPresenter> implements AffiliateDataView {

    public static final String TAG = "CreateUserFragment";
    public static final String AFFILIATE_KEY = " com.unidadcoronaria.doctorencasa.fragment.UserDataFragment.AFFILIATE_KEY";

    @BindView(R.id.fragment_affiliate_data_password)
    protected EditText vPassword;

    @BindView(R.id.fragment_affiliate_data_password_repeat)
    protected EditText vPasswordRepeat;

    @BindView(R.id.fragment_affiliate_data_email)
    protected EditText vEmail;

    @BindView(R.id.fragment_affiliate_data_username)
    protected EditText vUsername;

    @BindView(R.id.fragment_affiliate_title)
    protected TextView vTitle;


    private Affiliate mAffiliate;
    private CreateAccountView mCallback;


    public static CreateUserFragment newInstance(Affiliate affiliate){
        CreateUserFragment instance =  new CreateUserFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AFFILIATE_KEY, affiliate);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_create_user;
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
        if(getArguments() != null && getArguments().containsKey(AFFILIATE_KEY)){
            mAffiliate = (Affiliate) getArguments().get(AFFILIATE_KEY);
            setAffiliateData();
        }
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
        mCallback.setToolbarTitle(getResources().getString(R.string.create_account));
        mCallback.setBackVisibilityInToolbar(true);
    }

    private void setAffiliateData() {
        vTitle.setText(getString(R.string.create_user_title, mAffiliate.getFirstName()));
        vEmail.setText(mAffiliate.getEmail());
    }

    @OnClick(R.id.fragment_create_affiliate_account_back)
    public void onBackClick(){
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.fragment_affiliate_create)
    public void onCreateAccountClick() {
        mPresenter.createAccount(mAffiliate.getAffiliateId(), mAffiliate.getProviderId(),
                vUsername.getText().toString() , vPassword.getText().toString(), vPasswordRepeat.getText().toString(), vEmail.getText().toString());
    }


    @Override
    public void isUsernameEmpty() {

    }

    @Override
    public void isPasswordEmpty() {

    }

    @Override
    public void isPasswordRepeatEmpty() {

    }

    @Override
    public void invalidUsernameFormat() {

    }

    @Override
    public void invalidPasswordFormat() {

    }

    @Override
    public void invalidPasswordRepeatFormat() {

    }

    @Override
    public void notMatchingPassword() {

    }

    @Override
    public void isEmailEmpty() {

    }

    @Override
    public void invalidEmailFormat() {

    }
}

