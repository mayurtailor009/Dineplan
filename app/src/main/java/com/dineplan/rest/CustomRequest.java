package com.dineplan.rest;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends Request<JSONObject> {
    HttpEntity entity;
    String TAG;
    private Listener<JSONObject> listener;
    private ErrorListener errorListener;
    private Map<String, String> params;
    private Context cont;

    // Constructor for making GET Request
    public CustomRequest(String url, Map<String, String> params, Listener<JSONObject> reponseListener, ErrorListener errorListener, String TAG,Context context) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.errorListener = errorListener;
        this.params = params;
        this.TAG = TAG;
        this.cont=context;
    }

    // Constructor for making GET/POST Request
    public CustomRequest(int method, String url, Map<String, String> params, Listener<JSONObject> reponseListener, ErrorListener errorListener, String TAG,Context context) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.errorListener = errorListener;
        this.params = params;
        this.TAG = TAG;
        this.cont=context;
    }

    // Constructor for uploading Multipart POST Request
    public CustomRequest(int method, String url, Map<String, String> params, Listener<JSONObject> reponseListener, ErrorListener errorListener, String TAG, HttpEntity entity,Context context) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.errorListener = errorListener;
        this.params = params;
        this.entity = entity;
        this.TAG = TAG;
        this.cont=context;
    }


    @Override
    public String getBodyContentType() {
        if (entity == null)
            return super.getBodyContentType();
        else
            return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (entity == null)
            return super.getBody();
        else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                entity.writeTo(bos);
            } catch (IOException e) {
                VolleyLog.e("IOException writing to ByteArrayOutputStream");
            }
            return bos.toByteArray();
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (entity == null)
            return params;
        else
            return super.getParams();
    }


    String jsonString;
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
             jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers)); //Get jsonString from NetworkResponse.
            Entry entry = HttpHeaderParser.parseCacheHeaders(response); // Convert response into entry for storing into cache.
            if (TAG != null && entry != null)
                AppController.getInstance().getRequestQueue().getCache().put(TAG, entry); //Cache saved into memory if TAG and entry is not null.
            return Response.success(new JSONObject(jsonString), entry);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> pars = new HashMap<String, String>();
        pars.put("Content-Type", "application/x-www-form-urlencoded");
        // pars.put("Content-Type", "application/json; charset=utf-8");
        return pars;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response); // Deliver response to RequestCall.
    }

    @Override
    public void deliverError(VolleyError error) {
        errorListener.onErrorResponse(error);  // Deliver error to RequestCall.
    }
}
