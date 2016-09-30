package com.dineplan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.adpaters.SplitPaymentAdapter;
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
import com.dineplan.utility.VerticalSpaceItemDecoration;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentSplitActivity extends BaseActivity implements AsyncTaskCompleteListener<String> {


    private float price;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    private ArrayList<OrderItem> orderItems;
    private RecyclerView rec_record_payment;
    private Button tv_signin;
    private final int REQ_GENERATE_TICKET=1;
    private ArrayList<PaymentType> paymentTypes;
    private DbHandler dbHandler;
    private OrderTicket orderTicket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_split);

        init();
    }

    private void init(){

        dbHandler=new DbHandler(this);
         paymentTypes = new DbHandler(this).getPaymentTypeList();
        Toolbar toolbar = getToolbar();
        Button btnRight = (Button) toolbar.findViewById(R.id.tv_signin);
        btnRight.setOnClickListener(this);
        btnRight.setText(getString(R.string.btn_txt_tender));
        price=getIntent().getExtras().getFloat("amount");
        orderItems=(ArrayList<OrderItem>)getIntent().getExtras().get("order");
        ((TextView)toolbar.findViewById(R.id.tv_title)).setText("$"+ Utils.roundTwoDecimals(price));
        ImageView ivclose = ((ImageView)toolbar.findViewById(R.id.iv_close));
        ivclose.setOnClickListener(this);
        ivclose.setImageResource(R.drawable.back_btn);
        setTouchNClick(R.id.tv_signin);
        rec_record_payment=(RecyclerView)findViewById(R.id.rec_record_payment);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rec_record_payment.setLayoutManager(mLayoutManager);
        rec_record_payment.setItemAnimator(new DefaultItemAnimator());
        rec_record_payment.addItemDecoration(new VerticalSpaceItemDecoration(50));

        rec_record_payment.setAdapter(new SplitPaymentAdapter(this,paymentTypes));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                float amt=0;
                for(PaymentType payment:paymentTypes){
                    amt+=payment.getAmount();
                }
                if(amt<price){
                    Utils.showOkDialog(this,getResources().getString(R.string.app_name),getResources().getString(R.string.entered_amount_is_less_then_total));
                    return ;
                }
                        OrderTicket orderTicket = getTicketObj();
                        Gson gson = new Gson();
                        try{
                            JSONObject ticketJson=new JSONObject();
                            JSONObject jsonObject = new JSONObject(gson.toJson(orderTicket));
                            ticketJson.put("Ticket",jsonObject);
                            generateTicketOnServer(ticketJson);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                break;
            case R.id.iv_close:
                        finish();
                break;
        }
    }


    public OrderTicket getTicketObj(){

        PaymentTicketUtil paymentTicketUtil = new PaymentTicketUtil(this, orderItems,price,paymentTypes,null);
         orderTicket = paymentTicketUtil.generateTicket();
        orderTicket.setTicketNumber(dbHandler.insertTicket(new Gson().toJson(orderTicket)));
        orderTicket.setTicketId(0);
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
                        JSONObject jsonObject=new JSONObject(result);
                        if(jsonObject.getBoolean("success")){
                            dbHandler.updateTicketId(jsonObject.getJSONObject("result").getInt("ticket"),orderTicket.getTicketNumber());
                            Intent intent=new Intent(this,PaymentReceiptActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Utils.showOkDialog(this,getResources().getString(R.string.app_name),
                                    jsonObject.getJSONObject("error").getString("message"));
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
