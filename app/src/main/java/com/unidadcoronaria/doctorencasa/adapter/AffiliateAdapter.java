package com.unidadcoronaria.doctorencasa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.GamAffiliate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class AffiliateAdapter extends RecyclerView.Adapter<AffiliateAdapter.ProviderHolder> {

    private Callback mCallback;
    private List<GamAffiliate> mList = new ArrayList<>();
    private GamAffiliate mSelectedAffiliate;

    public AffiliateAdapter(List<GamAffiliate> mList, String selectedAffiliatedId, Callback callback) {
        this.mList = mList;
        this.mCallback = callback;
        checkSelected(selectedAffiliatedId);
    }

    private void checkSelected(String selectedAffiliatedId) {
        if(!selectedAffiliatedId.isEmpty()){
            for (GamAffiliate affiliate : mList) {
                if(affiliate.getAffiliateGamId().equals(selectedAffiliatedId)){
                    mSelectedAffiliate = affiliate;
                }
            }
        }
    }

    @Override
    public ProviderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AffiliateAdapter.ProviderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_affiliate, parent, false));
    }

    @Override
    public void onBindViewHolder(ProviderHolder holder, int position) {
        GamAffiliate affiliate = mList.get(position);
        if(affiliate.getFirstName() != null){
            holder.vName.setText(new StringBuilder(affiliate.getFirstName()).append(" ").append(affiliate.getLastName()));
        }
        else {
            holder.vName.setText("Anonimo");
        }

        holder.vName.setOnClickListener(v ->
            selectItem(position)
        );
        holder.vCheck.setOnClickListener(v ->
            selectItem(position)
        );
        holder.vContainer.setOnClickListener(v ->
            selectItem(position)
        );
        holder.vCheck.setSelected(affiliate.equals(mSelectedAffiliate));
    }

    private void selectItem(int position){
        if(mSelectedAffiliate != null && mSelectedAffiliate.equals(mList.get(position))){
            mSelectedAffiliate = null;
        } else {
            mSelectedAffiliate = mList.get(position);
        }
        mCallback.onItemSelected(mSelectedAffiliate);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public interface Callback  {
        void onItemSelected(GamAffiliate affiliate);
    }

    static class ProviderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_affiliate_name)
        TextView vName;

        @BindView(R.id.item_affiliate_check)
        ImageButton vCheck;

        @BindView(R.id.item_affiliate_container)
        ViewGroup vContainer;

        public ProviderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
