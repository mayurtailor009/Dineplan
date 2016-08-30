package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;

import com.dineplan.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private long splashDelay = 3000;
    Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                moveToNextActivity();
            }
        };
        timer = new Timer();
        timer.schedule(task, splashDelay);
    }

    public void moveToNextActivity() {
        Intent i = null;


        i = new Intent(SplashActivity.this, LoginActivity.class);

        startActivity(i);

        finish();
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        finish();
    }
}
