package com.unidadcoronaria.doctorencasa.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.unidadcoronaria.doctorencasa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public abstract class BaseFragment extends LifecycleFragment {

    @BindView(R.id.rl_progress)
    @Nullable
    View vProgress;

    //region Fragment Implementation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(makeContentViewResourceId(), container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    //endregion


    //region Abstract Declaration
    protected abstract int makeContentViewResourceId();

    public abstract String getFragmentTag();
    //endregion
}
