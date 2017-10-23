package com.unidadcoronaria.doctorencasa.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.util.FragmentNavigationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The base class for all activities
 *
 * @author Agustin.Bala
 * @since 0.0.1
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar vToolbar;

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
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    protected void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected void setBackVisibilityInToolbar(boolean isBackVisible) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isBackVisible);
            actionBar.setHomeButtonEnabled(isBackVisible);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(toolbarTitle);
        }
    }
    //endregion

    //region Abstract methods
    protected abstract @LayoutRes  int getLayout();

    protected abstract BaseFragment getFragment();

    protected abstract boolean showToolbar();
    //endregion
}