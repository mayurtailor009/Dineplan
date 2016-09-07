package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineplan.R;



public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    public FoodAdapter(Context context){
        this.context= context;
    }
    private OnItemClickListner onItemClickListner;

    class ViewHolderFood extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView tvItemName, tvPrice;
        public ViewHolderFood(View itemView) {
            super(itemView);

            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListner!=null){
                onItemClickListner.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface OnItemClickListner {
        void onItemClick(View view, int position);
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
                .inflate(R.layout.item_food, parent, false);
        return new ViewHolderFood(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderFood viewHolderFood = (ViewHolderFood) holder;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }
}