package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.CreateAccountView;
import com.unidadcoronaria.doctorencasa.LoadableActivity;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.AffiliateDataView;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.activity.TermsAndConditionsActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;
import com.unidadcoronaria.doctorencasa.presenter.CreateUserPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ButterKnife.Action;
import butterknife.OnClick;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateUserFragment extends BaseFragment<CreateUserPresenter> implements AffiliateDataView {

    public static final String TAG = "CreateUserFragment";
    public static final String PROVIDER_KEY = " com.unidadcoronaria.doctorencasa.fragment.UserDataFragment.PROVIDER_KEY";

    @BindView(R.id.fragment_create_user_affiliate_number)
    protected EditText vAffiliateNumber;

    @BindView(R.id.fragment_create_user_number_layout)
    protected TextInputLayout vAffiliateNumberLayout;

    @BindView(R.id.fragment_create_user_password)
    protected EditText vPassword;

    @BindView(R.id.fragment_create_user_password_repeat)
    protected EditText vPasswordRepeat;

    @BindView(R.id.fragment_create_user_email)
    protected EditText vEmail;

    @BindView(R.id.fragment_create_user_email_layout)
    protected TextInputLayout vEmailLayout;

    @BindView(R.id.fragment_create_user_password_layout)
    protected TextInputLayout vPasswordLayout;

    @BindView(R.id.fragment_create_user_password_repeat_layout)
    protected TextInputLayout vPasswordRepeatLayout;

    @BindView(R.id.fragment_create_user_data_container)
    protected View vAffiliateDataContainer;

    @BindView(R.id.fragment_create_user_terms_check)
    protected CheckBox vCheckTerms;

    @BindView(R.id.fragment_create_user_terms_check_text)
    protected TextView vTerms;


    @BindViews({R.id.fragment_create_user_email_layout,
            R.id.fragment_create_user_password_layout, R.id.fragment_create_user_password_repeat_layout})
    protected List<TextInputLayout> textInputLayoutList;


    private Provider mProvider;
    private int mAffiliateGroupId;
    private CreateAccountView mCallback;
    private String mPreviousNumberValue;
    private LoadableActivity mLoadableCallback;


    public static CreateUserFragment newInstance(Provider provider) {
        CreateUserFragment instance = new CreateUserFragment();
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
        if (getArguments() != null && getArguments().containsKey(PROVIDER_KEY)) {
            mProvider = (Provider) getArguments().get(PROVIDER_KEY);
        }
        vTerms.setText(Html.fromHtml(getString(R.string.terms_accept)));
        vAffiliateNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 4 && !s.toString().equals(mPreviousNumberValue)) {
                    mPreviousNumberValue = s.toString();
                    mPresenter.getAffiliateGroupData(vAffiliateNumber.getText().toString().split(" - ")[0], mProvider.getId());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateAccountView) {
            mCallback = (CreateAccountView) context;
        }
        if (context instanceof LoadableActivity) {
            mLoadableCallback = (LoadableActivity) context;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mCallback.setToolbarTitle(getResources().getString(R.string.app_name));
        mCallback.setBackVisibilityInToolbar(true);
    }

    @OnClick(R.id.fragment_create_user_cancel)
    public void onBackClick() {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.fragment_create_user_continue)
    public void onCreateAccountClick() {
        clearAllErrors();
        if (mAffiliateGroupId != 0) {
            mPresenter.createAccount(mAffiliateGroupId, mProvider.getId(),
                    vEmail.getText().toString(), vPassword.getText().toString(), vPasswordRepeat.getText().toString(), vEmail.getText().toString(), vCheckTerms.isChecked());
        } else {
            Toast.makeText(getActivity(), "El número de socio no existe para la empresa seleccionada.", Toast.LENGTH_LONG).show();
        }
    }



    @OnClick(R.id.fragment_create_user_terms_check_text)
    public void onTermsClick() {
        startActivity(TermsAndConditionsActivity.getStartIntent(getActivity()));

    }


    @Override
    public void isPasswordEmpty() {
        setEmptyErrorMessage(R.id.fragment_create_user_password_layout);
    }

    @Override
    public void isPasswordRepeatEmpty() {
        setEmptyErrorMessage(R.id.fragment_create_user_password_repeat_layout);
    }

    @Override
    public void invalidPasswordFormat() {
        setErrorMessage(R.id.fragment_create_user_password_layout, R.string.error_invalid_password_format);
    }

    @Override
    public void invalidPasswordRepeatFormat() {
        setErrorMessage(R.id.fragment_create_user_password_repeat_layout, R.string.error_invalid_password_format);
    }

    @Override
    public void notMatchingPassword() {
        setErrorMessage(R.id.fragment_create_user_password_repeat_layout, R.string.non_matching_passwords);
    }

    @Override
    public void isEmailEmpty() {
        setEmptyErrorMessage(R.id.fragment_create_user_email_layout);
    }

    @Override
    public void invalidEmailFormat() {
        setErrorMessage(R.id.fragment_create_user_email_layout, R.string.error_invalid_email_format);
    }

    @Override
    public void onCreateUserSuccess() {
        Intent intent = MainActivity.getStartIntent(getActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCreateUserError(GenericResponseDTO errorResponse) {
        mLoadableCallback.hideProgress();
        if (errorResponse.getCode() == 1000 || errorResponse.getCode() == 1001) {
            Toast.makeText(getActivity(), "El mail ya se encuentra registrado.", Toast.LENGTH_LONG).show();
        } else if (errorResponse.getCode() == 1002) {
            Toast.makeText(getActivity(), "El afiliado ya tiene una cuenta asociada.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Hubo un error guardando la información del usuario. Por favor, intentelo nuevamente.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreateUserStart() {
        mLoadableCallback.showProgress();
    }

    @Override
    public void onGroupOwnerRetrieved(Affiliate affiliate) {
        mLoadableCallback.hideProgress();
        if (affiliate != null) {
            mAffiliateGroupId = affiliate.getGroupNumber();
            vAffiliateNumber.setText(vAffiliateNumber.getText().toString().split(" - ")[0] + " - " + affiliate.getFirstName() + " " + affiliate.getLastName());
            vAffiliateNumber.setSelection(vAffiliateNumber.getText().toString().length());
        }
    }

    @Override
    public void onEmptyAffiliateNumber() {
        mLoadableCallback.hideProgress();
        vAffiliateNumberLayout.setError(getString(R.string.no_affiliate_number));
        vAffiliateNumberLayout.setErrorEnabled(true);
    }

    @Override
    public void onGroupOwnerError() {
        mLoadableCallback.hideProgress();
        mAffiliateGroupId = 0;
        vAffiliateNumber.setText(vAffiliateNumber.getText().toString().split(" - ")[0]);
        vAffiliateNumber.setSelection(vAffiliateNumber.getText().toString().length());
    }

    @Override
    public void notAcceptedTerms() {
        Toast.makeText(getActivity(), "No te olvides de aceptar los términos y condiciones", Toast.LENGTH_LONG).show();
    }

    private void setEmptyErrorMessage(int viewId) {
        clearAllErrors();
        ButterKnife.apply(textInputLayoutList, (Action<TextInputLayout>) (view, index) -> {
            if (view.getId() == viewId) {
                view.setErrorEnabled(true);
                view.setError(getString(R.string.can_be_empty));
            }
        });
    }

    private void setErrorMessage(int viewId, @StringRes int messageId) {
        clearAllErrors();
        ButterKnife.apply(textInputLayoutList, (Action<TextInputLayout>) (view, index) -> {
            if (view.getId() == viewId) {
                view.setErrorEnabled(true);
                view.setError(getString(messageId));
            }
        });
    }

    private void clearAllErrors() {
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