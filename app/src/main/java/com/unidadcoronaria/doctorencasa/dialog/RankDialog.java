package com.unidadcoronaria.doctorencasa.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ashokslsk.androidabcd.squarerating.SquareRatingView;
import com.unidadcoronaria.doctorencasa.R;

/**
 * Created by agustin on 2/12/17.
 */

public class RankDialog {

    private AlertDialog alertDialog;

    public void showRankMessage(Context context, Callback mCallback) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.view_rank_dialog, null);

        showBaseMessage(context, mCallback,  dialogView);
    }

    private void showBaseMessage(Context context,
                                 final Callback mCallback,
                                 final View dialogView) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alertDialog = builder.create();

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        alertDialog.setView(dialogView);


        final Button yesButton = dialogView.findViewById(R.id.positive_button);
        final Button noButton = dialogView.findViewById(R.id.negative_button);

        final EditText vComment = dialogView.findViewById(R.id.dialog_rank_comment);
        final SquareRatingView vRating =  dialogView.findViewById(R.id.dialog_rank_ranking);

        yesButton.setOnClickListener(view -> {
            mCallback.onPositiveClick(vComment.getText().toString(), vRating.getProgress());
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

    public interface Callback{

        void onPositiveClick(String comment, int ranking);
        void onNegativeClick();
    }

}

