package com.dineplan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dineplan.R;
import com.dineplan.utility.Constants;
import com.dineplan.utility.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private long splashDelay = 2000;
    Timer timer;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences=getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                moveToNextActivity();
            }
        };
        timer = new Timer();
        timer.schedule(task, splashDelay);
        Utils.exportDatabse("dineplan",this);
    }

    public void moveToNextActivity() {
        preferences.edit().putString(Constants.AMOUNT_TYPE,"$").commit();
        if(preferences.getString("user",null)!=null){
            if(preferences.getString("location",null)!=null){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }else{
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();

    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        finish();
    }
}
