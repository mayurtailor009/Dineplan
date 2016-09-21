package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.OrderItem;
import com.dineplan.model.OrderTag;
import com.dineplan.utility.Utils;

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
        if(viewType==0){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sale_list_header, parent, false);
            return new ViewHolder(v);
        }else if(viewType==1){
            View v = LayoutInflater.from(context).inflate(R.layout.sale_item, null);
            TaxHolder saleHolder = new TaxHolder(v);
            return saleHolder;
        }else{
            View v = LayoutInflater.from(context).inflate(R.layout.sale_item, null);
            SaleHolder saleHolder = new SaleHolder(v);
            return saleHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TaxHolder){
            TaxHolder saleHolder = (TaxHolder) holder;
            float tax=0;
            for(OrderItem orderItem:orderItems){
                        tax+=  orderItem.getTaxAmount();
            }
            saleHolder.itemPrice.setText("$" +Utils.roundTwoDecimals(tax));
            saleHolder.itemName.setText(context.getResources().getText(R.string.lable_tax));
            saleHolder.quantity.setText("");

        }else
        if(holder instanceof SaleHolder) {
            SaleHolder saleHolder = (SaleHolder) holder;
            saleHolder.itemPrice.setText("$" + calculatePrice(orderItems.get(position - 1)));
            saleHolder.itemName.setText(orderItems.get(position - 1).getItemName());
            if(orderItems.get(position - 1).getQuantity()>1)
                saleHolder.quantity.setText(" X " + orderItems.get(position - 1).getQuantity());
        }else{
            ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.category.setText(orderItems.get(0).getOrderType());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return  0;
        else if(position==orderItems.size()+1)
            return 1;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return orderItems.size()+2;
    }


    class SaleHolder extends RecyclerView.ViewHolder{
        public TextView itemName,itemPrice,quantity;
        public SaleHolder(View itemView) {
            super(itemView);
            quantity=(TextView)itemView.findViewById(R.id.tv_quantity);
            itemName=(TextView) itemView.findViewById(R.id.tv_item);
            itemPrice=(TextView)itemView.findViewById(R.id.tv_price);

        }
    }


    class TaxHolder extends RecyclerView.ViewHolder{
        public TextView itemName,itemPrice,quantity;
        public TaxHolder(View itemView) {
            super(itemView);
            quantity=(TextView)itemView.findViewById(R.id.tv_quantity);
            itemName=(TextView) itemView.findViewById(R.id.tv_item);
            itemPrice=(TextView)itemView.findViewById(R.id.tv_price);

        }
    }



    public String calculatePrice(OrderItem orderItem){
        float price=orderItem.getMenuPortion().getPrice();
        if(orderItem.getOrderTags()!=null) {
            for (OrderTag tag : orderItem.getOrderTags()) {
                price=price+tag.getPrice();
            }
        }
        if(orderItem.getOrderTags()!=null){
            for(OrderTag tag:orderItem.getOrderTags()){
                price+=tag.getPrice();
            }
        }
        price=price*orderItem.getQuantity();
        return Utils.roundTwoDecimals(price);
    }


    public float calculatefloatPrice(OrderItem orderItem){
        float price=orderItem.getMenuPortion().getPrice();
        if(orderItem.getOrderTags()!=null) {
            for (OrderTag tag : orderItem.getOrderTags()) {
                price=price+tag.getPrice();
            }
        }
        if(orderItem.getOrderTags()!=null){
            for(OrderTag tag:orderItem.getOrderTags()){
                price+=tag.getPrice();
            }
        }
        price=price*orderItem.getQuantity();
        return  price;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView category;

        public ViewHolder(View itemView) {
            super(itemView);
            category=(TextView)itemView.findViewById(R.id.tv_category);
        }
    }
}
