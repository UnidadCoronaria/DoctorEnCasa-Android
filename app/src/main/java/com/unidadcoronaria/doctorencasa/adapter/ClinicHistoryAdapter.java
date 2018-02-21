package com.unidadcoronaria.doctorencasa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class ClinicHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Callback mCallback;
    private List<ClinicHistory> mList = new ArrayList<>();

    public ClinicHistoryAdapter(List<ClinicHistory> mList, Callback callback) {
        this.mList = mList;
        this.mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ClinicHistoryAdapter.ClinicHistoryHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic_history_header, parent, false));
            default:
                return new ClinicHistoryAdapter.ClinicHistoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic_history, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClinicHistory clinicHistory = mList.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                ClinicHistoryHeaderHolder holderHeader = (ClinicHistoryHeaderHolder)holder;
                holderHeader.vLast.setText(App.getInstance().getString(R.string.previous_clinic_history_of, clinicHistory.getFirstName() + " " + clinicHistory.getLastName()));
                holderHeader.vLastDate.setText(DateUtil.getConvertedDayString(new Date(clinicHistory.getVideocall().getDate())));
                holderHeader.vDiagnostic.setText(clinicHistory.getComment());
                holderHeader.vDoctor.setText("Dr "+ clinicHistory.getVideocall().getDoctor().getFirstName()+" "+clinicHistory.getVideocall().getDoctor().getLastName());
                break;
            default:
                ClinicHistoryHolder holderItem = (ClinicHistoryHolder)holder;
                holderItem.vComment.setText(clinicHistory.getComment());
                holderItem.vDate.setText(DateUtil.getConvertedDayString(new Date(clinicHistory.getVideocall().getDate())));
                holderItem.vDoctor.setText("Dr "+ clinicHistory.getVideocall().getDoctor().getFirstName()+" "+clinicHistory.getVideocall().getDoctor().getLastName());
                holderItem.vContainer.setOnClickListener(v ->
                    mCallback.onItemClick(clinicHistory));
                
                break;
        }

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface Callback{

        void onItemClick(ClinicHistory clinicHistory);
    }

    static class ClinicHistoryHeaderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_clinic_history_header_last)
        TextView vLast;

        @BindView(R.id.item_clinic_history_header_last_date)
        TextView vLastDate;

        @BindView(R.id.item_clinic_history_header_diagnostic)
        TextView vDiagnostic;

        @BindView(R.id.item_clinic_history_header_doctor)
        TextView vDoctor;

        @BindView(R.id.item_clinic_history_header_doctor_image)
        ImageView vDoctorImage;


        public ClinicHistoryHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ClinicHistoryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_clinic_history_comment)
        TextView vComment;

        @BindView(R.id.item_clinic_history_doctor)
        TextView vDoctor;

        @BindView(R.id.item_clinic_history_date)
        TextView vDate;

        @BindView(R.id.item_clinic_history_date_container)
        View vContainer;


        public ClinicHistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
