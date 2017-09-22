package com.unidadcoronaria.doctorencasa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.Provider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderHolder> {

    private Provider mSelectedProvider;
    private List<Provider> mList = new ArrayList<>();

    public ProviderAdapter(List<Provider> mList) {
        this.mList = mList;
    }

    @Override
    public ProviderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProviderAdapter.ProviderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_provider, parent, false));
    }

    @Override
    public void onBindViewHolder(ProviderHolder holder, int position) {
        Provider mProvider = mList.get(position);
        holder.vName.setText(mProvider.getName());
        holder.vContainer.setOnClickListener(v -> mSelectedProvider = mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Provider getSelectedProvider() {
        return mSelectedProvider;
    }

    static class ProviderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_provider_name)
        TextView vName;

        @BindView(R.id.item_provider_container)
        ViewGroup vContainer;

        public ProviderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
