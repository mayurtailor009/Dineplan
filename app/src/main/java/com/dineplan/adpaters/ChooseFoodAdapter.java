package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineplan.AddOrderTag;
import com.dineplan.R;
import com.dineplan.model.OrderTag;
import com.dineplan.model.OrderTagGroup;

import java.util.ArrayList;


/**
 * Created by sandeepjoshi on 01/09/16.
 */
public class ChooseFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<OrderTagGroup> grps;
    ArrayList<OrderTag> orderTags=null;
    private AddOrderTag addOrderTag;
    public ChooseFoodAdapter(Context context, ArrayList<OrderTagGroup> grps,AddOrderTag addOrderTag){
        this.context= context;
        this.grps=grps;
        this.addOrderTag=addOrderTag;
        orderTags=new ArrayList<>();
        for(OrderTagGroup group:grps){
            for(OrderTag orderTag:group.getOrderTags()){
                orderTag.setTagGroupName(group.getName());
                orderTag.setTagGroupId(group.getId());
            }
            orderTags.add(new OrderTag(group.getName()));
            orderTags.addAll(group.getOrderTags());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(orderTags.get(position).getId()==0){
            return  1;
        }else{
            return 2;
        }
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
        return orderTags.size();
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
            ((ViewHolder) holder).category.setText(orderTags.get(position).getName());

        }else {
            ViewHolderFood viewHolderFood = (ViewHolderFood) holder;
            viewHolderFood.tvItemName.setText(orderTags.get(position).getName());
            if (orderTags.get(position).getPrice() != 0) {
                viewHolderFood.tvPrice.setText("$"+String.valueOf(orderTags.get(position).getPrice()));

            }
            viewHolderFood.itemView.setSelected(false);
            final int pos=position;
            viewHolderFood.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posit=pos;
                    if(view.isSelected()){
                        view.setSelected(false);
                        ((TextView) view.findViewById(R.id.tv_dish_name1)).setTextColor(context.getResources().getColor(R.color.black));
                        ((TextView) view.findViewById(R.id.tv_price)).setTextColor(context.getResources().getColor(R.color.black));
                        addOrderTag.removeOrderTag(orderTags.get(posit));
                    }else {
                        view.setSelected(true);
                        ((TextView) view.findViewById(R.id.tv_dish_name1)).setTextColor(context.getResources().getColor(R.color.white));
                        ((TextView) view.findViewById(R.id.tv_price)).setTextColor(context.getResources().getColor(R.color.white));
                        addOrderTag.addOrderTag(orderTags.get(posit));
                    }
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
