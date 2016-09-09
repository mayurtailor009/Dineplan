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
import com.dineplan.ShowPortions;
import com.dineplan.activities.AddFoodActivity;
import com.dineplan.model.MenuItem;
import com.dineplan.model.OrderItem;


/**
 * Created by sandeepjoshi on 08/09/16.
 */
public class MenuPortionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private MenuItem menuItem;
    private Context context;
    private ShowPortions showPortions;
    public MenuPortionAdapter(MenuItem menuItem,Context context,ShowPortions showPortions){
        this.menuItem=menuItem;
        this.showPortions=showPortions;
        this.context=context;
    }

    @Override
    public void onClick(View view) {
        showPortions.showMenu();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView menuItem;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            menuItem=(TextView)itemView.findViewById(R.id.tv_heading);
            imageView=(ImageView) itemView.findViewById(R.id.btn_back);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View v = null;
        if(viewType==1){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_portion_header, parent, false);
            return new ViewHolder(v);
        }else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_food, parent, false);
            return new ViewHolderFood(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  ViewHolderFood) {
            ViewHolderFood viewHolderFood = (ViewHolderFood) holder;
            viewHolderFood.tvItemName.setText(menuItem.getMenuPortions().get(position).getPortionName());
            viewHolderFood.tvPrice.setText("$" + String.valueOf(menuItem.getMenuPortions().get(position).getPrice()));
            viewHolderFood.tv_subheading.setText(menuItem.getAliasCode());
            final int pos = position;
            viewHolderFood.itemView.setOnClickListener(new View.OnClickListener() {
                int posit = pos;

                @Override
                public void onClick(View view) {
                    if (menuItem.getOrderTagGroups().size() > 0) {
                        Intent intent = new Intent(context, AddFoodActivity.class);
                        intent.putExtra("menuItem", menuItem);
                        context.startActivity(intent);
                    } else {
                        OrderItem orderItem=new OrderItem();
                        orderItem.setMenuPortion(menuItem.getMenuPortions().get(posit));
                        showPortions.AddItem(orderItem);
                    }
                }
            });
        }else{
            ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.menuItem.setText(menuItem.getName());
            viewHolder.itemView.setOnClickListener(this);
        }
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
        return menuItem.getMenuPortions().size();
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 1;

        }else
        {
            return 2;
        }
    }
}
