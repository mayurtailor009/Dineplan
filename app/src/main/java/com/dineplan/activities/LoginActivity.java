package com.dineplan.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.User;
import com.dineplan.rest.Constant;
import com.dineplan.rest.RequestCall;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements AsyncTaskCompleteListener<String>{

    private int LOGIN=1;
    private EditText etUsername, etPassword, etTenant;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init(){
        Toolbar toolbar = getToolbar();
        TextView tvSignin = (TextView) toolbar.findViewById(R.id.tv_signin);
        tvSignin.setOnClickListener(this);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etTenant = (EditText) findViewById(R.id.et_tenant);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("tenancyName",etTenant.getText().toString());
                    jsonObject.put("usernameOrEmailAddress",etUsername.getText().toString());
                    jsonObject.put("password",etPassword.getText().toString());
                    new RequestCall(Constant.BASE_URL+"Account/Authenticate",this,jsonObject,LoginActivity.class.getName(),this,LOGIN,true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            break;
        }
    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        if(result!=null){
            User user=new Gson().fromJson(result,User.class);
            if(user.isSuccess()){
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
