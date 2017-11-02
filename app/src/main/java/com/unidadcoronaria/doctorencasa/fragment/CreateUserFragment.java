package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateAccountView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.presenter.CreateUserPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ButterKnife.Action;
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

    @BindView(R.id.fragment_affiliate_data_username_layout)
    protected TextInputLayout vUsernameLayout;

    @BindView(R.id.fragment_affiliate_data_email_layout)
    protected TextInputLayout vEmailLayout;

    @BindView(R.id.fragment_affiliate_data_password_layout)
    protected TextInputLayout vPasswordLayout;

    @BindView(R.id.fragment_affiliate_data_password_repeat_layout)
    protected TextInputLayout vPasswordRepeatLayout;

    @BindView(R.id.fragment_affiliate_title)
    protected TextView vTitle;

    @BindViews({ R.id.fragment_affiliate_data_username_layout, R.id.fragment_affiliate_data_email_layout,
            R.id.fragment_affiliate_data_password_layout, R.id.fragment_affiliate_data_password_repeat_layout})
    protected List<TextInputLayout> textInputLayoutList;


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
        clearAllErrors();
        mPresenter.createAccount(mAffiliate.getAffiliateId(), mAffiliate.getProviderId(),
                        vUsername.getText().toString(), vPassword.getText().toString(), vPasswordRepeat.getText().toString(), vEmail.getText().toString());
    }


    @Override
    public void isUsernameEmpty() {
        setEmptyErrorMessage(R.id.fragment_affiliate_data_username_layout);
    }

    @Override
    public void isPasswordEmpty() {
        setEmptyErrorMessage(R.id.fragment_affiliate_data_password_layout);
    }

    @Override
    public void isPasswordRepeatEmpty() {
        setEmptyErrorMessage(R.id.fragment_affiliate_data_password_repeat_layout);
    }

    @Override
    public void invalidUsernameFormat() {
        setErrorMessage(R.id.fragment_affiliate_data_username_layout, R.string.error_invalid_username_format);
    }

    @Override
    public void invalidPasswordFormat() {
        setErrorMessage(R.id.fragment_affiliate_data_password_layout, R.string.error_invalid_password_format);
    }

    @Override
    public void invalidPasswordRepeatFormat() {
        setErrorMessage(R.id.fragment_affiliate_data_password_repeat_layout, R.string.error_invalid_password_format);
    }

    @Override
    public void notMatchingPassword() {
        setErrorMessage(R.id.fragment_affiliate_data_password_repeat_layout, R.string.non_matching_passwords);
    }

    @Override
    public void isEmailEmpty() {
        setEmptyErrorMessage(R.id.fragment_affiliate_data_email_layout);
    }

    @Override
    public void invalidEmailFormat() {
        setErrorMessage(R.id.fragment_affiliate_data_email_layout, R.string.error_invalid_email_format);
    }

    @Override
    public void onSaveAffiliateSuccess() {
        startActivity(MainActivity.getStartIntent(getActivity()));
        getActivity().finish();
    }

    @Override
    public void onSaveAffiliateError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error guardando la información del usuario. Por favor, intentelo nuevamente.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateUserStart() {
        vProgress.setVisibility(View.VISIBLE);
    }

    private void setEmptyErrorMessage(int viewId){
        clearAllErrors();
        ButterKnife.apply(textInputLayoutList, new Action<TextInputLayout>() {
            @Override
            public void apply(@NonNull TextInputLayout view, int index) {
                if(view.getId() == viewId){
                    view.setErrorEnabled(true);
                    view.setError(getString(R.string.can_be_empty));
                }
            }
        });
    }

    private void setErrorMessage(int viewId, @StringRes int messageId){
        clearAllErrors();
        ButterKnife.apply(textInputLayoutList, new Action<TextInputLayout>() {
            @Override
            public void apply(@NonNull TextInputLayout view, int index) {
                if(view.getId() == viewId){
                    view.setErrorEnabled(true);
                    view.setError(getString(messageId));
                }
            }
        });
    }

    private void clearAllErrors(){
        ButterKnife.apply(textInputLayoutList, new Action<TextInputLayout>() {
            @Override
            public void apply(@NonNull TextInputLayout view, int index) {
                view.setErrorEnabled(false);
                view.setError(null);
            }
        });
    }

}

