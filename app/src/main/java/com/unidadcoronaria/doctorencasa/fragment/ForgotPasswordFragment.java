package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.ForgotPasswordView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerLoginComponent;
import com.unidadcoronaria.doctorencasa.presenter.ForgotPasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ForgotPasswordFragment extends BaseFragment<ForgotPasswordPresenter> implements ForgotPasswordView {

    public static final String TAG = "ForgotPasswordFragment";


    @BindView(R.id.fragment_forgot_password_email)
    protected TextView vEmail;
    @BindView(R.id.fragment_forgot_password_email_layout)
    protected TextInputLayout vEmailLayout;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerLoginComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    @OnClick(R.id.fragment_forgot_password_button)
    public void attemptForgotPassword(){
        vProgress.setVisibility(View.VISIBLE);
        vEmailLayout.setError(null);
        vEmailLayout.setErrorEnabled(false);
        hideSoftKeyboard();
        mPresenter.forgotPassword(vEmail.getText().toString());
    }

    @Override
    public void onEmptyEmail() {
        vProgress.setVisibility(View.GONE);
        vEmailLayout.requestFocus();
        vEmailLayout.setError(getString(R.string.can_be_empty));
        vEmailLayout.setErrorEnabled(true);
    }

    @Override
    public void onInvalidFormatEmail() {
        vProgress.setVisibility(View.GONE);
        vEmailLayout.requestFocus();
        vEmailLayout.setError(getString(R.string.error_invalid_email_format));
        vEmailLayout.setErrorEnabled(true);
    }

    @Override
    public void onForgotPasswordError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error intentando recuperar la contraseña. Por favor, intentelo nuevamente.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onForgotPasswordSuccess() {
        vProgress.setVisibility(View.GONE);
        new AlertDialog.Builder(getActivity()).setMessage(R.string.email_sent).setPositiveButton(R.string.ok,
                (dialog, which) -> getActivity().finish() ).setCancelable(false).show();
    }

    private void hideSoftKeyboard() {
        View view = this.getView().getRootView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
