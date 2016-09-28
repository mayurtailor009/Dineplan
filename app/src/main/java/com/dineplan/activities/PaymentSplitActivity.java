package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;

public class PaymentSplitActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_record_card);

        init();
    }

    private void init(){

        Toolbar toolbar = getToolbar();
        TextView tvRight = (TextView) toolbar.findViewById(R.id.tv_right);
        tvRight.setVisibility(View.GONE);

        ((TextView)toolbar.findViewById(R.id.tv_title)).setText("$200.00");
        ((ImageView)toolbar.findViewById(R.id.iv_close)).setOnClickListener(this);

        TextView tvAnyAmount = (TextView) findViewById(R.id.tv_any_amount);
        String val = getString(R.string.label_any_amount);
        tvAnyAmount.setText(val.replace("#amount#", "$200.00"));

        setTouchNClick(R.id.btn_payment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_payment:
                startActivity(new Intent(this, PaymentReceiptActivity.class));
                finish();
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }
}
