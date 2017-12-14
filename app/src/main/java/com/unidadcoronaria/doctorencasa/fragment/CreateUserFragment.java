package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.unidadcoronaria.doctorencasa.domain.Provider;
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
    public static final String PROVIDER_KEY = " com.unidadcoronaria.doctorencasa.fragment.UserDataFragment.PROVIDER_KEY";

    @BindView(R.id.fragment_select_affiliate_account_affiliate_number)
    protected EditText vAffiliateNumber;

    @BindView(R.id.fragment_select_affiliate_account_affiliate_number_layout)
    protected TextInputLayout vAffiliateNumberLayout;

    @BindView(R.id.fragment_affiliate_data_password)
    protected EditText vPassword;

    @BindView(R.id.fragment_affiliate_data_password_repeat)
    protected EditText vPasswordRepeat;

    @BindView(R.id.fragment_affiliate_data_email)
    protected EditText vEmail;

    @BindView(R.id.fragment_affiliate_data_email_layout)
    protected TextInputLayout vEmailLayout;

    @BindView(R.id.fragment_affiliate_data_password_layout)
    protected TextInputLayout vPasswordLayout;

    @BindView(R.id.fragment_affiliate_data_password_repeat_layout)
    protected TextInputLayout vPasswordRepeatLayout;

    @BindView(R.id.fragment_create_user_group_head)
    protected EditText vGroupHead;

    @BindView(R.id.fragment_select_affiliate_data_container)
    protected View vAffiliateDataContainer;



    @BindViews({ R.id.fragment_affiliate_data_email_layout,
            R.id.fragment_affiliate_data_password_layout, R.id.fragment_affiliate_data_password_repeat_layout})
    protected List<TextInputLayout> textInputLayoutList;


    private Provider mProvider;
    private int mAffiliateGroupId;
    private CreateAccountView mCallback;


    public static CreateUserFragment newInstance(Provider provider){
        CreateUserFragment instance =  new CreateUserFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROVIDER_KEY, provider);
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
        if(getArguments() != null && getArguments().containsKey(PROVIDER_KEY)){
            mProvider = (Provider) getArguments().get(PROVIDER_KEY);
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

    @OnClick(R.id.fragment_select_affiliate_account_search)
    public void onSearchClick(){
        vAffiliateNumberLayout.setError(null);
        vAffiliateNumberLayout.setErrorEnabled(false);
        vProgress.setVisibility(View.VISIBLE);
        mPresenter.getAffiliateGroupData(vAffiliateNumber.getText().toString(), mProvider.getId());
        hideSoftKeyboard();
    }


    @OnClick(R.id.fragment_create_affiliate_account_back)
    public void onBackClick(){
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.fragment_affiliate_create)
    public void onCreateAccountClick() {
        clearAllErrors();
        mPresenter.createAccount(mAffiliateGroupId, mProvider.getId(),
                        vEmail.getText().toString(), vPassword.getText().toString(), vPasswordRepeat.getText().toString(), vEmail.getText().toString());
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
    public void onCreateUserSuccess() {
        Intent intent = MainActivity.getStartIntent(getActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCreateUserError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error guardando la información del usuario. Por favor, intentelo nuevamente.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateUserStart() {
        vProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGroupOwnerRetrieved(Affiliate affiliate) {
        vProgress.setVisibility(View.GONE);
        if (affiliate != null) {
            mAffiliateGroupId = affiliate.getGroupNumberId();
            vGroupHead.setText("Nombre del titular: " + affiliate.getFirstName() + " " + affiliate.getLastName());
            vAffiliateDataContainer.setVisibility(View.VISIBLE);
        } else {
            vAffiliateNumberLayout.setError(getString(R.string.wrong_affiliate_number));
            vAffiliateNumberLayout.setErrorEnabled(true);
            vAffiliateDataContainer.setVisibility(View.GONE);
        }

    }

    @Override
    public void onEmptyAffiliateNumber() {
        vProgress.setVisibility(View.GONE);
        vAffiliateNumberLayout.setError(getString(R.string.no_affiliate_number));
        vAffiliateNumberLayout.setErrorEnabled(true);
    }

    @Override
    public void onGroupOwnerError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error obteniendo la información del afiliado", Toast.LENGTH_LONG).show();
    }

    private void setEmptyErrorMessage(int viewId){
        clearAllErrors();
        ButterKnife.apply(textInputLayoutList, (Action<TextInputLayout>) (view, index) -> {
            if(view.getId() == viewId){
                view.setErrorEnabled(true);
                view.setError(getString(R.string.can_be_empty));
            }
        });
    }

    private void setErrorMessage(int viewId, @StringRes int messageId){
        clearAllErrors();
        ButterKnife.apply(textInputLayoutList, (Action<TextInputLayout>) (view, index) -> {
            if(view.getId() == viewId){
                view.setErrorEnabled(true);
                view.setError(getString(messageId));
            }
        });
    }

    private void clearAllErrors(){
        ButterKnife.apply(textInputLayoutList, (Action<TextInputLayout>) (view, index) -> {
            view.setErrorEnabled(false);
            view.setError(null);
        });
    }

    private void hideSoftKeyboard() {
            View view = this.getView().getRootView();
            if (view != null) {
               InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
    }

}