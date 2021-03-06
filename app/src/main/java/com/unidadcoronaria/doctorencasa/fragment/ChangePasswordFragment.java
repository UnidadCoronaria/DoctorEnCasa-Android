package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.ChangePasswordView;
import com.unidadcoronaria.doctorencasa.LoadableActivity;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.ChangePasswordActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerCreateAccountComponent;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;
import com.unidadcoronaria.doctorencasa.presenter.ChangePasswordPresenter;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ButterKnife.Action;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ChangePasswordFragment extends BaseFragment<ChangePasswordPresenter> implements ChangePasswordView {

    public static final String TAG = "ChangePasswordFragment";

    @BindView(R.id.fragment_change_password_current_password)
    protected EditText vCurrentPassword;

    @BindView(R.id.fragment_change_password_new_password)
    protected EditText vNewPassword;

    @BindView(R.id.fragment_change_password_repeat_new_password)
    protected EditText vNewPasswordRepeat;

    @BindView(R.id.fragment_change_password_current_password_layout)
    protected TextInputLayout vCurrentPasswordLayout;

    @BindView(R.id.fragment_change_password_new_password_layout)
    protected TextInputLayout vNewPasswordLayout;

    @BindView(R.id.fragment_change_password_repeat_new_password_layout)
    protected TextInputLayout vNewPasswordRepeatLayout;

    @BindViews({R.id.fragment_change_password_current_password_layout, R.id.fragment_change_password_new_password_layout,
            R.id.fragment_change_password_repeat_new_password_layout})
    protected List<TextInputLayout> textInputLayoutList;

    private LoadableActivity mCallback;


    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment instance = new ChangePasswordFragment();
        return instance;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof LoadableActivity){
            mCallback = (LoadableActivity) context;
        }

    }

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_change_password;
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
    }

    @OnClick(R.id.fragment_change_password_button)
    public void onChangePasswordClick() {
        clearAllErrors();
        mPresenter.changePassword(vCurrentPassword.getText().toString(),
                vNewPassword.getText().toString(), vNewPasswordRepeat.getText().toString());
    }

    @Override
    public void isNewPasswordEmpty() {
        setEmptyErrorMessage(R.id.fragment_change_password_new_password_layout);
    }

    @Override
    public void isCurrentPasswordEmpty() {
        setEmptyErrorMessage(R.id.fragment_change_password_current_password_layout);
    }

    @Override
    public void isNewPasswordRepeatEmpty() {
        setEmptyErrorMessage(R.id.fragment_change_password_repeat_new_password_layout);
    }

    @Override
    public void invalidNewPasswordFormat() {
        setErrorMessage(R.id.fragment_change_password_new_password_layout, R.string.error_invalid_password_format);
    }

    @Override
    public void invalidCurrentPasswordFormat() {
        setErrorMessage(R.id.fragment_change_password_current_password_layout, R.string.error_invalid_password_format);
    }

    @Override
    public void invalidNewPasswordRepeatFormat() {
        setErrorMessage(R.id.fragment_change_password_repeat_new_password_layout, R.string.error_invalid_password_format);
    }

    @Override
    public void notMatchingPassword() {
        setErrorMessage(R.id.fragment_change_password_repeat_new_password_layout, R.string.non_matching_passwords);
    }

    @Override
    public void onChangePasswordSuccess(UserInfo userInfo) {
        boolean isExpired = SessionUtil.getIsTokenExpired();
        SessionUtil.saveIsTokenExpired(false);
        AlertDialog.Builder dialogConfirmBuilder = new AlertDialog.Builder(getActivity()).setMessage(R.string.updated_password).setPositiveButton(R.string.ok,
                (dialog, which) -> {
                    if (isExpired) {
                        SessionUtil.logout();
                    }
                    ((ChangePasswordActivity) getActivity()).logout();
                }).setCancelable(false);

        AlertDialog alertDialog = dialogConfirmBuilder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent)));
        alertDialog.show();


    }

    @Override
    public void onChangePasswordError(GenericResponseDTO errorResponse) {
        mCallback.hideProgress();
        if(errorResponse.getCode() == 1003){
            showDialog("La contraseña ingresada es incorrecta.");
        } else {
            showDialog("Hubo un error actualizando la información del usuario. Por favor, intentelo nuevamente.");
        }
    }

    @Override
    public void onChangePasswordStart() {
        mCallback.showProgress();
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

    private void showDialog(String message){
        AlertDialog.Builder dialogConfirmBuilder = new AlertDialog.Builder(getActivity()).setMessage(message).setPositiveButton(R.string.ok,
                (dialog, which) -> {}).setCancelable(false);

        AlertDialog alertDialog = dialogConfirmBuilder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent)));
        alertDialog.show();
    }

}

