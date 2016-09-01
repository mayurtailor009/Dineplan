package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dineplan.R;
import com.dineplan.adpaters.FoodAdapter;
import com.dineplan.adpaters.LocationAdapter;

public class SelectLocation extends BaseActivity{

    private LocationAdapter locationAdapter;
    private RecyclerView rec_location;
    private ImageButton btn_select_location;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        init();
    }

    private void init() {
        Toolbar toolbar = getToolbar();
        toolbar.setContentInsetsAbsolute(0,0);
        btn_select_location=(ImageButton) toolbar.findViewById(R.id.btn_select_location);
        rec_location=(RecyclerView)findViewById(R.id.rec_location);
        btn_select_location.setOnClickListener(this);
        setLocationList();
    }

    private void setLocationList() {
        locationAdapter = new LocationAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rec_location.setHasFixedSize(true);
        rec_location.setLayoutManager(mLayoutManager);
        rec_location.setItemAnimator(new DefaultItemAnimator());
        rec_location.setAdapter(locationAdapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent i = null;
        i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
