package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.Tax;

import java.util.ArrayList;


/**
 * Created by sandeepjoshi on 20/09/16.
 */
public class TaxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private ArrayList<Tax> taxes;

    public TaxAdapter(Context context,ArrayList<Tax> taxes){
        this.context=context;
        this.taxes=taxes;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.department_heading, parent, false);
            return new ViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tax_item, parent, false);
            return  new TaxHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TaxHolder){
            TaxHolder taxHolder=(TaxHolder)holder;
            taxHolder.textView.setText(taxes.get(position-1).getName()+" "+taxes.get(position-1).getPercentage()+"%");
            taxHolder.aSwitch.setTag(taxes.get(position-1));
            taxHolder.aSwitch.setOnCheckedChangeListener(this);
        }else{
            ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.category.setText(context.getResources().getText(R.string.lable_taxes));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return taxes.size()+1;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
          Tax tax= (Tax) compoundButton.getTag();
           tax.setChecked(true);
    }


    class TaxHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public Switch aSwitch;
        public TaxHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.tv_name);
            aSwitch=(Switch)itemView.findViewById(R.id.sw_tax);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView category;

        public ViewHolder(View itemView) {
            super(itemView);
            category=(TextView)itemView.findViewById(R.id.tv_category);
        }
    }
}
