package com.unidadcoronaria.doctorencasa.fragment;

import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.LoginActivity;
import com.unidadcoronaria.doctorencasa.presenter.BasePresenter;
import com.unidadcoronaria.doctorencasa.service.SinchService;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public abstract class BaseFragment<T extends BasePresenter> extends LifecycleFragment {

    @BindView(R.id.rl_progress)
    @Nullable
    View vProgress;

    @Inject
    protected T mPresenter;

    protected FragmentContainer mFragmentContainerCallback;

    //region Fragment Implementation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(makeContentViewResourceId(), container, false);
        inject();
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentContainer){
            this.mFragmentContainerCallback = (FragmentContainer) context;
        }
    }

    @Override
    @Deprecated
    public void onAttach(Activity context){
        super.onAttach(context);
        if(context instanceof FragmentContainer){
            this.mFragmentContainerCallback = (FragmentContainer) context;
        }
    }

    //endregion


    //region Abstract Declaration
    protected abstract int makeContentViewResourceId();

    public abstract String getFragmentTag();

    protected abstract void inject();

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @CallSuper
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @CallSuper
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    public void logout(){
        SessionUtil.logout();
        Intent intent = LoginActivity.getStartIntent(getActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
    //endregion


}
