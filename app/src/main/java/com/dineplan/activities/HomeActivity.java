package com.dineplan.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
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
import android.view.animation.LinearInterpolator;
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
import com.google.firebase.analytics.FirebaseAnalytics;
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
    private FoodListFragment foodListFragment;
    private User user;
    private final int REQ_SYNC_MENU = 3, REQ_SYNC_PAYMENT = 4, REQ_START_SHIFT = 2, REQ_SYNC_STATUS = 1, REQ_SYNC_TRANSACTION = 5, REQ_SYNC_TAX = 6, REQ_SYNC_DEPARTMENT = 7;
    private int itemCount = 0;
    private TextView tv_count, tv_sale;

    private int syncCounter = 0, totalSyncCount = 0;
    private ArrayList<Syncer> sync;

    private FirebaseAnalytics mFirebaseAnalytics;


    // Instance fields
    Account mAccount;
    // Constants
    // Content provider authority
    public static final String AUTHORITY = "com.dineplan.sync.StubProvider";
    // Account
    public static final String ACCOUNT = "default_account";
    // Sync interval constants
    public static final long SECONDS_PER_MINUTE = 1L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 1L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;
    // Global variables
    // A content resolver for accessing the provider
    ContentResolver mResolver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_home);
        setupDrawer();
        init(savedInstanceState);
        Utils.exportDatabse("dineplan", this);
        new DbHandler(this).getTransactionTypeList();
        //Utils.exportDatabse("dineplan", this);
        logEvent();


        setUpTheSyncAdapter();

    }


    private void init(Bundle savedInstanceState) {
        ll_sale = (LinearLayout) findViewById(R.id.ll_sale);
        preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        user = new Gson().fromJson(preferences.getString("user", "{}"), User.class);
        if (preferences.getInt("workPeriodId", 0) == 0) {
            findViewById(R.id.lay_shift).setVisibility(View.VISIBLE);
            findViewById(R.id.lay_shift).setOnClickListener(this);
        } else {
            if (savedInstanceState == null) {
                foodListFragment = new FoodListFragment();
                addFragment(foodListFragment, true);
                ll_sale.setOnClickListener(foodListFragment);
            }
        }
        findViewById(R.id.btn_start_shift).setOnClickListener(this);

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            foodListFragment = (FoodListFragment) getSupportFragmentManager().getFragments().get(0);
        }
        if (foodListFragment != null)
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
        //setTouchNClick(R.id.ll_location);
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
            /*case R.id.ll_location:
                toggleDrawer();
                startActivity(new Intent(this, SelectLocation.class));
                break;*/
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
                            sync = new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("items").toString(), type);
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
                        Syncer syncer = getSyncObj("MENU");
                        new DbHandler(this).updateSyncRequire(syncer.getId());
                    }
                }
                syncCounter = syncCounter + 2;
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
                        Syncer syncer = getSyncObj("PAYMENTTYPE");
                        new DbHandler(this).updateSyncRequire(syncer.getId());
                    }
                }
                syncCounter = syncCounter + 1;
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
                        Syncer syncer = getSyncObj("TRANSACTIONTYPE");
                        new DbHandler(this).updateSyncRequire(syncer.getId());
                    }
                }
                syncCounter = syncCounter + 1;
                startJourney();
                break;

            case REQ_SYNC_TAX:
                if (result.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            Type type = new TypeToken<ArrayList<Tax>>() {
                            }.getType();
                            ArrayList<Tax> items = new Gson().fromJson(jsonObject.getJSONArray("result").toString(), type);
                            new DbHandler(this).syncTaxType(items);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Syncer syncer = getSyncObj("TAX");
                        new DbHandler(this).updateSyncRequire(syncer.getId());
                    }
                }
                syncCounter = syncCounter + 1;
                startJourney();
                break;
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
                        Syncer syncer = getSyncObj("DEPARTMENT");
                        new DbHandler(this).updateSyncRequire(syncer.getId());
                    }
                }
                syncCounter = syncCounter + 1;
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

        animationScale(tv_count);
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
    public void clearItems() {
        itemCount = 0;
        tv_sale.setText(getResources().getText(R.string.no_sale));
        tv_count.setVisibility(View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            itemCount = savedInstanceState.getInt("cou");
            if (itemCount == 0) {
                tv_sale.setText(getResources().getText(R.string.no_sale));
                tv_count.setText(String.valueOf(itemCount));
                tv_count.setVisibility(View.GONE);
            } else {
                tv_sale.setText(getResources().getText(R.string.current_sale));
                tv_count.setText(String.valueOf(itemCount));
                tv_count.setVisibility(View.VISIBLE);
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

    public void handleSyncStatusResponse(ArrayList<Syncer> list) {
        try {

            for (Syncer syncer : list) {
                if (syncer.isSyncNeeded()) {
                    totalSyncCount++;
                }
            }

            for (Syncer syncer : list) {
                if (syncer.getName().equalsIgnoreCase("menu") & syncer.isSyncNeeded()) {

                    callGetMenuApi();
                } else if (syncer.getName().equalsIgnoreCase("PAYMENTTYPE") & syncer.isSyncNeeded()) {

                    callGetPaymentTypeApi();
                } else if (syncer.getName().equalsIgnoreCase("TRANSACTIONTYPE") & syncer.isSyncNeeded()) {

                    callGetTransactionTypeApi();
                } else if (syncer.getName().equalsIgnoreCase("TAX") & syncer.isSyncNeeded()) {

                    callGetTaxApi();
                } else if (syncer.getName().equalsIgnoreCase("DEPARTMENT") & syncer.isSyncNeeded()) {

                    callGetDepartmentApi();
                }
            }
            startJourney();

        } catch (Exception e) {
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

    public void startJourney() {

        if (syncCounter >= totalSyncCount) {
            foodListFragment = new FoodListFragment();
            addFragment(foodListFragment, true);
            ll_sale.setOnClickListener(foodListFragment);
        }
    }

    public Syncer getSyncObj(String syncType) {
        if (sync == null)
            return null;
        for (Syncer syncer : sync) {
            if (syncer != null && syncer.getName() != null && syncer.getName().equalsIgnoreCase(syncType)) {
                return syncer;
            }
        }
        return null;
    }

    public void logEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "home");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "HomeActivity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Screen");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public TextView getTv_count() {
        return tv_count;
    }

    private void animationScale(View source) {

        AnimatorSet anim = new AnimatorSet();
        AnimatorSet animDown = new AnimatorSet();
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(source, "scaleX", 0.5f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(source, "scaleY", 0.5f);

        animDown.playTogether(scaleDownX, scaleDownY);
        animDown.setInterpolator(new LinearInterpolator());
        animDown.setDuration(300);

        AnimatorSet animUp = new AnimatorSet();
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(source, "scaleX", 1f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(source, "scaleY", 1f);

        animUp.playTogether(scaleUpX, scaleUpY);
        animUp.setInterpolator(new LinearInterpolator());
        animUp.setDuration(300);
        //animSetXY.start();
        anim.playSequentially(animDown, animUp);
        anim.start();
    }


    private void setUpTheSyncAdapter() {
        // Create the dummy account
        mAccount = CreateSyncAccount(this);
        // Get the content resolver for your app
        mResolver = getContentResolver();


        Bundle bundle=new Bundle();
        ContentResolver.setIsSyncable(mAccount, getString(R.string.content_authority), 1);
        ContentResolver.requestSync(mAccount, getString(R.string.content_authority), bundle);


        ContentResolver.setSyncAutomatically(mAccount, getString(R.string.content_authority), true);
       // ContentResolver.requestSync(mAccount, AUTHORITY, Bundle.EMPTY);

       /* *//*
         * Turn on periodic syncing
         *//*
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                SYNC_INTERVAL);*/

    }


    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public  Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(ACCOUNT, getResources().getString(R.string.account_type));
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
       /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return  newAccount;
    }
}






