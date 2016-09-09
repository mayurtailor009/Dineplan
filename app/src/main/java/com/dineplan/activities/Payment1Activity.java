package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;

public class Payment1Activity extends BaseActivity {

    private EditText etPrice;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment1);

        init();
    }

    private void init(){

        Toolbar toolbar = getToolbar();
        TextView tvAdd = (TextView) toolbar.findViewById(R.id.tv_right);
        tvAdd.setOnClickListener(this);

        ((TextView)toolbar.findViewById(R.id.tv_title)).setText("$200.00");
        ((ImageView)toolbar.findViewById(R.id.iv_close)).setOnClickListener(this);

        etPrice = (EditText) findViewById(R.id.et_price);

        setTouchNClick(R.id.ll_card_payment);
        setTouchNClick(R.id.ll_cash_payment);
        setTouchNClick(R.id.ll_other_payment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_right:

                break;
            case R.id.ll_card_payment:
                startActivity(new Intent(this, PaymentRecordCardActivity.class));
                finish();
                break;
            case R.id.ll_cash_payment:
                startActivity(new Intent(this, PaymentCashActivity.class));
                finish();
                break;
            case R.id.ll_other_payment:
                startActivity(new Intent(this, PaymentOtherActivity.class));
                finish();
                break;
        }
    }
}
