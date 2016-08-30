package com.dineplan.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.utility.TouchEffect;

/**
 * BaseActivity
 */

public class BaseActivity extends AppCompatActivity implements OnClickListener {

	public static final TouchEffect TOUCH = new TouchEffect();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//
//        MarsApplication marsApplication = (MarsApplication) getApplicationContext();
//        marsApplication.sendPresenceAvailable();
//	}
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        MarsApplication marsApplication = (MarsApplication) getApplicationContext();
//        marsApplication.sendPresenceUnAvailable();
//    }

	public View setTouchNClick(int id) {

		View v = findViewById(id);
		v.setOnClickListener(this);
		v.setOnTouchListener(TOUCH);
		return v;
	}
	
	public View setClick(int id) {

		View v = findViewById(id);
		v.setOnClickListener(this);
		return v;
	}

	public Toolbar getToolbar(){
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//((TextView)toolbar.findViewById(R.id.tv_header)).setText(title);
		//toolbar.setContentInsetsAbsolute(0,0);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		return toolbar;
	}
	/*
	public Toolbar setToolbarTitleHome(String title){
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		((TextView)toolbar.findViewById(R.id.tv_header)).setText(title);
		return toolbar;
	}*/

	public void setViewEnable(int id, boolean flag){
		View v = findViewById(id);
		v.setEnabled(flag);
	}
	
	public void setViewVisibility(int id, int flag){
		View v = findViewById(id);
		v.setVisibility(flag);
	}

	/**
	 * Method use to set view selected
	 * @param id resource id of view.
	 * @param flag true if view want to selected else false
	 */
	public void setViewSelected(int id, boolean flag){
		View v = findViewById(id);
		v.setSelected(flag);
	}

	/**
	 * Method use to set text on TextView, EditText, Button.
	 * @param id resource of TextView, EditText, Button.
	 * @param text string you want to set on TextView, EditText, Button
	 */
	public void setViewText(int id, String text){
		View v = ((View)findViewById(id));
		if(v instanceof TextView){
			TextView tv = (TextView)v;
			tv.setText(text);
		}
		if(v instanceof EditText){
			EditText et = (EditText)v;
			et.setText(text);
		}
		if(v instanceof Button){
			Button btn = (Button)v;
			btn.setText(text);
		}
		
	}
	
	public void setViewText(View view, int id, String text){
		View v = ((View)view.findViewById(id));
		if(v instanceof TextView){
			TextView tv = (TextView)v;
			tv.setText(text);
		}
		if(v instanceof EditText){
			EditText et = (EditText)v;
			et.setText(text);
		}
		if(v instanceof Button){
			Button btn = (Button)v;
			btn.setText(text);
		}
		
	}
	
	/**
	 * Method use to get Text from TextView, EditText, Button.
	 * @param id resource id TextView, EditText, Button
	 * @return string text from view
	 */
	public String getViewText(int id){
		String text="";
		View v = ((View)findViewById(id));
		if(v instanceof TextView){
			TextView tv = (TextView)v;
			text = tv.getText().toString().trim();
		}
		if(v instanceof EditText){
			EditText et = (EditText)v;
			text = et.getText().toString().trim();
		}
		if(v instanceof Button){
			Button btn = (Button)v;
			text = btn.getText().toString().trim();
		}
		return text;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
		}
	}

	/*public void setRightTextClick(String text){
		TextView tv = (TextView) findViewById(R.id.tv_right);
		tv.setVisibility(View.VISIBLE);
		tv.setText(text);
		setClick(R.id.tv_right);
	}

	public void setLeftTextClick(String text){
		TextView tv = (TextView) findViewById(R.id.tv_left);
		tv.setVisibility(View.VISIBLE);
		tv.setText(text);
		setClick(R.id.tv_left);
	}

	public void setRightEnable(boolean flag){
		TextView tv = (TextView) findViewById(R.id.tv_right);
		tv.setEnabled(flag);
	}*/

	/**
	 * get current visible fragment
	 *
	 * @return
	 */
	public Fragment getCurrentFragment(String tag) {
		return getSupportFragmentManager().findFragmentByTag(tag);
	}

	public Fragment getCurrentFragment(){
		FragmentManager.BackStackEntry backEntry=getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1);
		String str=backEntry.getName();
		Fragment fragment=getSupportFragmentManager().findFragmentByTag(str);
		return  fragment;
	}

    public final boolean isOnline(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected())
            return true;
        return false;
    }


    public ProgressDialog getProgressDialog(Context context, boolean cancelable){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(cancelable);
        progressDialog.setTitle(context.getString(R.string.loading_msg));
        return progressDialog;
    }


}
