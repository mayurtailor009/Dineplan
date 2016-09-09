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

import com.dineplan.AddOrderTag;
import com.dineplan.R;
import com.dineplan.adpaters.ChooseFoodAdapter;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.MenuItem;
import com.dineplan.model.OrderItem;
import com.dineplan.model.OrderTag;

import java.util.ArrayList;

public class AddFoodActivity extends BaseActivity implements View.OnClickListener, AddOrderTag {

    private EditText etQuantity, etNotes;
    private LinearLayout llChoseFood;
    private DbHandler dbHandler;
    private TextView tv_title;
    private MenuItem menuItem;
    private float price;
    private int quantiy;
    private OrderItem orderItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        init();
        SetChooseFoodList();
    }

    private void init(){
        menuItem=(MenuItem) getIntent().getExtras().get("menuItem");
        price=menuItem.getPrice();
        orderItem=new OrderItem();
        dbHandler=new DbHandler(this);
        Toolbar toolbar = getToolbar();
        Button btnAdd = (Button) toolbar.findViewById(R.id.tv_signin);
        btnAdd.setOnClickListener(this);
        btnAdd.setText(getString(R.string.btn_txt_add));
        tv_title=((TextView)toolbar.findViewById(R.id.tv_title));
        tv_title.setText(menuItem.getName()+" $"+price);
        ((ImageView)toolbar.findViewById(R.id.iv_close)).setOnClickListener(this);
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
                orderItem.setPrice(price);
                Intent intent=new Intent();
                intent.putExtra("data",orderItem);
                setResult(RESULT_OK,intent);
                finish();
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

         quantiy = Integer.parseInt(etQuantity.getText().toString());

        if(plus){
            quantiy++;
        }else{
            if(quantiy==1)
                return;
            quantiy--;
        }
        etQuantity.setText(""+quantiy);
        price=menuItem.getPrice()*quantiy;
        if(orderItem.getOrderTags()!=null) {
            for (OrderTag tag:orderItem.getOrderTags()){
                price+=tag.getPrice();
            }
        }
        tv_title.setText(menuItem.getName()+" $"+price);

    }

    public void SetChooseFoodList(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_portions);
        ChooseFoodAdapter chooseFoodAdapter = new ChooseFoodAdapter(this,menuItem.getOrderTagGroups(),this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chooseFoodAdapter);
    }

    @Override
    public void addOrderTag(OrderTag orderTag) {
        if(orderItem.getOrderTags()==null)
            orderItem.setOrderTags(new ArrayList<OrderTag>());
        orderItem.getOrderTags().add(orderTag);
        price=price+orderTag.getPrice();
        tv_title.setText(menuItem.getName()+" $"+price);
    }

    @Override
    public void removeOrderTag(OrderTag orderTag) {
        price=price-orderTag.getPrice();
        orderItem.getOrderTags().remove(orderTag);
        tv_title.setText(menuItem.getName()+" $"+price);
    }
}
