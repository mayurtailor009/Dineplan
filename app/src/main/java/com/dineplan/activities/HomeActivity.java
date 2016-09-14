package com.dineplan.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dineplan.AddSaleItem;
import com.dineplan.R;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.fragments.FoodListFragment;
import com.dineplan.model.Category;
import com.dineplan.model.Department;
import com.dineplan.model.Location;
import com.dineplan.model.PaymentType;
import com.dineplan.model.Syncer;
import com.dineplan.model.Tax;
import com.dineplan.model.TransactionType;
import com.dineplan.model.User;
import com.dineplan.rest.Constant;
import com.dineplan.rest.RequestCall;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.dineplan.utility.Constants;
import com.dineplan.utility.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomeActivity extends BaseActivity implements AsyncTaskCompleteListener<String>, AddSaleItem {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private Dialog dialog;
    private LinearLayout ll_sale;
    private  FoodListFragment foodListFragment;
    private User user;
    private final int REQ_SYNC_MENU = 3, REQ_SYNC_PAYMENT = 4, REQ_START_SHIFT = 2, REQ_SYNC_STATUS = 1
            , REQ_SYNC_TRANSACTION = 5, REQ_SYNC_TAX = 6, REQ_SYNC_DEPARTMENT = 7;
    private int itemCount = 0;
    private TextView tv_count, tv_sale;

    private int syncCounter=0, totalSyncCount=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupDrawer();
        init(savedInstanceState);


        //Utils.exportDatabse("dineplan", this);
    }

    private void init(Bundle savedInstanceState) {
        ll_sale=(LinearLayout)findViewById(R.id.ll_sale);
        preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        user = new Gson().fromJson(preferences.getString("user", "{}"), User.class);
        if (preferences.getInt("workPeriodId", 0) == 0) {
            findViewById(R.id.lay_shift).setVisibility(View.VISIBLE);
            findViewById(R.id.lay_shift).setOnClickListener(this);
        } else {
            if (savedInstanceState == null) {
                foodListFragment=new FoodListFragment();
                addFragment(foodListFragment, true);
                ll_sale.setOnClickListener(foodListFragment);
            }
        }
        findViewById(R.id.btn_start_shift).setOnClickListener(this);

        if(foodListFragment!=null)
        ll_sale.setOnClickListener(foodListFragment);


    }

    private void setupDrawer() {

        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.splashlogo);
        tv_count = (TextView) toolbar.findViewById(R.id.tv_count);
        tv_sale = (TextView) toolbar.findViewById(R.id.tv_sale);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close) {
            /* Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //Put your code here
            }

            /* Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                //Put your code here
                Utils.hideKeyboard(HomeActivity.this);
            }
        };

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(R.drawable.icon_menu);*/

        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        setTouchNClick(R.id.ll_shift);
        setTouchNClick(R.id.ll_location);
        setTouchNClick(R.id.ll_logout);
        setTouchNClick(R.id.ll_setting);
        setTouchNClick(R.id.ll_reports);
        setTouchNClick(R.id.ll_register);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_register:
                toggleDrawer();
                break;
            case R.id.ll_shift:
                toggleDrawer();
                startActivity(new Intent(this, ShiftActivity.class));
                break;
            case R.id.ll_location:
                toggleDrawer();
                startActivity(new Intent(this, SelectLocation.class));
                break;
            case R.id.ll_logout:
                toggleDrawer();
                break;
            case R.id.ll_setting:
                toggleDrawer();
                break;
            case R.id.ll_reports:
                toggleDrawer();
                break;
            case R.id.btn_start_shift:
                dialog = new Dialog(this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.float_amount);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER_VERTICAL);
                dialog.show();
                dialog.findViewById(R.id.btn_done).setOnClickListener(this);
                break;
            case R.id.btn_done:
                String floatAmount = ((EditText) ((ViewGroup) view.getParent()).findViewById(R.id.float_amt)).getText().toString();
                dialog.cancel();
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SharedPreferences preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                    Gson gson = new Gson();
                    Location location = gson.fromJson(preferences.getString("location", "{}"), Location.class);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tenantId", user.getTenantId());
                    jsonObject.put("userId", user.getUserId());
                    jsonObject.put("locationId", location.getId());
                    jsonObject.put("float", floatAmount);
                    jsonObject.put("userName", user.getUserName());
                    jsonObject.put("startTime", dateFormat.format(new Date()));
                    new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/workPeriod/StartWorkPeriod", this, jsonObject, ShiftActivity.class.getName(), this, REQ_START_SHIFT, true, Utils.getHeader(user));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Open or close left drawer
     */
    private void toggleDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        switch (fromCalling) {

            case REQ_START_SHIFT:
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            preferences.edit().putInt("workPeriodId", jsonObject.getJSONObject("result").getInt("workPeriodId")).commit();
                            findViewById(R.id.lay_shift).setVisibility(View.GONE);

                            callSyncStatusApi();

                        } else {
                            Utils.showOkDialog(this, jsonObject.getJSONObject("error").getString("message"), jsonObject.getJSONObject("error").getString("details"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQ_SYNC_STATUS:
                if (result.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            Type type = new TypeToken<ArrayList<Syncer>>() {
                            }.getType();
                            ArrayList<Syncer> sync = new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("items").toString(), type);
                            new DbHandler(this).isSyncNeeded(sync);

                            handleSyncStatusResponse(sync);

                            //addFragment(new FoodListFragment(), true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case REQ_SYNC_MENU:
                if (result.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            Type type = new TypeToken<ArrayList<Category>>() {
                            }.getType();
                            ArrayList<Category> items = new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("categories").toString(), type);
                            new DbHandler(this).SyncMenuCategories(items);
                            startJourney();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                syncCounter=syncCounter+2;
                startJourney();
                break;

            case REQ_SYNC_PAYMENT:
                if (result.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            Type type = new TypeToken<ArrayList<PaymentType>>() {
                            }.getType();
                            ArrayList<PaymentType> items = new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("items").toString(), type);
                            new DbHandler(this).syncPaymentType(items);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                syncCounter=syncCounter+1;
                startJourney();
                break;

            case REQ_SYNC_TRANSACTION:
                if (result.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            Type type = new TypeToken<ArrayList<TransactionType>>() {
                            }.getType();
                            ArrayList<TransactionType> items = new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("items").toString(), type);
                            new DbHandler(this).syncTransactionType(items);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                syncCounter=syncCounter+1;
                startJourney();
                break;

            case REQ_SYNC_TAX:
                if (result.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            Type type = new TypeToken<ArrayList<Tax>>() {
                            }.getType();
                            ArrayList<Tax> items = new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("items").toString(), type);
                            new DbHandler(this).syncTaxType(items);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                syncCounter=syncCounter+1;
                startJourney();

            case REQ_SYNC_DEPARTMENT:
                if (result.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            Type type = new TypeToken<ArrayList<Department>>() {
                            }.getType();
                            ArrayList<Department> items = new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("items").toString(), type);
                            new DbHandler(this).syncDepartment(items);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                syncCounter=syncCounter+1;
                startJourney();
                break;
        }
    }

    @Override
    public void AddItem() {
        ++itemCount;
        tv_sale.setText(getResources().getText(R.string.current_sale));
        tv_count.setText(String.valueOf(itemCount));
        tv_count.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeItem() {
        --itemCount;
        if (itemCount == 0) {
            tv_sale.setText(getResources().getText(R.string.no_sale));
            tv_count.setText(String.valueOf(itemCount));
            tv_count.setVisibility(View.GONE);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        float x = tv_count.getX();
        float y=tv_count.getY();

        if (savedInstanceState != null) {
            itemCount = savedInstanceState.getInt("itemCount");
            if (itemCount == 0) {
                tv_sale.setText(getResources().getText(R.string.no_sale));
                tv_count.setText(String.valueOf(itemCount));
                tv_count.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    public void callSyncStatusApi() {
        try {
            if (!Utils.isOnline(this)) {
                return;
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tenantId", user.getTenantId());
            new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/sync/GetAll", this, jsonObject, ShiftActivity.class.getName(), this, REQ_SYNC_STATUS, true, Utils.getHeader(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSyncStatusResponse(ArrayList<Syncer> list){
        try{

            for (Syncer syncer : list) {
                if (syncer.isSyncNeeded()) {
                    totalSyncCount++;
                }
            }

            for (Syncer syncer : list) {
                if (syncer.getName().equalsIgnoreCase("menu") & syncer.isSyncNeeded()) {

                    callGetMenuApi();
                }

                else if (syncer.getName().equalsIgnoreCase("PAYMENTTYPE") & syncer.isSyncNeeded()) {

                    callGetPaymentTypeApi();
                }

                else if (syncer.getName().equalsIgnoreCase("TRANSACTIONTYPE") & syncer.isSyncNeeded()) {

                    callGetTransactionTypeApi();
                }

                else if (syncer.getName().equalsIgnoreCase("TAX") & syncer.isSyncNeeded()) {

                    callGetTaxApi();
                }

                else if (syncer.getName().equalsIgnoreCase("DEPARTMENT") & syncer.isSyncNeeded()) {

                    callGetDepartmentApi();
                }
            }
            startJourney();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callGetMenuApi() {
        try {
            if (!Utils.isOnline(this)) {
                return;
            }
            JSONObject jsonObject = new JSONObject();
            Location location = new Gson().fromJson(preferences.getString("location", "{}"), Location.class);
            jsonObject.put("locationId", location.getId());
            new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/menuItem/ApiGetMenuForLocation", this, jsonObject, ShiftActivity.class.getName(), this, REQ_SYNC_MENU, true, Utils.getHeader(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callGetPaymentTypeApi() {
        try {
            if (!Utils.isOnline(this)) {
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tenantId", user.getTenantId());
            new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/paymentType/ApiGetAll", this, jsonObject, ShiftActivity.class.getName(), this, REQ_SYNC_PAYMENT, true, Utils.getHeader(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callGetTransactionTypeApi() {
        try {
            if (!Utils.isOnline(this)) {
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tenantId", user.getTenantId());
            new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/transactionType/ApiGetAll", this, jsonObject, ShiftActivity.class.getName(), this, REQ_SYNC_TRANSACTION, true, Utils.getHeader(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void callGetTaxApi() {
        try {
            if (!Utils.isOnline(this)) {
                return;
            }
            JSONObject jsonObject = new JSONObject();
            Location location = new Gson().fromJson(preferences.getString("location", "{}"), Location.class);
            jsonObject.put("locationId", location.getId());
            new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/dinePlanTax/ApiGetTaxes", this, jsonObject, ShiftActivity.class.getName(), this, REQ_SYNC_TAX, true, Utils.getHeader(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callGetDepartmentApi() {
        try {
            if (!Utils.isOnline(this)) {
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tenantId", user.getTenantId());
            new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/department/ApiGetAll", this, jsonObject, ShiftActivity.class.getName(), this, REQ_SYNC_DEPARTMENT, true, Utils.getHeader(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startJourney(){

        if(syncCounter == totalSyncCount) {
            foodListFragment=new FoodListFragment();
            addFragment(foodListFragment, true);
            ll_sale.setOnClickListener(foodListFragment);
        }
    }
}

