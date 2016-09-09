package com.dineplan.rest;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dineplan.R;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.dineplan.utility.Utils;

import org.apache.http.HttpEntity;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RequestCall implements Listener<JSONObject>, ErrorListener {
    final int MY_SOCKET_TIMEOUT_MS = 1000000;
    private JSONObject jsonObject;
    Context context;
    HashMap<String, String> params;
    String TAG, GET_URL;
    Dialog pDialog;
    String SERVICE_URL;
    AsyncTaskCompleteListener<String> listener;
    HttpEntity multiEntity;
    int fromCalling, method;
    boolean isLoading = true;
    private String message="Please wait...";

    private Map<String,String> headers;





    // Constructor for making POST Request
    public RequestCall(String SERVICE_URL, Context context, JSONObject jsonObject, String TAG, AsyncTaskCompleteListener<String> listener, int fromCalling, boolean isLoading) {
        this.SERVICE_URL = SERVICE_URL;
        this.method = Method.POST;
        this.context = context;
        this.jsonObject = jsonObject;
        this.TAG = TAG;
        this.listener = listener;
        this.fromCalling = fromCalling;
        this.isLoading = isLoading;
        if(Utils.isNetworkConnected(context))
            makeCall(isLoading);
        else
            Utils.showOkDialog(context,context.getResources().getString(R.string.network_error),context.getResources().getString(R.string.network_not_available));
    }


    // Constructor for making POST Request
    public RequestCall(String SERVICE_URL, Context context, JSONObject jsonObject, String TAG, AsyncTaskCompleteListener<String> listener, int fromCalling, boolean isLoading,Map<String,String> header) {
        this.SERVICE_URL = SERVICE_URL;
        this.method = Method.POST;
        this.context = context;
        this.jsonObject = jsonObject;
        this.TAG = TAG;
        this.listener = listener;
        this.fromCalling = fromCalling;
        this.isLoading = isLoading;
        this.headers=header;
        makeHeaderCall(isLoading);
    }

    private void makeHeaderCall(boolean loading) {
        pDialog = new Dialog(context, android.R.style.Theme_Holo_Light_Dialog);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_dialog_layout);
        ((TextView)pDialog.findViewById(R.id.progress_text)).setText(message);
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pDialog.setCancelable(false);
        if (loading)
            pDialog.show(); // show first time if request is fresh.
        CustomJsonReq customJsonReq=new CustomJsonReq(Request.Method.POST,SERVICE_URL,jsonObject,this,this,headers);
        customJsonReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(customJsonReq,TAG); // for Add a Request in queue
    }


    private void makeCall(boolean loading) {
        pDialog = new Dialog(context, android.R.style.Theme_Holo_Light_Dialog);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_dialog_layout);
        ((TextView)pDialog.findViewById(R.id.progress_text)).setText(message);
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pDialog.setCancelable(false);
        if (loading)
            pDialog.show(); // show first time if request is fresh.
        CustomJsonReq customJsonReq=new CustomJsonReq(Request.Method.POST,SERVICE_URL,jsonObject,this,this);
        customJsonReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(customJsonReq,TAG); // for Add a Request in queue
    }





    @Override
    public void onResponse(JSONObject arg0) {
        if (pDialog != null)
            pDialog.dismiss();
        listener.onTaskComplete(arg0.toString(), fromCalling); //Revert updated response to calling class.
    }

    @Override
    public void onErrorResponse(VolleyError arg0) {
        if (pDialog != null)
            pDialog.dismiss();
        String response = "{\"status\": 0,\"message\": \"Network Error\"}";
        listener.onTaskComplete(response, fromCalling); //Revert error response to calling class.
    }






}

