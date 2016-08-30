package com.dineplan.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.utility.TouchEffect;


/**
 * Created by mayur.tailor on 21-03-2016.
 */
public class BaseFragment extends Fragment implements OnClickListener {

    public static final TouchEffect TOUCH = new TouchEffect();
    FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    /**
     * Method use to set Touch and Click listener on View.
     *
     * @param id   Resource id of View
     * @param view your layout view.
     * @return
     */
    public View setTouchNClick(int id, View view) {

        View v = view.findViewById(id);
        v.setOnTouchListener(TOUCH);
        v.setOnClickListener(this);
        return v;
    }

    /**
     * Method use to set Click listener on View.
     *
     * @param id   Resource id of View
     * @param view your layout view.
     * @return
     */
    public View setClick(int id, View view) {

        View v = view.findViewById(id);
        v.setOnClickListener(this);
        return v;
    }

    /**
     * Method use to enable/disable view.
     *
     * @param id   resource id.
     * @param view
     * @param flag flag true if you want to make view enable else false
     */
    public void setViewEnable(int id, View view, boolean flag) {
        View v = view.findViewById(id);
        v.setEnabled(flag);
    }

    /**
     * Method use to set ViewVisiblity
     *
     * @param id   id resource id of View.
     * @param view
     * @param flag flag value can be VISIBLE, GONE, INVISIBLE.
     */
    public void setViewVisibility(int id, View view, int flag) {
        View v = view.findViewById(id);
        v.setVisibility(flag);
    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        Intent i = null;
        switch (arg0.getId()) {

        }
    }

    /**
     * Method use to set text on TextView, EditText, Button.
     *
     * @param id   resource of TextView, EditText, Button.
     * @param text string you want to set on TextView, EditText, Button
     * @param view
     */
    public void setViewText(int id, String text, View view) {
        View v = ((View) view.findViewById(id));
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setText(text);
        }
        if (v instanceof EditText) {
            EditText et = (EditText) v;
            et.setText(text);
        }
        if (v instanceof Button) {
            Button btn = (Button) v;
            btn.setText(text);
        }

    }


    /**
     * Method use to get Text from TextView, EditText, Button.
     *
     * @param id   resource id TextView, EditText, Button
     * @param view
     * @return string text from view
     */
    public String getViewText(int id, View view) {
        String text = "";
        View v = ((View) view.findViewById(id));
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            text = tv.getText().toString().trim();
        }
        if (v instanceof EditText) {
            EditText et = (EditText) v;
            text = et.getText().toString().trim();
        }
        if (v instanceof Button) {
            Button btn = (Button) v;
            text = btn.getText().toString().trim();
        }
        return text;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public ProgressDialog getProgressDialog(boolean isCancalable){
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(isCancalable);
        progressDialog.setTitle(getString(R.string.loading_msg));

        return progressDialog;
    }
}
