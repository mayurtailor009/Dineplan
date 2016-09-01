package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dineplan.R;



/**
 * Created by sandeepjoshi on 01/09/16.
 */
public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private RadioButton selectedRadioButton=null;
    public LocationAdapter(Context context){
        this.context= context;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b) {
            if (selectedRadioButton != null)
                selectedRadioButton.setChecked(false);
            selectedRadioButton=(RadioButton)compoundButton;
        }
    }


    class ViewHolderFood extends RecyclerView.ViewHolder {
        private TextView tvItemName, tvPrice;
        private RadioButton radioButton;
        public ViewHolderFood(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            radioButton = (RadioButton)itemView.findViewById(R.id.rd_btn);
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View v = null;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new ViewHolderFood(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderFood viewHolderFood = (ViewHolderFood) holder;
        viewHolderFood.radioButton.setOnCheckedChangeListener(this);

    }

}
