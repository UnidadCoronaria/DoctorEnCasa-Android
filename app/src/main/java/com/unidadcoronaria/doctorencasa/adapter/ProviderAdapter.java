package com.unidadcoronaria.doctorencasa.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.App;
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
    private Callback mCallback;

    public ProviderAdapter(List<Provider> mList, Callback callback) {
        this.mList = mList;
        this.mCallback = callback;
    }

    @Override
    public ProviderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProviderAdapter.ProviderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_provider, parent, false));
    }

    @Override
    public void onBindViewHolder(ProviderHolder holder, int position) {
        Provider mProvider = mList.get(position);
        holder.vName.setText(mProvider.getName());
        holder.vName.setOnClickListener(v -> selectItem(position));
        holder.vCheck.setOnClickListener(v -> selectItem(position));
        holder.vCheck.setSelected(mSelectedProvider != null && mSelectedProvider.equals(mProvider));
    }

    private void selectItem(int position) {
        mSelectedProvider = mList.get(position);
        notifyDataSetChanged();
        mCallback.onItemSelected();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Provider getSelectedProvider() {
        return mSelectedProvider;
    }

    public interface Callback {
       void onItemSelected();
    }

    static class ProviderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_provider_name)
        TextView vName;

        @BindView(R.id.item_provider_check)
        ImageView vCheck;

        @BindView(R.id.item_provider_container)
        ViewGroup vContainer;

        public ProviderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
