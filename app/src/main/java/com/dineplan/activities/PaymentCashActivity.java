package com.dineplan.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
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
    private DbHandler dbHandler ;
    private OrderTicket orderTicket;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_cash);
        dbHandler = new DbHandler(this);
        init();
    }

    private void init(){

        sharedPreferences=getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
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
        etPrice.addTextChangedListener(new MyWatcher());

            ((TextView) findViewById(R.id.tv_heading)).setText(getResources().getString(R.string.label_amount_tracking));
            etPrice.setCompoundDrawablesWithIntrinsicBounds( R.drawable.record_and_other_icon, 0, 0, 0);
            ((TextView)findViewById(R.id.tv_any_amount)).setText("Any amount over $"+Utils.roundTwoDecimals(price)+" will be recorded as tip");




    }


    class MyWatcher implements TextWatcher {

        public MyWatcher(){
           }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(!charSequence.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$"))
            {
                String userInput= ""+charSequence.toString().replaceAll("[^\\d]", "");
                StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                    cashAmountBuilder.deleteCharAt(0);
                }
                while (cashAmountBuilder.length() < 3) {
                    cashAmountBuilder.insert(0, '0');
                }
                cashAmountBuilder.insert(cashAmountBuilder.length()-2, '.');
                cashAmountBuilder.insert(0, '$');

                etPrice.setText(cashAmountBuilder.toString());
                // keeps the cursor always to the right
                Selection.setSelection(etPrice.getText(), cashAmountBuilder.toString().length());



            }


        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                String text=etPrice.getText().toString().replace(sharedPreferences.getString(Constants.AMOUNT_TYPE,""),"");
                if(text.length()>0) {
                    float amt = Float.parseFloat(text);
                    if (amt < price) {
                        Utils.showOkDialog(this, getResources().getString(R.string.app_name), getResources().getString(R.string.entered_amount_is_less_then_total));
                        return;
                    }
                }else{
                        Utils.showOkDialog(this, getResources().getString(R.string.app_name), getResources().getString(R.string.entered_amount_is_less_then_total));
                        return;
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

                }
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }

    public OrderTicket getTicketObj(){
        PaymentTicketUtil paymentTicketUtil = new PaymentTicketUtil(this, orderItems, paymentType, price, null);
         orderTicket = paymentTicketUtil.generateTicket();
        orderTicket.setTicketId(0);
        orderTicket.setTicketNumber(dbHandler.insertTicket(new Gson().toJson(orderTicket)));
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
