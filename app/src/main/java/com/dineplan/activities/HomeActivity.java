package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.dineplan.R;
import com.dineplan.fragments.FoodListFragment;
import com.dineplan.utility.Utils;

public class HomeActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupDrawer();

        addFragment(new FoodListFragment(), true);
    }

    private void setupDrawer(){

        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.splashlogo);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close){
            /* Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //Put your code here
            }

            /* Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                //Put your code here
                Utils.hideKeyboard(HomeActivity.this);
            }
        };

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(R.drawable.icon_menu);*/

        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        setTouchNClick(R.id.ll_shift);
        setTouchNClick(R.id.ll_location);
        setTouchNClick(R.id.ll_logout);
        setTouchNClick(R.id.ll_setting);
        setTouchNClick(R.id.ll_reports);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_shift:
                toggleDrawer();
                startActivity(new Intent(this, ShiftActivity.class));
                break;
            case R.id.ll_location:
                toggleDrawer();
                startActivity(new Intent(this, SelectLocation.class));
                break;
            case R.id.ll_logout:
                toggleDrawer();
                break;
            case R.id.ll_setting:
                toggleDrawer();
                break;
            case R.id.ll_reports:
                toggleDrawer();
                break;
        }
    }

    /**
     * Open or close left drawer
     */
    private void toggleDrawer(){
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

}
