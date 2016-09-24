package com.dineplan.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.OrderItem;
import com.dineplan.model.OrderTicket;
import com.dineplan.model.PaymentType;
import com.dineplan.model.User;
import com.dineplan.rest.Constant;
import com.dineplan.rest.RequestCall;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.dineplan.utility.Constants;
import com.dineplan.utility.PaymentTicketUtil;
import com.dineplan.utility.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentCashActivity extends BaseActivity  implements AsyncTaskCompleteListener<String> {

    private PaymentType paymentType;
    private EditText etPrice;
    private float price;
    private ArrayList<OrderItem> orderItems;
    private final int REQ_GENERATE_TICKET=1;
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
        orderItems=(ArrayList<OrderItem>)getIntent().getExtras().get("order");
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

                OrderTicket orderTicket = getTicketObj();
                DbHandler dbHandler = new DbHandler(this);
                Gson gson = new Gson();
                dbHandler.insertTicket(gson.toJson(orderTicket));
                try{
                    JSONObject jsonObject = new JSONObject(gson.toJson(orderTicket));
                    generateTicketOnServer(jsonObject);
                }
                catch(Exception e){

                }
                //startActivity(new Intent(this, PaymentReceiptActivity.class));
                //finish();
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }

    public OrderTicket getTicketObj(){
        PaymentTicketUtil paymentTicketUtil = new PaymentTicketUtil(this, orderItems, paymentType, price, null);

        OrderTicket orderTicket = paymentTicketUtil.generateTicket();
        orderTicket.setTicketId(101);
        orderTicket.setTicketNumber(101);
        return orderTicket;
    }

    public void generateTicketOnServer(JSONObject json) {
        try {
            if (!Utils.isOnline(this)) {
                return;
            }
            SharedPreferences preferences = preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
            User user = new Gson().fromJson(preferences.getString("user", "{}"), User.class);
            new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/ticket/CreateOrUpdateTicket",
                    this, json, PaymentCashActivity.class.getName(), this, REQ_GENERATE_TICKET, true, Utils.getHeader(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        switch (fromCalling){
            case REQ_GENERATE_TICKET:
                if(result!=null){
                    try{

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
