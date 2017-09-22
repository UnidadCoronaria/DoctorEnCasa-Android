package com.unidadcoronaria.doctorencasa.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import com.unidadcoronaria.doctorencasa.NavBarView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.HomeFragment;
import com.unidadcoronaria.doctorencasa.fragment.PromotionFragment;
import com.unidadcoronaria.doctorencasa.fragment.VideoCallFragment;

import butterknife.BindView;

/**
 * The base class for all activities that contains an NavigationDrawer
 *
 * @author Agustin.Bala
 * @since 0.0.1
 */
public abstract class BaseNavActivity extends BaseActivity implements NavBarView {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView vNavigationView;

    private int currentTab;

    //region BaseActivity implementation
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureNav();
    }

    private void configureNav() {
        vNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if(currentTab != item.getItemId()) {
                        currentTab = item.getItemId();
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                showFragment(HomeFragment.newInstance());
                                return true;
                            case R.id.nav_gift:
                                showFragment(PromotionFragment.newInstance());
                                return true;
                            case R.id.nav_videocall:
                                showFragment(VideoCallFragment.newInstance());
                                return true;
                            default:
                                return false;
                        }
                    }
                    return false;
                });
    }
    //endregion

    //region Abstract methods declarations
    @Override
    public void showFragment(BaseFragment fragment){
         getSupportFragmentManager().beginTransaction().addToBackStack("")
                    .replace(R.id.activity_base_fragment, fragment).commit();
    }

    //endregion
}