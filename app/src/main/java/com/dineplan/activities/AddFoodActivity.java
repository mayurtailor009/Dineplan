package com.dineplan.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dineplan.R;

public class AddFoodActivity extends BaseActivity implements View.OnClickListener{

    private EditText etQuantity, etNotes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        init();
    }

    private void init(){
        Toolbar toolbar = getToolbar();
        toolbar.setContentInsetsAbsolute(0,0);
        toolbar.setTitle("ACCHARI TIKKA $200.00");

        Button btnAdd = (Button) toolbar.findViewById(R.id.tv_signin);
        btnAdd.setOnClickListener(this);
        btnAdd.setText(getString(R.string.btn_txt_add));

        /*toolbar.setNavigationIcon(R.drawable.close_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFoodActivity.this.finish();
            }
        });*/

        setTouchNClick(R.id.iv_minus);
        setTouchNClick(R.id.iv_plus);
        setTouchNClick(R.id.tv_togo);
        setTouchNClick(R.id.tv_delivery);

        etQuantity = (EditText) findViewById(R.id.et_quantity);
        etNotes = (EditText) findViewById(R.id.et_notes);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                /// Do add product.
                break;
            case R.id.iv_minus:
                calCulateQuanity(false);
                break;
            case R.id.iv_plus:
                calCulateQuanity(true);
                break;
            case R.id.tv_togo:

                break;
            case R.id.tv_delivery:

                break;
        }
    }

    public void calCulateQuanity(boolean plus){
        if(etQuantity.getText().toString().equals(""))
            return;

        int quantiy = Integer.parseInt(etQuantity.getText().toString());

        if(plus){
            quantiy++;
        }else{
            if(quantiy==1)
                return;
            quantiy--;
        }
        etQuantity.setText(""+quantiy);
    }
}
