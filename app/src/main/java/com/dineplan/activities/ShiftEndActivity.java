package com.dineplan.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;

public class ShiftEndActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_end);

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

                break;
            case R.id.btn_cancel:

                break;
            case R.id.btn_open_till:

                break;
        }
    }
}
