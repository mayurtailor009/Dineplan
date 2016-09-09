package com.dineplan.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dineplan.R;

public class PaymentReceiptActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt);

        init();
    }

    private void init(){
        Toolbar toolbar = getToolbar();
        TextView tvRight = (TextView) toolbar.findViewById(R.id.tv_right);
        tvRight.setOnClickListener(this);

        TextView tvAnyAmount = (TextView) findViewById(R.id.tv_amount);
        String val = getString(R.string.label_no_change);
        tvAnyAmount.setText(val.replace("#amount#", "$200.00"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
               finish();
                break;
        }
    }
}
