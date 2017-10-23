package com.unidadcoronaria.doctorencasa.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class AffiliateAdapter extends RecyclerView.Adapter<AffiliateAdapter.ProviderHolder> {

    private Affiliate mSelectedAffiliate;
    private List<Affiliate> mList = new ArrayList<>();

    public AffiliateAdapter(List<Affiliate> mList) {
        this.mList = mList;
    }

    @Override
    public ProviderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AffiliateAdapter.ProviderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_affiliate, parent, false));
    }

    @Override
    public void onBindViewHolder(ProviderHolder holder, int position) {
        Affiliate affiliate = mList.get(position);
        holder.vName.setText(new StringBuilder(affiliate.getFirstName()).append(" ").append(affiliate.getLastName()));
        holder.vName.setOnClickListener(v -> { mSelectedAffiliate = mList.get(position); notifyDataSetChanged();});
        holder.vCheck.setOnClickListener(v -> { mSelectedAffiliate = mList.get(position); notifyDataSetChanged();});
        holder.vCheck.setSelected(mSelectedAffiliate != null && mSelectedAffiliate.equals(affiliate));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Affiliate getSelectedAffiliate() {
        return mSelectedAffiliate;
    }

    static class ProviderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_affiliate_name)
        TextView vName;

        @BindView(R.id.item_affiliate_check)
        ImageView vCheck;

        @BindView(R.id.item_affiliate_container)
        ViewGroup vContainer;

        public ProviderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
