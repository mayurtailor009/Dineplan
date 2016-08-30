package com.dineplan.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dineplan.R;

public class LoginActivity extends BaseActivity {

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

                break;
        }
    }
}
