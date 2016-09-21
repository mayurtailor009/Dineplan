package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.OrderItem;
import com.dineplan.model.OrderTicket;
import com.dineplan.model.PaymentType;
import com.dineplan.utility.Utils;

import java.util.ArrayList;

public class PaymentCashActivity extends BaseActivity {

    private PaymentType paymentType;
    private EditText etPrice;
    private float price;
    private ArrayList<OrderItem> orderItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_cash);
        init();
    }

    private void init(){

        Toolbar toolbar = getToolbar();
        Button btnRight = (Button) toolbar.findViewById(R.id.tv_signin);
        btnRight.setOnClickListener(this);
        btnRight.setText(getString(R.string.btn_txt_tender));
        paymentType=(PaymentType)getIntent().getExtras().get("payment");
        price=getIntent().getExtras().getFloat("amount");
        orderItems=(ArrayList<OrderItem>)getIntent().getExtras().get("orderItems");
        ((TextView)toolbar.findViewById(R.id.tv_title)).setText("$"+ Utils.roundTwoDecimals(price));
        ImageView ivclose = ((ImageView)toolbar.findViewById(R.id.iv_close));
        ivclose.setOnClickListener(this);
        ivclose.setImageResource(R.drawable.back_btn);
        etPrice = (EditText) findViewById(R.id.et_price);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                OrderTicket orderTicket=new OrderTicket();
                startActivity(new Intent(this, PaymentReceiptActivity.class));
                finish();
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }
}
