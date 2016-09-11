package com.dineplan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dineplan.R;
import com.dineplan.adpaters.LocationAdapter;
import com.dineplan.model.Location;
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
import java.util.ArrayList;


public class SelectLocation extends BaseActivity implements AsyncTaskCompleteListener<String> {

    private LocationAdapter locationAdapter;
    private RecyclerView rec_location;
    private ImageView ivSelectLocation;
    private ArrayList<Location> locations;
    private SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        init();
    }

    private void init() {
        Toolbar toolbar = getToolbar();
        toolbar.setContentInsetsAbsolute(0,0);
        ivSelectLocation =(ImageView) toolbar.findViewById(R.id.btn_next);
        rec_location=(RecyclerView)findViewById(R.id.rec_location);
        ivSelectLocation.setOnClickListener(this);
        ivSelectLocation.setVisibility(View.GONE);
        findViewById(R.id.btn_back).setOnClickListener(this);


        try {
               preferences=getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);
                User user=new Gson().fromJson(preferences.getString("user","{}"),User.class);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId",user.getUserId());
                new RequestCall(preferences.getString("url",Constant.BASE_URL)+"api/services/app/location/GetLocationBasedOnUser", this, jsonObject, SelectLocation.class.getName(), this, 1, true,Utils.getHeader(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setLocationList() {
        Location location=new Gson().fromJson(preferences.getString("location","{}"),Location.class);
        locationAdapter = new LocationAdapter(this,locations, ivSelectLocation,location);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rec_location.setHasFixedSize(true);
        rec_location.setLayoutManager(mLayoutManager);
        rec_location.setItemAnimator(new DefaultItemAnimator());
        rec_location.setAdapter(locationAdapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_next:
                Location locate=null;
                for(Location l:locations){
                    if(l.isSelected()){
                        locate=l;
                    }
                }
                if(locate!=null) {
                    Intent i = null;
                    preferences.edit().putString("location",new Gson().toJson(locate)).commit();
                    i = new Intent(this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Utils.showOkDialog(this,"Alert","Please select a location.");
                }
                break;
        }

    }

    @Override
    public void onTaskComplete(String result, int fromCalling) {
        if(result!=null){
            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getBoolean("success")){
                    Type type=new TypeToken<ArrayList<Location>>(){}.getType();
                    locations=new Gson().fromJson(jsonObject.getJSONObject("result").getJSONArray("items").toString(),type);
                    setLocationList();
                }else{
                    Utils.showOkDialog(this,jsonObject.getJSONObject("error").getString("message"),jsonObject.getJSONObject("error").getString("details"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
