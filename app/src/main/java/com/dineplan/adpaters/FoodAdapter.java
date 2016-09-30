package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.ShowPortions;
import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.Category;
import com.dineplan.model.MenuItem;
import com.dineplan.model.MenuPortion;
import com.dineplan.model.OrderItem;
import com.dineplan.model.Tax;
import com.dineplan.utility.Utils;

import java.util.ArrayList;


public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private ArrayList<MenuItem> items;
    private DbHandler dbHandler;
    private ShowPortions showPortions;

    public FoodAdapter(Context context,ArrayList<MenuItem> items,ShowPortions showPortions){
        this.context= context;
        this.items=new ArrayList<MenuItem>(items);
        dbHandler=new DbHandler(context);
        this.showPortions=showPortions;
    }


    class ViewHolderFood extends RecyclerView.ViewHolder {
        private TextView tvItemName, tvPrice;
        public ViewHolderFood(View itemView) {
            super(itemView);
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
        viewHolderFood.tvPrice.setText("$"+Utils.roundTwoDecimals(items.get(position).getPrice()));
        final int pos=position;
        viewHolderFood.itemView.setOnClickListener(new View.OnClickListener() {
            int posit=pos;
            @Override
            public void onClick(View view) {
                MenuItem menuItem=items.get(posit);
                MenuItem item=dbHandler.getMenuItemDetail(menuItem);
                if(item.getMenuPortions().size()>1){
                    showPortions.addFoodOrder(menuItem);
                }else
                if(menuItem.getOrderTagGroups().size()>0){
                    showPortions.addFoodOrder(menuItem);
                }else{
                    OrderItem orderItem=new OrderItem();
                    orderItem.setItemName(menuItem.getName());
                    orderItem.setItemId(menuItem.getId());
                    orderItem.setQuantity(1);
                    orderItem.setPrice(menuItem.getMenuPortions().get(0).getPrice());
                    orderItem.setMenuPortion(menuItem.getMenuPortions().get(0));
                    float taxcount=0;
                    for(Tax tax:menuItem.getTaxes()){
                            taxcount+=((tax.getPercentage()*orderItem.getMenuPortion().getPrice())/100);
                    }
                    orderItem.setTaxAmount(taxcount);
                    showPortions.AddItem(orderItem);
                }
            }
        });
    }

    public void filter(String query, Category category, ArrayList<MenuItem> fullList){

        if(fullList!=null && fullList.size()>0){
            this.items.clear();
            boolean categoryIgnore = false;
            if(category.getId() == 0)
                categoryIgnore = true;
            query = query.toLowerCase();
            for(MenuItem dto : fullList){
                if(categoryIgnore){
                    if(dto.getName()!=null
                            && dto.getName().toLowerCase().contains(query)){
                        items.add(dto);
                    }else{
                        if(dto.getMenuPortions()!=null && dto.getMenuPortions().size()>0){
                            for(MenuPortion menuPortion : dto.getMenuPortions()){
                                if(menuPortion.getPortionName()!=null &&
                                        menuPortion.getPortionName().toLowerCase().contains(query)){
                                    items.add(dto);
                                    break;
                                }
                            }
                        }
                    }
                }
                else{
                    if(category.getId() ==dto.getCategoryId() && dto.getName()!=null
                            && dto.getName().toLowerCase().contains(query)){
                        items.add(dto);
                    }
                    else{
                        if(dto.getMenuPortions()!=null && dto.getMenuPortions().size()>0){
                            for(MenuPortion menuPortion : dto.getMenuPortions()){
                                if(menuPortion.getPortionName()!=null &&
                                        menuPortion.getPortionName().toLowerCase().contains(query)){
                                    items.add(dto);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();

    }


    class AniList implements Animation.AnimationListener{


        private View vi;
        public AniList(View vi){
            this.vi=vi;
        }
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
                vi.clearAnimation();
        }
    }
}