package com.dineplan.adpaters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.activities.Payment1Activity;
import com.dineplan.activities.PaymentCashActivity;
import com.dineplan.model.PaymentType;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 24/09/16.
 */

public class SplitPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private ArrayList<PaymentType> paymentTypes;
    private Context context;
    public SplitPaymentAdapter(Context context, ArrayList<PaymentType>  paymentTypes){
        this.paymentTypes=paymentTypes;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi= LayoutInflater.from(context).inflate(R.layout.payment_type_item,parent,false);
        PaymentTypeAdapter.PaymentHolder paymentHolder=new PaymentTypeAdapter.PaymentHolder(vi);
        return paymentHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PaymentTypeAdapter.PaymentHolder paymentHolder=(PaymentTypeAdapter.PaymentHolder)holder;
        paymentHolder.paymentType.setText(paymentTypes.get(position).getName());
        if(paymentTypes.get(position).getName().equalsIgnoreCase("cash")){
            paymentHolder.paymentIcon.setImageResource(R.drawable.cash_icon);
        }
        paymentHolder.itemView.setTag(paymentTypes.get(position));
        paymentHolder.itemView.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return paymentTypes.size();
    }

    @Override
    public void onClick(View view) {
        PaymentType paymentType=(PaymentType) view.getTag();
        Intent intent=new Intent(context, PaymentCashActivity.class);
        intent.putExtra("payment",paymentType);
        intent.putExtras(((Payment1Activity)context).getIntent().getExtras());
        context.startActivity(intent);
    }

    class PaymentHolder extends RecyclerView.ViewHolder{
        public ImageView paymentIcon;
        public TextView paymentType;
        public PaymentHolder(View itemView) {
            super(itemView);
            paymentIcon=(ImageView)itemView.findViewById(R.id.iv_image);
            paymentType=(TextView)itemView.findViewById(R.id.tv_type);
        }
    }
}
