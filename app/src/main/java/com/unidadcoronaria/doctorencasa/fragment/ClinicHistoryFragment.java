package com.unidadcoronaria.doctorencasa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.ClinicHistoryView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.ClinicHistoryDetailActivity;
import com.unidadcoronaria.doctorencasa.adapter.ClinicHistoryAdapter;
import com.unidadcoronaria.doctorencasa.di.component.DaggerClinicHistoryComponent;
import com.unidadcoronaria.doctorencasa.dialog.SelectAffiliateDialog;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.domain.GamAffiliate;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.presenter.ClinicHistoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ClinicHistoryFragment extends BaseFragment<ClinicHistoryPresenter> implements ClinicHistoryView, ClinicHistoryAdapter.Callback, SelectAffiliateDialog.Callback {


    public static final String TAG = "ClinicHistoryFragment";
    private static final String BUNDLE_RECYCLER_LAYOUT = "CLINIC_HISTORY_LIST";

    @BindView(R.id.fragment_clinic_history_list)
    RecyclerView vClinicHistoryList;

    @BindView(R.id.fragment_clinic_history_error)
    TextView vError;

    @BindView(R.id.fragment_clinic_history_refresh)
    SwipeRefreshLayout vRefresh;

    @BindView(R.id.fragment_clinic_history_image)
    ImageView vImage;

    @BindView(R.id.fragment_clinic_history_empty)
    TextView vEmptyText;

    @BindView(R.id.fragment_clinic_history_empty_image)
    ImageView vEmptyImage;


    private ClinicHistoryAdapter mClinicHistoryAdapter;
    private SelectAffiliateDialog mSelectAffiliateDialog = new SelectAffiliateDialog();
    private String mSelectedAffiliateId;
    private List<ClinicHistory> mOriginalList;

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
        vRefresh.setOnRefreshListener(() -> {
            vRefresh.setRefreshing(true);
            mPresenter.init();
            vProgress.setVisibility(View.VISIBLE);
            vClinicHistoryList.setVisibility(View.GONE);
            vError.setVisibility(View.GONE);
            vImage.setVisibility(View.GONE);
            vEmptyText.setVisibility(View.GONE);
            vEmptyImage.setVisibility(View.GONE);
        });
        vRefresh.setColorSchemeResources(R.color.red);
        vProgress.setVisibility(View.VISIBLE);
        mPresenter.init();
    }

    public static BaseFragment newInstance() {
        ClinicHistoryFragment instance = new ClinicHistoryFragment();
        return instance;
    }


    @Override
    public void onClinicHistoryListRetrieved(List<ClinicHistory> videoCallList) {
        mOriginalList = videoCallList;
        mClinicHistoryAdapter = new ClinicHistoryAdapter(videoCallList, this);
        vClinicHistoryList.setAdapter(mClinicHistoryAdapter);
        vProgress.setVisibility(View.GONE);
        vError.setVisibility(View.GONE);
        vImage.setVisibility(View.GONE);
        if(vRefresh.isRefreshing()){
            vRefresh.setRefreshing(false);
        }
        if(videoCallList.isEmpty()){
            vEmptyImage.setVisibility(View.VISIBLE);
            vEmptyText.setVisibility(View.VISIBLE);
            vClinicHistoryList.setVisibility(View.GONE);
        } else {
            vEmptyImage.setVisibility(View.GONE);
            vEmptyText.setVisibility(View.GONE);
            vClinicHistoryList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClinicHistoryListError() {
        vProgress.setVisibility(View.GONE);
        vError.setVisibility(View.VISIBLE);
        vImage.setVisibility(View.VISIBLE);
        vEmptyText.setVisibility(View.GONE);
        vEmptyImage.setVisibility(View.GONE);
        vClinicHistoryList.setVisibility(View.GONE);
        if(vRefresh.isRefreshing()){
            vRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(ClinicHistory clinicHistory) {
        Intent intent = new Intent(getActivity(), ClinicHistoryDetailActivity.class);
        intent.putExtra(ClinicHistoryDetailActivity.CLINIC_HISTORY_KEY, clinicHistory);
        getActivity().startActivity(intent);
    }

    public void showFilters() {
        if(vImage.getVisibility() == View.GONE && vEmptyImage.getVisibility() ==View.GONE ){
            mSelectAffiliateDialog.dismiss();
            mSelectAffiliateDialog.showAffiliateList(getActivity(), getAffiliateList(), (mSelectedAffiliateId != null)?mSelectedAffiliateId:"", this);
        } else {
            if(vEmptyImage.getVisibility() ==View.VISIBLE){
                AlertDialog.Builder dialogConfirmBuilder = new AlertDialog.Builder(getActivity()).setMessage(R.string.empty_filters).setPositiveButton(R.string.ok,
                        (dialog, which) -> dialog.dismiss() ).setCancelable(false);

                AlertDialog alertDialog = dialogConfirmBuilder.create();
                alertDialog.setOnShowListener(dialog -> alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red)));
                alertDialog.show();
            }
        }
    }

    private List<GamAffiliate> getAffiliateList() {
        List<GamAffiliate> mAffiliateList = new ArrayList<>();
        boolean isPresent = false;
        for (ClinicHistory clinicHistory : mOriginalList) {
            if (mAffiliateList.isEmpty()) {
                mAffiliateList.add(new GamAffiliate(clinicHistory.getAffiliateGamId(), clinicHistory.getFirstName(), clinicHistory.getLastName()));
            } else {
                for (GamAffiliate gamAffiliate : mAffiliateList) {
                    if (gamAffiliate.getAffiliateGamId().equals(clinicHistory.getAffiliateGamId())) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    mAffiliateList.add(new GamAffiliate(clinicHistory.getAffiliateGamId(), clinicHistory.getFirstName(), clinicHistory.getLastName()));
                }
                isPresent = false;
            }
        }
        return mAffiliateList;
    }

    @Override
    public void onSelectedAffiliate(String affiliateId) {
        mSelectedAffiliateId = affiliateId;
        filterList();
    }

    private void filterList() {
        List<ClinicHistory> filteredList = new ArrayList<>();
        if (mSelectedAffiliateId != null && !mSelectedAffiliateId.isEmpty()) {
            for (ClinicHistory clinicHistory : mOriginalList) {
                if (clinicHistory.getAffiliateGamId().equals(mSelectedAffiliateId)) {
                    filteredList.add(clinicHistory);
                }
            }
        } else {
            filteredList = new ArrayList<>(mOriginalList);
        }

        mClinicHistoryAdapter = new ClinicHistoryAdapter(filteredList, this);
        vClinicHistoryList.setAdapter(mClinicHistoryAdapter);
    }

    @Override
    public void onNegativeClick() {

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            vClinicHistoryList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, vClinicHistoryList.getLayoutManager().onSaveInstanceState());
    }
}
