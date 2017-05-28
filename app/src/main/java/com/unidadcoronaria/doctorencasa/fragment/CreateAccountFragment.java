package com.unidadcoronaria.doctorencasa.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.viewmodel.CreateAccountViewModel;

import butterknife.BindView;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateAccountFragment extends BaseFragment {

    public static final String TAG = "CreateAccountFragment";

    @BindView(R.id.fragment_create_message)
    protected TextView vMessage;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_create_account;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CreateAccountViewModel mViewModel = ViewModelProviders.of(this).get(CreateAccountViewModel.class);
        mViewModel.getProviderList().observe(this, list -> {
            if (list == null) {
                vMessage.setVisibility(View.GONE);
            } else {
                vMessage.setVisibility(View.VISIBLE);
                vMessage.setText(list.get(0).getName());
            }
        });
    }

}
