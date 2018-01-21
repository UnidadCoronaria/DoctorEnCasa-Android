package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.ClinicHistoryView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.ClinicHistoryDetailActivity;
import com.unidadcoronaria.doctorencasa.activity.NewCallActivity;
import com.unidadcoronaria.doctorencasa.adapter.ClinicHistoryAdapter;
import com.unidadcoronaria.doctorencasa.di.component.DaggerClinicHistoryComponent;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.presenter.ClinicHistoryPresenter;
import com.unidadcoronaria.doctorencasa.service.SinchService;

import java.util.List;

import butterknife.BindView;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryFragment extends BaseFragment<ClinicHistoryPresenter> implements ClinicHistoryView, ClinicHistoryAdapter.Callback {


    public static final String TAG = "ClinicHistoryFragment";

    @BindView(R.id.fragment_clinic_history_list)
    RecyclerView vClinicHistoryList;

    private ClinicHistoryAdapter mClinicHistoryAdapter;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_clinic_history;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerClinicHistoryComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        vClinicHistoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    public static BaseFragment newInstance() {
        ClinicHistoryFragment instance = new ClinicHistoryFragment();
        return instance;
    }

    @Override
    public void onClinicHistoryListRetrieved(List<ClinicHistory> clinicHistoryList) {
        mClinicHistoryAdapter = new ClinicHistoryAdapter(clinicHistoryList,this);
        vClinicHistoryList.setAdapter(mClinicHistoryAdapter);
    }

    @Override
    public void onClinicHistoryListError() {
        Toast.makeText(getActivity(), "Hubo un error obteniendo tu historia clínica.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(ClinicHistory clinicHistory) {
        Intent intent = new Intent(getActivity(), ClinicHistoryDetailActivity.class);
        intent.putExtra(ClinicHistoryDetailActivity.CLINIC_HISTORY_KEY, clinicHistory);
        getActivity().startActivity(intent);
    }
}
