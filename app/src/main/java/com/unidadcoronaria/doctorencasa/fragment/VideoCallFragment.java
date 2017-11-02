package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.VideoCallView;
import com.unidadcoronaria.doctorencasa.activity.NewCallActivity;
import com.unidadcoronaria.doctorencasa.di.component.DaggerVideoCallComponent;
import com.unidadcoronaria.doctorencasa.presenter.VideoCallPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class VideoCallFragment extends BaseFragment<VideoCallPresenter> implements VideoCallView {

    public static final String TAG = VideoCallFragment.class.getSimpleName();

    @BindView(R.id.fragment_video_call_button)
    protected View vButtonCall;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_video_call;
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
    }

    public static VideoCallFragment newInstance() {
        return new VideoCallFragment();
    }

    @OnClick(R.id.fragment_video_call_button)
    protected void onMakeCallClick(){
        startActivity(NewCallActivity.newInstance(getActivity(), "miriam"));
    }

}