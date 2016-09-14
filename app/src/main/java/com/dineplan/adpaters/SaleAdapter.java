package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.OrderItem;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 14/09/16.
 */
public class SaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<OrderItem>  orderItems;
    private Context context;
    public SaleAdapter(ArrayList<OrderItem>  orderItems,Context context){
       this.orderItems=orderItems;
       this.context=context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.sale_item,null);
        SaleHolder saleHolder=new SaleHolder(v);
        return saleHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SaleHolder saleHolder=(SaleHolder)holder;
        saleHolder.itemPrice.setText(String.valueOf(orderItems.get(position).getPrice()));
        saleHolder.itemName.setText(orderItems.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }


    class SaleHolder extends RecyclerView.ViewHolder{
        public TextView itemName,itemPrice;
        public SaleHolder(View itemView) {
            super(itemView);
            itemName=(TextView) itemView.findViewById(R.id.tv_item);
            itemPrice=(TextView)itemView.findViewById(R.id.tv_price);
        }
    }
}
