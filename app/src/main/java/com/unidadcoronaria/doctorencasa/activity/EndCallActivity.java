package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.EndCallFragment;
import com.unidadcoronaria.doctorencasa.fragment.NewCallFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class EndCallActivity extends BaseActivity {

    public final static String CALL_ID_KEY = "com.unidadcoronaria.doctorencasa.activity.endcallactivity.CALL_ID_KEY";
    private String mCallId;


    @Override
    protected int getLayout() {
        return R.layout.activity_end_video_call;
    }

    public static Intent newInstance(Context context, int mCallId) {
        Intent intent = new Intent(context, EndCallActivity.class);
        intent.putExtra(CALL_ID_KEY, mCallId);
        return intent;
    }

    @Override
    protected BaseFragment getFragment() {
        return EndCallFragment.newInstance(mCallId);
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent() != null && getIntent().hasExtra(CALL_ID_KEY)){
            mCallId = getIntent().getStringExtra(CALL_ID_KEY);
        }
        super.onCreate(savedInstanceState);
    }

}
