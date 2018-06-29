package com.unidadcoronaria.doctorencasa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.NewCallFragment;


/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class NewCallActivity extends BaseActivity {

    private NewCallFragment mNewCallFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_new_video_call;
    }


    @Override
    protected NewCallFragment getFragment() {
        mNewCallFragment = NewCallFragment.newInstance();
        return mNewCallFragment;
    }

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, NewCallActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    @Override
    public void onAttachedToWindow() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

}
