package com.dineplan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.User;
import com.dineplan.rest.RequestCall;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.dineplan.utility.Constants;
import com.dineplan.utility.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements AsyncTaskCompleteListener<String>{

    private int LOGIN=1;
    private EditText etUsername, etPassword, etTenant,url;
    private SharedPreferences app_pref;
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
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etTenant = (EditText) findViewById(R.id.et_tenant);
        url= (EditText) findViewById(R.id.et_url);
        setClick(R.id.tv_help);
        app_pref=getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:

               if(url.getText().toString().length()==0){
                    Utils.showOkDialog(this,getResources().getString(R.string.alert),getResources().getString(R.string.url_required));
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
                        app_pref.edit().putString("url",url.getText().toString());
                       // new RequestCall(url.getText().toString() + "/Account/Authenticate", this, jsonObject, LoginActivity.class.getName(), this, LOGIN, true);
                        new RequestCall(url.getText().toString() + "api/Account/Authenticate", this, jsonObject, LoginActivity.class.getName(), this, LOGIN, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            break;
            case R.id.tv_help:
                startActivity(new Intent(this, AddFoodActivity.class));
                break;
        }
    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        if(result!=null){
            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getBoolean("success")) {
                    Gson gson=new Gson();
                    User user = gson.fromJson(result, User.class);
                    user.setUserName(etUsername.getText().toString());
                    app_pref.edit().putString("user",gson.toJson(user)).commit();
                    Intent intent = new Intent(this, SelectLocation.class);
                    startActivity(intent);
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
