package com.dineplan.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by sandeepjoshi on 31/08/16.
 */
public class CustomJsonReq extends JsonObjectRequest {


    private Map<String,String> headers;
    private String jsonString;
    public CustomJsonReq(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public CustomJsonReq(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Map<String,String> header) {
        super(method, url, jsonRequest, listener, errorListener);
        this.headers=header;
    }

    public CustomJsonReq(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }



    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
       /* Map<String, String> pars = super.getHeaders();
        pars.put("Authorization", "Bearer NEW_3-lse3NblCZsfjU0t7guvV3UzY9m_uj9sYIrcj_ePTqFZBQae64aV5ilkxUSgo7NftHtdXrbQM3_Non54MtA9zX5EINWb0houQv-3J6EzplaLNgFr00Gv8pPO4CrB16Bg1XhKbswLynmP24y9HuHFlx22RnzexhmCwAhu0yOJzePvQELfkg700oloXVIjG1TmsgiIu1GXyUpiB8P1y0r-dhBS3uFkoeoXQuqM6GmkidUH9Gejwk7q9igClfQskWShJusgpqCptovb9GWVRyuB5ECn9hKQED8m5Ssv3qEglP34JREPeoCOEQ1xNgIV-veLeoY2L0Uq9w-5y4Eeg6ljVLclHp9xVzgXV3JCv2CQdRV-QK1q3nQdLEghLsB4mVzdwMCyX_EwhAETasaqOKPZCqODJlzBzRmpXYWIna5yA7gMAT7SSfgp_Sr7FP1");
        return pars;*/

         if(headers!=null)
            return  headers;
        else
            return super.getHeaders();
    }
}
