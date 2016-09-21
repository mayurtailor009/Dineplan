package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineplan.AddOrderTag;
import com.dineplan.R;
import com.dineplan.model.MenuPortion;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 12/09/16.
 */
public class MenuPortionAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<MenuPortion> menuPortions=null;
    private AddOrderTag addOrderTag;
    public MenuPortionAdapter(Context context,ArrayList<MenuPortion> menuPortions,AddOrderTag addOrderTag){
        this.context= context;
        this.addOrderTag=addOrderTag;
        this.menuPortions=menuPortions;
    }


    @Override
    public int getItemViewType(int position) {
       /* if(menuPortions.get(position).getId()==0){
            return  1;
        }else{*/
            return 2;
        //}
    }

    class ViewHolderFood extends RecyclerView.ViewHolder {
        private TextView tvItemName, tvPrice;
        public ViewHolderFood(View itemView) {
            super(itemView);
            tvItemName=(TextView)itemView.findViewById(R.id.tv_dish_name1);
            tvPrice=(TextView)itemView.findViewById(R.id.tv_price);
        }
    }

    @Override
    public int getItemCount() {
        return menuPortions.size();
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_heading, parent, false);
            return new ViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_choose_food, parent, false);
            return new ViewHolderFood(v);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ViewHolder){
          /*  ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.category.setText();*/

        }else {
            ViewHolderFood viewHolderFood = (ViewHolderFood) holder;
            viewHolderFood.tvItemName.setText(menuPortions.get(position).getPortionName());
            if (menuPortions.get(position).getPrice() != 0) {
                viewHolderFood.tvPrice.setText("$"+String.valueOf(menuPortions.get(position).getPrice()));

            }
            viewHolderFood.itemView.setSelected(false);
            final int pos=position;
            if(menuPortions.get(position).isSelected()){
                viewHolderFood.itemView.setSelected(true);
                ((TextView) viewHolderFood.itemView.findViewById(R.id.tv_dish_name1)).setTextColor(context.getResources().getColor(R.color.white));
                ((TextView) viewHolderFood.itemView.findViewById(R.id.tv_price)).setTextColor(context.getResources().getColor(R.color.white));
            }else{
                viewHolderFood.itemView.setSelected(false);
                ((TextView) viewHolderFood.itemView.findViewById(R.id.tv_dish_name1)).setTextColor(context.getResources().getColor(R.color.black));
                ((TextView) viewHolderFood.itemView.findViewById(R.id.tv_price)).setTextColor(context.getResources().getColor(R.color.black));
            }
            viewHolderFood.itemView.setOnClickListener(new View.OnClickListener() {
                int posit=pos;
                @Override
                public void onClick(View view) {

                    for(MenuPortion portion:menuPortions){
                        portion.setSelected(false);
                    }
                       addOrderTag.addPortions(menuPortions.get(posit));
                        menuPortions.get(posit).setSelected(true);
                        MenuPortionAdapter.this.notifyDataSetChanged();

                }
            });
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


