package com.dineplan.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dineplan.R;

public class PaymentOtherActivity extends BaseActivity {

    RadioButton rbCheck, rbOther,rbGiftCard;
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

        rbCheck = (RadioButton) findViewById(R.id.rb_check);
        rbOther = (RadioButton) findViewById(R.id.rb_other);
        rbGiftCard = (RadioButton) findViewById(R.id.rb_gift_card);

        rbCheck.setOnClickListener(this);
        rbOther.setOnClickListener(this);
        rbGiftCard.setOnClickListener(this);
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
            case R.id.rb_check:
                SetCheckRadio();
                break;
            case R.id.rb_gift_card:
                SetGiftCardRadio();
                break;
            case R.id.rb_other:
                SetOtherRadio();
                break;
        }
    }

    public void SetCheckRadio(){
        rbOther.setChecked(false);
        rbGiftCard.setChecked(false);
    }
    public void SetOtherRadio(){
        rbCheck.setChecked(false);
        rbGiftCard.setChecked(false);
    }
    public void SetGiftCardRadio(){
        rbOther.setChecked(false);
        rbCheck.setChecked(false);
    }
}
