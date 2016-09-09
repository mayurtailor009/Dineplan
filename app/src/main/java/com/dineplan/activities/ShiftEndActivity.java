package com.dineplan.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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


public class ShiftEndActivity extends BaseActivity implements AsyncTaskCompleteListener<String> {

    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_end);

        init();

    }

    private void init(){
        preferences=getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);
        Toolbar toolbar = getToolbar();
        Button btnAdd = (Button) toolbar.findViewById(R.id.tv_signin);
        btnAdd.setVisibility(View.GONE);

        ((TextView)toolbar.findViewById(R.id.tv_title)).setVisibility(View.GONE);
        ImageView ivBack = ((ImageView)toolbar.findViewById(R.id.iv_close));
        ivBack.setOnClickListener(this);
        ivBack.setImageResource(R.drawable.back_btn);

        setTouchNClick(R.id.btn_end_shift);
        setTouchNClick(R.id.btn_cancel);
        setTouchNClick(R.id.btn_open_till);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_end_shift:
                try {
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    Gson gson=new Gson();
                    User user=gson.fromJson(preferences.getString("user","{}"),User.class);
                    Location location=gson.fromJson(preferences.getString("location","{}"),Location.class);
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("workPeriodId",user.getUserId());
                    jsonObject.put("endTime",dateFormat.format(new Date()));
                    jsonObject.put("payments","[]");
                    new RequestCall(preferences.getString("url", Constant.BASE_URL)+"api/services/app/workPeriod/EndWorkPeriod", this, jsonObject, ShiftActivity.class.getName(), this, 1, true, Utils.getHeader(user));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_open_till:
                finish();
                break;
        }
    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        if(result!=null){
            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getBoolean("success")){
                    preferences.edit().remove("workPeriodId").commit();
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
