package com.dineplan.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dineplan.AddSaleItem;
import com.dineplan.R;
import com.dineplan.ShowPortions;
import com.dineplan.activities.AddFoodActivity;
import com.dineplan.activities.Payment1Activity;
import com.dineplan.activities.SaleDialog;
import com.dineplan.adpaters.CategoryAdapter;
import com.dineplan.adpaters.FoodAdapter;
import com.dineplan.adpaters.MenuAdapt;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.Category;
import com.dineplan.model.Department;
import com.dineplan.model.MenuItem;
import com.dineplan.model.OrderItem;
import com.dineplan.utility.Utils;

import java.util.ArrayList;




public class FoodListFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, ShowPortions {

    View view;

    private ArrayList<OrderItem> orderItems;
    private TextView tv_charge,taxStatus;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private ArrayList<Category> cate;
    private ArrayList<MenuItem> items;
    private Spinner sp_category;
    private float currentSale;
    private ImageView iv_search;
    private Button cancel;
    private RelativeLayout searchLayout;
    private LinearLayout categoryLayout;
    private EditText etSearch;
    private Category selectedCategory;
    private int SALE_ACTIVITY=2;
    private ArrayList<Department> departments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            currentSale = savedInstanceState.getFloat("sale");
            orderItems=(ArrayList<OrderItem>) savedInstanceState.get("orderItems");
            int itemCount = savedInstanceState.getInt("itemCount");
             itemCount = savedInstanceState.getInt("itemCount");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("sale",currentSale);
        if(orderItems!=null){
            outState.putSerializable("orderItems", orderItems);
            outState.putInt("itemCount", orderItems.size());
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.tv_charge)).setOnClickListener(this);
        DbHandler dbHandler=new DbHandler(getActivity());
        items=dbHandler.getMenuItemList();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        setFoodList();
        departments=new DbHandler(getActivity()).getDepartmentList();
        sp_category=(Spinner)view.findViewById(R.id.sp_category);
        cate=dbHandler.getCategoryList();
        cate.add(0,new Category("All"));
        selectedCategory = cate.get(0);
        sp_category.setAdapter(new CategoryAdapter(getActivity(),cate));
        sp_category.setOnItemSelectedListener(this);
        tv_charge=(TextView)view.findViewById(R.id.tv_charge);
        taxStatus=(TextView)view.findViewById(R.id.tv_tax_status);
        tv_charge.setOnClickListener(this);
        view.findViewById(R.id.ll_charge).setOnClickListener(this);
        iv_search=(ImageView)view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);

        cancel=(Button) view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);
        categoryLayout=(LinearLayout)view.findViewById(R.id.ll_spinner);
        searchLayout=(RelativeLayout) view.findViewById(R.id.ll_search);
        searchLayout.setOnClickListener(this);

        etSearch = (EditText) view.findViewById(R.id.et_search);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()>=0)
                foodAdapter.filter(charSequence.toString(), selectedCategory, items);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if(currentSale>0 && orderItems!=null && orderItems.size()>0){
            tv_charge.setText("Charge $"+Utils.roundTwoDecimals(currentSale));
            taxStatus.setText("Including Tax");
            taxStatus.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null) {
            view = inflater.inflate(R.layout.fragment_food_list, container, false);
        }



        return view;
    }




    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_cancel:
                    etSearch.setText("");
                    searchLayout.animate()

                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    searchLayout.setVisibility(View.GONE);
                                    searchLayout.setAlpha(1.0f);
                                    categoryLayout.setVisibility(View.VISIBLE);
                                }
                            });

                    break;
                case R.id.iv_search:

                    categoryLayout.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    categoryLayout.setVisibility(View.GONE);
                                    categoryLayout.setAlpha(1.0f);
                                    searchLayout.setVisibility(View.VISIBLE);
                                }
                            });

                    break;
                case R.id.tv_charge:
                    startPaymentActivity();
                    break;
                case R.id.ll_charge:
                    startPaymentActivity();
                    break;
                case R.id.ll_sale:
                    if(orderItems!=null && orderItems.size()>0) {
                        new SaleDialog(getActivity(),orderItems,this).show();
                    }

                    break;
            }
    }

    public void setFoodList(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        foodAdapter = new FoodAdapter(getActivity(),items,this);
        recyclerView.setAdapter(foodAdapter);

    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedCategory=cate.get(i);
        if(selectedCategory.getId()!=0) {
            ArrayList<MenuItem> item = (ArrayList<MenuItem>) items.clone();
            for (MenuItem it : items) {
                if (it.getCategoryId() != selectedCategory.getId()) {
                    item.remove(it);
                }
            }
            foodAdapter = new FoodAdapter(getActivity(), item,this);
            recyclerView.setAdapter(foodAdapter);
        }else{
            foodAdapter = new FoodAdapter(getActivity(), items,this);
            recyclerView.setAdapter(foodAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    @Override
    public void showPortions(MenuItem menuItem) {
        MenuAdapt menuPortionAdapter = new MenuAdapt(menuItem,getActivity(),this);
        recyclerView.setAdapter(menuPortionAdapter);
    }

    @Override
    public void showMenu() {
        sp_category.setSelection(0);
    }

    @Override
    public void AddItem(OrderItem orderItem) {
        orderItem.setDepartment(departments.get(0));
        if(orderItems==null)
            orderItems=new ArrayList<>();
        currentSale=currentSale+orderItem.getMenuPortion().getPrice()+orderItem.getTaxAmount();
        tv_charge.setText("Charge $"+Utils.roundTwoDecimals(currentSale));
        if(orderItem.getTaxes()!=null && orderItem.getTaxes().size()>0) {
            taxStatus.setText("Including Tax");
            taxStatus.setVisibility(View.VISIBLE);
        }
        ((AddSaleItem)getActivity()).AddItem();

        if(orderItems.contains(orderItem)){
            OrderItem item= orderItems.get(orderItems.indexOf(orderItem));
            item.setQuantity(item.getQuantity()+1);
        }else {
            orderItems.add(orderItem);
        }
    }

    @Override
    public void addFoodOrder(MenuItem menuItem) {
        Intent intent = new Intent(getActivity(), AddFoodActivity.class);
        intent.putExtra("menuItem", menuItem);
        startActivityForResult(intent,1);

    }

    @Override
    public void resetOrderItems() {
        orderItems.clear();
        ((AddSaleItem)getActivity()).clearItems();
        tv_charge.setText("$00.00");
        taxStatus.setVisibility(View.GONE);
        currentSale=0;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SALE_ACTIVITY) {
            
        }else{
            if (resultCode == getActivity().RESULT_OK) {
                OrderItem orderItem = (OrderItem) data.getExtras().get("data");

                if (orderItems == null)
                    orderItems = new ArrayList<>();

                orderItems.add(orderItem);
                currentSale = currentSale + orderItem.getPrice()+orderItem.getTaxAmount();
                tv_charge.setText("Charge $" + Utils.roundTwoDecimals(currentSale));
                taxStatus.setText("Including Tax");
                taxStatus.setVisibility(View.VISIBLE);
                ((AddSaleItem) getActivity()).AddItem();
            } else {
                tv_charge.setText("Charge $" + Utils.roundTwoDecimals(currentSale));
                taxStatus.setText("Including Tax");
                taxStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    public void startPaymentActivity(){
        Intent intent=new Intent(getActivity(), Payment1Activity.class);
        intent.putExtra("amount",currentSale);
        intent.putExtra("order",orderItems);
        startActivity(intent);
    }







}
