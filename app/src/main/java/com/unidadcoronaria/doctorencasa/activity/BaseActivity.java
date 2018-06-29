package com.unidadcoronaria.doctorencasa.activity;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unidadcoronaria.doctorencasa.LoadableActivity;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.util.FragmentNavigationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * The base class for all activities
 *
 * @author Agustin.Bala
 * @since 0.0.1
 */
public abstract class BaseActivity extends AppCompatActivity  implements LoadableActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar vToolbar;
    @Nullable
    @BindView(R.id.toolbar_title)
    TextView vToolbarTitle;
    @Nullable
    @BindView(R.id.toolbar_icon)
    ImageView vToolbarIcon;

    @Nullable
    @BindView(R.id.toolbar_filter_icon)
    ImageView vToolbarFilterIcon;


    @BindView(R.id.rl_progress)
    @Nullable
    View vProgress;

    //region Lifecycle implementation
    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        if(showToolbar()) {
            configureToolbar(savedInstanceState);
        } else {
            hideToolbar();
        }
        if (savedInstanceState == null && getFragment() != null) {
            FragmentNavigationUtil.addFragment(getSupportFragmentManager(), R.id.activity_base_fragment, getFragment());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    //endregion

    //region Toolbar
    protected void configureToolbar(Bundle savedInstanceState) {
        setSupportActionBar(vToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    protected void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if(vToolbar != null) {
            vToolbar.setVisibility(View.GONE);
        }
    }

    protected void setBackVisibilityInToolbar(boolean isBackVisible) {
        if (vToolbarIcon != null) {
            vToolbarIcon.setVisibility(isBackVisible ? View.VISIBLE : View.GONE);
        }
    }

    protected void showFilter(boolean isFilterVisible) {
        if (vToolbarFilterIcon != null) {
            vToolbarFilterIcon.setVisibility(isFilterVisible ? View.VISIBLE : View.GONE);
        }
    }

    // endregion

    //region Public Implementation
    public void replaceFragment(BaseFragment fragment){
        FragmentNavigationUtil.replaceFragmentWithBackStack(getSupportFragmentManager(), R.id.activity_base_fragment, fragment);
    }

    public void showAndHideFragment(String hideTag, BaseFragment baseFragment){
        FragmentNavigationUtil.addAndHideFragment(hideTag, getSupportFragmentManager(), R.id.activity_base_fragment, baseFragment);
    }

    public void setToolbarTitle(String toolbarTitle){
        if (vToolbarTitle != null) {
            vToolbarTitle.setText(toolbarTitle);
        }
    }

    @Optional
    @OnClick(R.id.toolbar_icon)
    public void onBackClick(){
        onBackPressed();
    }

    //endregion

    //region Abstract methods
    protected abstract @LayoutRes  int getLayout();

    protected abstract BaseFragment getFragment();

    protected abstract boolean showToolbar();
    //endregion

    @Override
    public void showProgress() {
        vProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        vProgress.setVisibility(View.GONE);
    }
}