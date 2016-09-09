package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.ShowPortions;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.MenuItem;
import com.dineplan.model.OrderItem;

import java.util.ArrayList;


public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private ArrayList<MenuItem> items;
    private DbHandler dbHandler;
    private ShowPortions showPortions;

    public FoodAdapter(Context context,ArrayList<MenuItem> items,ShowPortions showPortions){
        this.context= context;
        this.items=items;
        dbHandler=new DbHandler(context);
        this.showPortions=showPortions;
    }


    class ViewHolderFood extends RecyclerView.ViewHolder {
        private TextView tvItemName, tvPrice,tv_subheading;
        public ViewHolderFood(View itemView) {
            super(itemView);
            tv_subheading=(TextView) itemView.findViewById(R.id.tv_subheading);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View v = null;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new ViewHolderFood(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderFood viewHolderFood = (ViewHolderFood) holder;
        viewHolderFood.tvItemName.setText(items.get(position).getName());
        viewHolderFood.tvPrice.setText("$"+String.valueOf(items.get(position).getPrice()));
        viewHolderFood.tv_subheading.setText(items.get(position).getAliasCode());
        final int pos=position;
        viewHolderFood.itemView.setOnClickListener(new View.OnClickListener() {
            int posit=pos;
            @Override
            public void onClick(View view) {
                MenuItem menuItem=items.get(posit);
                MenuItem item=dbHandler.getMenuItemDetail(menuItem);
                if(item.getMenuPortions().size()>1){
                    showPortions.showPortions(menuItem);
                }else
                if(menuItem.getOrderTagGroups().size()>0){
                    showPortions.addFoodOrder(menuItem);
                }else{
                    OrderItem orderItem=new OrderItem();
                    orderItem.setMenuPortion(menuItem.getMenuPortions().get(0));
                    showPortions.AddItem(orderItem);
                }
            }
        });
    }

}