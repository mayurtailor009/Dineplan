package com.dineplan.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;

public class PaymentOtherActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_other);

        init();
    }

    private void init(){

        Toolbar toolbar = getToolbar();
        Button btnRight = (Button) toolbar.findViewById(R.id.tv_signin);
        btnRight.setOnClickListener(this);
        btnRight.setText(getString(R.string.btn_txt_tender));

        ((TextView)toolbar.findViewById(R.id.tv_title)).setText("$200.00");
        ImageView ivclose = ((ImageView)toolbar.findViewById(R.id.iv_close));
        ivclose.setOnClickListener(this);
        ivclose.setImageResource(R.drawable.back_btn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                /// TENDER CLICK.

                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }
}
