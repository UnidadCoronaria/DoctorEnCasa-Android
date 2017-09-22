package com.unidadcoronaria.doctorencasa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class AffiliateAdapter extends RecyclerView.Adapter<AffiliateAdapter.ProviderHolder> {

    private Affiliate mAffiliate;
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
        holder.vName.setText(affiliate.getFirstname()+" "+affiliate.getLastname());
        holder.vContainer.setOnClickListener(v -> this.mAffiliate = mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Affiliate getSelectedAffiliate() {
        return mAffiliate;
    }

    static class ProviderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_affiliate_name)
        TextView vName;

        @BindView(R.id.item_affiliate_container)
        ViewGroup vContainer;

        public ProviderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
