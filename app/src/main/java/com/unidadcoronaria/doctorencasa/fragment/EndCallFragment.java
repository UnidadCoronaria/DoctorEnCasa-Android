package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ashokslsk.androidabcd.squarerating.SquareRatingView;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.EndCallView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.presenter.EndCallPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class EndCallFragment extends BaseFragment<EndCallPresenter> implements EndCallView {


    public static final String TAG = "EndCallFragment";
    public final static String CALL_ID_KEY = "com.unidadcoronaria.doctorencasa.fragment.endcallfrgment.CALL_ID_KEY";

    @BindView(R.id.fragment_end_call_comment)
    protected EditText vComment;

    @BindView(R.id.fragment_end_call_ranking)
    protected SquareRatingView vRating;

    private int mVideoCallId;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_end_call;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerVideoCallComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
        if (getArguments() != null && getArguments().containsKey(CALL_ID_KEY)) {
            mVideoCallId = getArguments().getInt(CALL_ID_KEY);
        }
    }

    public static BaseFragment newInstance(String mCallId) {
        EndCallFragment instance = new EndCallFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(CALL_ID_KEY, mCallId);
        instance.setArguments(bundle);
        return instance;
    }

    @OnClick
    public void onSendClick(){
        mPresenter.rank(mVideoCallId, vComment.getText().toString(), vRating.getProgress());
    }

    @Override
    public void onRankSuccess() {
        vProgress.setVisibility(View.GONE);
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage("Tus datos fueron enviados con éxito.t")
                .setPositiveButton(getString(R.string.yes), (dialog, button) -> getActivity().finish())
                .show();
    }

    @Override
    public void onRankError() {
        vProgress.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Hubo un error, intentelo nuevamente", Toast.LENGTH_LONG).show();
    }
}
