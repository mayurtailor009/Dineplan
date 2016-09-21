package com.dineplan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dineplan.AddOrderTag;
import com.dineplan.R;
import com.dineplan.adpaters.ChooseFoodAdapter;
import com.dineplan.adpaters.DepartmentAdapter;
import com.dineplan.adpaters.MenuPortionAdapter;
import com.dineplan.adpaters.TaxAdapter;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.Department;
import com.dineplan.model.MenuItem;
import com.dineplan.model.MenuPortion;
import com.dineplan.model.OrderItem;
import com.dineplan.model.OrderTag;
import com.dineplan.model.Tax;

import java.util.ArrayList;

public class AddFoodActivity extends BaseActivity implements View.OnClickListener, AddOrderTag {
    private ArrayList<Department> depart;
    private EditText etQuantity, etNotes;
    private LinearLayout llChoseFood;
    private DbHandler dbHandler;
    private TextView tv_title;
    private MenuItem menuItem;
    private float price;
    private int quantiy;
    private OrderItem orderItem;

    private RecyclerView rec_menu_portions;
    private MenuPortionAdapter menuPortionAdapter;
    private RecyclerView department;
  //  private TextView forHere,toGo,delivery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        init();
        SetChooseFoodList();
    }

    private void init(){
        quantiy=1;
        menuItem=(MenuItem) getIntent().getExtras().get("menuItem");
        price=menuItem.getPrice();
        orderItem=new OrderItem();
        orderItem.setItemName(menuItem.getName());
        orderItem.setMenuPortion(menuItem.getMenuPortions().get(0));
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
        etQuantity = (EditText) findViewById(R.id.et_quantity);
        etNotes = (EditText) findViewById(R.id.et_notes);



        if(menuItem.getMenuPortions().size()>1) {
            rec_menu_portions = (RecyclerView) findViewById(R.id.rec_menu_portions);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            rec_menu_portions.setHasFixedSize(true);
            rec_menu_portions.setLayoutManager(mLayoutManager);
            rec_menu_portions.setItemAnimator(new DefaultItemAnimator());
            menuItem.getMenuPortions().get(0).setSelected(true);
            menuPortionAdapter = new MenuPortionAdapter(this,menuItem.getMenuPortions(),this);
            rec_menu_portions.setAdapter(menuPortionAdapter);
        }


        depart=new DbHandler(this).getDepartmentList();
        department = (RecyclerView) findViewById(R.id.rec_department);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setAutoMeasureEnabled(true);
        department.setHasFixedSize(true);
        department.setLayoutManager(mLayoutManager);
        department.setItemAnimator(new DefaultItemAnimator());
        depart.get(0).setSelected(true);
        orderItem.setDepartment(depart.get(0));
        department.setAdapter(new DepartmentAdapter(this,depart,orderItem));

        if(menuItem.getTaxes()!=null && menuItem.getTaxes().size()>0){
            RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
            RecyclerView taxes=(RecyclerView) findViewById(R.id.rec_taxes);
            taxes.setHasFixedSize(true);
            taxes.setLayoutManager(mLayout);
            taxes.setItemAnimator(new DefaultItemAnimator());
            taxes.setAdapter(new TaxAdapter(this,menuItem.getTaxes()));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signin:
                ArrayList<Tax> taxes=new ArrayList<>();
                float taxcount=0;
                for(Tax tax:menuItem.getTaxes()){
                    if(tax.isChecked()) {
                        taxes.add(tax);
                        taxcount+=((tax.getPercentage()*price)/100);
                    }
                }
                orderItem.setTaxAmount(taxcount);
                orderItem.setTaxes(taxes);
                orderItem.setPrice(price);
                orderItem.setNote(etNotes.getText().toString());
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
        price=orderItem.getMenuPortion().getPrice()*quantiy;
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

        price=orderItem.getMenuPortion().getPrice();
        if(orderItem.getOrderTags()!=null) {
            for (OrderTag tag : orderItem.getOrderTags()) {
                price=price+tag.getPrice();
            }
        }
        price=price*quantiy;
        tv_title.setText(menuItem.getName()+" $"+price);
    }

    @Override
    public void removeOrderTag(OrderTag orderTag) {
        orderItem.getOrderTags().remove(orderTag);
        price=orderItem.getMenuPortion().getPrice();
        if(orderItem.getOrderTags()!=null) {
            for (OrderTag tag : orderItem.getOrderTags()) {
                price=price+tag.getPrice();
            }
        }

        price=price*quantiy;
        tv_title.setText(menuItem.getName()+" $"+price);
    }

    @Override
    public void addPortions(MenuPortion menuPortions) {
        orderItem.setMenuPortion(menuPortions);
        price=menuPortions.getPrice();
        if(orderItem.getOrderTags()!=null) {
            for (OrderTag tag : orderItem.getOrderTags()) {
                    price=price+tag.getPrice();
            }
        }
        if(orderItem.getOrderTags()!=null){
            for(OrderTag tag:orderItem.getOrderTags()){
               price+=tag.getPrice();
            }
        }
        price=price*quantiy;
        tv_title.setText(menuItem.getName()+" $"+price);
    }
}
