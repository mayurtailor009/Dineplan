package com.dineplan.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dineplan.R;
import com.dineplan.adpaters.SaleAdapter;
import com.dineplan.model.OrderItem;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 14/09/16.
 */
public class SaleActivity extends BaseActivity{


    private View root;
    private RecyclerView sale;
    private ArrayList<OrderItem> orderItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale);
        init();
    }


    public void init() {
        Toolbar toolbar = getToolbar();
        toolbar.setContentInsetsAbsolute(0,0);
        toolbar.findViewById(R.id.btn_back).setOnClickListener(this);
        sale=(RecyclerView)findViewById(R.id.rec_sale);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        sale.setLayoutManager(mLayoutManager);
        sale.setItemAnimator(new DefaultItemAnimator());
        orderItems=(ArrayList<OrderItem>) getIntent().getExtras().getSerializable("sale");
        sale.setAdapter(new SaleAdapter(orderItems,this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
