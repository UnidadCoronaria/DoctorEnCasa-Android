package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.ClinicHistoryDetailView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.ClinicHistoryDetailActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerClinicHistoryComponent;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.presenter.ClinicHistoryDetailPresenter;
import com.unidadcoronaria.doctorencasa.util.DateUtil;
import com.unidadcoronaria.doctorencasa.util.StringUtil;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryDetailFragment extends BaseFragment<ClinicHistoryDetailPresenter> implements ClinicHistoryDetailView {


    public static final String TAG = "ClinicHistoryDetailFragment";

    @BindView(R.id.fragment_clinic_history_detail_last)
    TextView vLast;

    @BindView(R.id.fragment_clinic_history_detail_last_date)
    TextView vLastDate;

    @BindView(R.id.fragment_clinic_history_detail_recommendation)
    TextView vRecommendation;

    @BindView(R.id.fragment_clinic_history_detail_reason)
    TextView vReason;

    @BindView(R.id.fragment_clinic_history_detail_anamnesis)
    TextView vAnamnesis;

    @BindView(R.id.fragment_clinic_history_detail_doctor)
    TextView vDoctor;

    @BindView(R.id.fragment_clinic_history_detail_doctor_image)
    ImageView vDoctorImage;

    private ClinicHistory mClinicHistory;

    public static ClinicHistoryDetailFragment getInstance(ClinicHistory clinicHistory){
        ClinicHistoryDetailFragment instance = new ClinicHistoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ClinicHistoryDetailActivity.CLINIC_HISTORY_KEY, clinicHistory);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_clinic_history_detail;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mClinicHistory = (ClinicHistory) getArguments().get(ClinicHistoryDetailActivity.CLINIC_HISTORY_KEY);
        if(mClinicHistory != null){
            vLast.setText(App.getInstance().getString(R.string.previous_clinic_history_of, mClinicHistory.getFirstName() + " " + mClinicHistory.getLastName()));
            vLastDate.setText(DateUtil.getConvertedDayString(new Date(mClinicHistory.getVideocall().getDate())));
            vReason.setText(StringUtil.splitReasonList(mClinicHistory.getReasons()));
            vRecommendation.setText(mClinicHistory.getRecommendation());
            vAnamnesis.setText(mClinicHistory.getAnamnesis());
            vDoctor.setText("Dr "+  mClinicHistory.getVideocall().getDoctor().getFirstName()+" "+mClinicHistory.getVideocall().getDoctor().getLastName());
        }
        return view;
    }
}
