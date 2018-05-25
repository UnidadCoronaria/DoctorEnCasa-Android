package com.unidadcoronaria.doctorencasa.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unidadcoronaria.doctorencasa.NavBarView;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;
import com.unidadcoronaria.doctorencasa.fragment.BaseFragment;
import com.unidadcoronaria.doctorencasa.fragment.ClinicHistoryFragment;
import com.unidadcoronaria.doctorencasa.fragment.SettingsFragment;
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

    private int currentTab = R.id.nav_videocall;
    private ClinicHistoryFragment clinicHistoryFragment;

    //region BaseActivity implementation
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureNav();
        vToolbarFilterIcon.setOnClickListener(v -> {
            if(clinicHistoryFragment != null){
                clinicHistoryFragment.showFilters();
            }
        });
    }

    private void configureNav() {
        vNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if(currentTab != item.getItemId()) {
                        currentTab = item.getItemId();
                        switch (item.getItemId()) {
                            case R.id.nav_historic:
                                clinicHistoryFragment = (ClinicHistoryFragment) ClinicHistoryFragment.newInstance();
                                showFragment(clinicHistoryFragment);
                                showFilter(true);
                                return true;
                            case R.id.nav_plan:
                                showFragment(SettingsFragment.newInstance());
                                showFilter(false);
                                return true;
                            case R.id.nav_videocall:
                                showVideocallFragment();
                                return true;
                            default:
                                return false;
                        }
                    }
                    return false;
                });
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack(){
        if(currentTab == R.id.nav_videocall) {
            finish();
        } else {
            currentTab = R.id.nav_videocall;
            showVideocallFragment();
            Menu menu = vNavigationView.getMenu();
            menu.getItem(0).setChecked(true);
        }
    }
    //endregion

    protected void showVideocallFragment(){
        showFragment(VideoCallFragment.newInstance());
        showFilter(false);
    }

    //region Abstract methods declarations
    @Override
    public void showFragment(BaseFragment fragment){
         getSupportFragmentManager().beginTransaction().addToBackStack("")
                    .replace(R.id.activity_base_fragment, fragment).commit();
    }

    //endregion
}