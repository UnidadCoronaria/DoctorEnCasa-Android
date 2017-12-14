package com.unidadcoronaria.doctorencasa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class ClinicHistoryAdapter extends RecyclerView.Adapter<ClinicHistoryAdapter.ClinicHistoryHolder> {

    private List<ClinicHistory> mList = new ArrayList<>();

    public ClinicHistoryAdapter(List<ClinicHistory> mList) {
        this.mList = mList;
    }

    @Override
    public ClinicHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClinicHistoryAdapter.ClinicHistoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ClinicHistoryHolder holder, int position) {
        ClinicHistory clinicHistory = mList.get(position);
        holder.vComment.setText(clinicHistory.getComment());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class ClinicHistoryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_clinic_history_comment)
        TextView vComment;

        public ClinicHistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
