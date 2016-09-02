package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.adpaters.ChooseFoodAdapter;

public class AddFoodActivity extends BaseActivity implements View.OnClickListener{

    private EditText etQuantity, etNotes;
    private LinearLayout llChoseFood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        init();

        SetChooseFoodList();
    }

    private void init(){
        Toolbar toolbar = getToolbar();
        Button btnAdd = (Button) toolbar.findViewById(R.id.tv_signin);
        btnAdd.setOnClickListener(this);
        btnAdd.setText(getString(R.string.btn_txt_add));

        ((TextView)toolbar.findViewById(R.id.tv_title)).setText("ACCHARI TIKKA $200.00");
        ((ImageView)toolbar.findViewById(R.id.iv_close)).setOnClickListener(this);

        setTouchNClick(R.id.iv_minus);
        setTouchNClick(R.id.iv_plus);
        setTouchNClick(R.id.tv_togo);
        setTouchNClick(R.id.tv_delivery);

        etQuantity = (EditText) findViewById(R.id.et_quantity);
        etNotes = (EditText) findViewById(R.id.et_notes);

        //llChoseFood = (LinearLayout) findViewById(R.id.ll_choose);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                /// Do add product.
                startActivity(new Intent(this, Payment1Activity.class));
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
            case R.id.iv_close:
                finish();
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

    public void SetChooseFoodList(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ChooseFoodAdapter chooseFoodAdapter = new ChooseFoodAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chooseFoodAdapter);
    }
}
