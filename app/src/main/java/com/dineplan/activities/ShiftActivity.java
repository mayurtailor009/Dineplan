package com.dineplan.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.Location;
import com.dineplan.model.User;
import com.dineplan.rest.Constant;
import com.dineplan.rest.RequestCall;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.dineplan.utility.Constants;
import com.dineplan.utility.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ShiftActivity extends BaseActivity implements AsyncTaskCompleteListener<String> {


    private String floatAmount;
    private  Dialog dialog;
    private SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
        init();
    }

    private void init(){
        Toolbar toolbar = getToolbar();
        Button btnAdd = (Button) toolbar.findViewById(R.id.tv_signin);
        btnAdd.setVisibility(View.GONE);

        ((TextView)toolbar.findViewById(R.id.tv_title)).setVisibility(View.GONE);
        ImageView ivBack = ((ImageView)toolbar.findViewById(R.id.iv_close));
        ivBack.setOnClickListener(this);
        ivBack.setImageResource(R.drawable.back_btn);

        setTouchNClick(R.id.btn_end_shift);
        setTouchNClick(R.id.btn_start_shift);
        preferences=getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);

        preferences=getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);
        if(preferences.getInt("workPeriodId",0)!=0){
            findViewById(R.id.btn_start_shift).setEnabled(false);
            ((Button)findViewById(R.id.btn_start_shift)).setTextColor(getResources().getColor(R.color.main_light));
          }else{
            findViewById(R.id.btn_end_shift).setEnabled(false);
            ((Button)findViewById(R.id.btn_end_shift)).setTextColor(getResources().getColor(R.color.main_light));

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_done:
                floatAmount=((EditText)((ViewGroup)view.getParent()).findViewById(R.id.float_amt)).getText().toString();
                dialog.cancel();
                try {
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    SharedPreferences preferences=getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);
                    Gson gson=new Gson();
                    User user=gson.fromJson(preferences.getString("user","{}"),User.class);
                    Location location=gson.fromJson(preferences.getString("location","{}"),Location.class);
                    JSONObject  jsonObject=new JSONObject();
                    jsonObject.put("tenantId",user.getUserId());
                    jsonObject.put("userId",user.getUserId());
                    jsonObject.put("locationId",location.getId());
                    jsonObject.put("float",floatAmount);
                    jsonObject.put("userName",user.getUserName());
                    jsonObject.put("startTime",dateFormat.format(new Date()));
                    new RequestCall(preferences.getString("url", Constant.BASE_URL)+"api/services/app/workPeriod/StartWorkPeriod", this, jsonObject, ShiftActivity.class.getName(), this, 1, true,Utils.getHeader(user));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_end_shift:
                startActivity(new Intent(this, ShiftEndActivity.class));
                break;
            case R.id.btn_start_shift:
                dialog=new Dialog(this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.float_amount);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER_VERTICAL);
                dialog.show();
                dialog.findViewById(R.id.btn_done).setOnClickListener(this);
                break;
            case R.id.iv_close:
                finish();;
                break;
        }
    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        if(result!=null){
            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getBoolean("success")){
                    preferences.edit().putInt("workPeriodId",jsonObject.getJSONObject("result").getInt("workPeriodId")).commit();
                    finish();
                }else{
                    Utils.showOkDialog(this,jsonObject.getJSONObject("error").getString("message"),jsonObject.getJSONObject("error").getString("details"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
