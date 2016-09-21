package com.dineplan.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.adpaters.PaymentTypeAdapter;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.OrderItem;
import com.dineplan.model.PaymentType;
import com.dineplan.utility.Utils;

import java.util.ArrayList;

public class Payment1Activity extends BaseActivity {

    private RecyclerView rec_payment_type;
    private ArrayList<PaymentType> paymentTypes;
    private float price;
    private DbHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment1);
        init();
    }

    private void init(){

        dbHandler=new DbHandler(this);
        paymentTypes=dbHandler.getPaymentTypeList();
        Toolbar toolbar = getToolbar();
        TextView tvAdd = (TextView) toolbar.findViewById(R.id.tv_right);
        tvAdd.setOnClickListener(this);

        ((ImageView)toolbar.findViewById(R.id.iv_close)).setOnClickListener(this);
        rec_payment_type=(RecyclerView)findViewById(R.id.rec_payment_type);

        price=getIntent().getExtras().getFloat("amount");
        ((TextView)toolbar.findViewById(R.id.tv_title)).setText("$"+ Utils.roundTwoDecimals(price));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rec_payment_type.setLayoutManager(mLayoutManager);
        rec_payment_type.setHasFixedSize(true);
        rec_payment_type.setItemAnimator(new DefaultItemAnimator());
        rec_payment_type.setAdapter(new PaymentTypeAdapter(this,paymentTypes));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_right:
                break;
        }
    }
}
