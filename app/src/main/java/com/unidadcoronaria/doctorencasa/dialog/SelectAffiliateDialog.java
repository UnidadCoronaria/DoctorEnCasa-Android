package com.unidadcoronaria.doctorencasa.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ashokslsk.androidabcd.squarerating.SquareRatingView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.adapter.AffiliateAdapter;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.GamAffiliate;

import java.util.List;

/**
 * Created by agustin on 2/12/17.
 */

public class SelectAffiliateDialog implements AffiliateAdapter.Callback {

    private AlertDialog alertDialog;
    private GamAffiliate mSelectedAffiliate;
    private AffiliateAdapter adapter;

    public void showAffiliateList(Context context, List<GamAffiliate> affiliateList, int selectedAffiliateId, Callback mCallback) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.view_affiliate_list_dialog, null);

        showBaseMessage(context, affiliateList, selectedAffiliateId,  mCallback,  dialogView);
    }

    private void showBaseMessage(Context context,  List<GamAffiliate> affiliateList,  int selectedAffiliateId,
                                 final Callback mCallback,
                                 final View dialogView) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alertDialog = builder.create();

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        alertDialog.setView(dialogView);


        final Button yesButton = dialogView.findViewById(R.id.positive_button);
        final Button noButton = dialogView.findViewById(R.id.negative_button);

        final RecyclerView vRecycler = dialogView.findViewById(R.id.dialog_affiliate_list_items);
        adapter = new AffiliateAdapter(affiliateList, selectedAffiliateId, this);
        vRecycler.setLayoutManager(new LinearLayoutManager(context));
        vRecycler.setAdapter(adapter);

        yesButton.setOnClickListener(view -> {
            if(mSelectedAffiliate != null){
                mCallback.onSelectedAffiliate(mSelectedAffiliate.getAffiliateGamId());
            } else {
                mCallback.onSelectedAffiliate(0);
            }
            dismiss();
        });
        noButton.setOnClickListener(view ->  {
            mCallback.onNegativeClick();
            dismiss();
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onItemSelected(GamAffiliate affiliate) {
        mSelectedAffiliate = affiliate;
        adapter.notifyDataSetChanged();
    }

    public interface Callback{

        void onSelectedAffiliate(int affiliateId);
        void onNegativeClick();
    }

}

