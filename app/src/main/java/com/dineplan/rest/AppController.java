package com.dineplan.rest;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.dineplan.utility.FontsOverride;


public class AppController extends MultiDexApplication {
    public static final String TAG = AppController.class
            .getSimpleName();
    private static AppController mInstance;
    private Context context;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public AppController() {
        this.context = this;
        mInstance = this;
    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SERIF", "raleway_black.ttf");
       // FontsOverride.setDefaultFont(this, "DEFAULT", "MyFontAsset.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "OpenSans-Semibold.ttf");
     //   FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }







}
