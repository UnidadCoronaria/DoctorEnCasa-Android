package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.fragment.NewCallFragment;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class NewCallActivity extends BaseActivity {

    public final static String CALL_DESTINATION_ID_KEY = "com.unidadcoronaria.doctorencasa.activity.newcallactivity.CALL_DESTINATION_ID_KEY";
    public final static String CALL_DESTINATION_KEY = "com.unidadcoronaria.doctorencasa.activity.newcallactivity.CALL_DESTINATION_KEY";
    private VideoCall mCallDestination;
    private int mCallDestinationId;


    @Override
    protected int getLayout() {
        return R.layout.activity_new_video_call;
    }

    public static Intent newInstance(Context context, VideoCall callDestination) {
        Intent intent = new Intent(context, NewCallActivity.class);
        intent.putExtra(CALL_DESTINATION_KEY, callDestination);
        return intent;
    }

    @Override
    protected NewCallFragment getFragment() {
        return NewCallFragment.newInstance(mCallDestination, mCallDestinationId);
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent() != null && getIntent().hasExtra(CALL_DESTINATION_KEY)){
            mCallDestination = (VideoCall) getIntent().getSerializableExtra(CALL_DESTINATION_KEY);
        }
        if(getIntent() != null && getIntent().hasExtra(CALL_DESTINATION_ID_KEY)){
            mCallDestinationId = getIntent().getIntExtra(CALL_DESTINATION_ID_KEY, 0);
        }
        super.onCreate(savedInstanceState);
    }

}
