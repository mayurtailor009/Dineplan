package com.dineplan.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.Category;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 07/09/16.
 */
public class CategoryAdapter extends BaseAdapter{

    private ArrayList<Category> categories;
    private Context context;
    public CategoryAdapter(Context context,ArrayList<Category> categories){
            this.categories=categories;
            this.context=context;
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_category,null);
            holder=new ViewHolder();
            holder.category=(TextView) view.findViewById(R.id.tv_category);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.category.setText(categories.get(position).getName());
        return view;
    }


    class ViewHolder{
        private TextView category;
    }
}
