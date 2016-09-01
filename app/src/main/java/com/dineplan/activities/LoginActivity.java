package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.User;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements AsyncTaskCompleteListener<String>{

    private int LOGIN=1;
    private EditText etUsername, etPassword, etTenant,url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init(){
        Toolbar toolbar = getToolbar();
        toolbar.setContentInsetsAbsolute(0,0);
        TextView tvSignin = (TextView) toolbar.findViewById(R.id.tv_signin);
        tvSignin.setOnClickListener(this);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        etTenant = (EditText) findViewById(R.id.et_tenant);
        url= (EditText) findViewById(R.id.et_url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                Intent intent = new Intent(this, SelectLocation.class);
                startActivity(intent);
                finish();

               /* if(url.getText().toString().length()==0){
                    Utils.showOkDialog(this,getResources().getString(R.string.alert),getResources().getString(R.string.username_required));
                }else
                if(etTenant.getText().toString().length()==0){
                    Utils.showOkDialog(this,getResources().getString(R.string.alert),getResources().getString(R.string.tenant_required));
                }else
                if(etUsername.getText().toString().length()==0){
                    Utils.showOkDialog(this,getResources().getString(R.string.alert),getResources().getString(R.string.username_required));
                }else
                if(etPassword.getText().toString().length()==0){
                    Utils.showOkDialog(this,getResources().getString(R.string.alert),getResources().getString(R.string.password_required));
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("tenancyName", etTenant.getText().toString());
                        jsonObject.put("usernameOrEmailAddress", etUsername.getText().toString());
                        jsonObject.put("password", etPassword.getText().toString());
                        new RequestCall(url.getText().toString() + "/Account/Authenticate", this, jsonObject, LoginActivity.class.getName(), this, LOGIN, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }*/
            break;
        }
    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        if(result!=null){
            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getBoolean("success")) {
                    User user = new Gson().fromJson(result, User.class);
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
