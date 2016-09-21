package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.Department;
import com.dineplan.model.OrderItem;

import java.util.ArrayList;


/**
 * Created by sandeepjoshi on 19/09/16.
 */
public class DepartmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<Department> departments;
    private OrderItem orderItem;
    public DepartmentAdapter(Context context, ArrayList<Department> departmentList, OrderItem orderItem) {
        this.context=context;
        this.orderItem=orderItem;
        this.departments=departmentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.department_heading, parent, false);
            return new HeadingHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.department_item, parent, false);
            return new ViewHolder(v);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return  1;
        }else{
        return 2;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof HeadingHolder){
                HeadingHolder headingHolder=(HeadingHolder)holder;
                headingHolder.department.setText("Departments");
            }else{
                ViewHolder viewHolder=(ViewHolder)holder;
                viewHolder.department.setText(departments.get(position-1).getName());
                viewHolder.itemView.setTag(position-1);
                viewHolder.itemView.setOnClickListener(this);
                if(departments.get(position-1).isSelected()){
                    viewHolder.itemView.setSelected(true);
                    viewHolder.department.setTextColor(context.getResources().getColor(R.color.white));
                }else{
                    viewHolder.itemView.setSelected(false);
                    viewHolder.department.setTextColor(context.getResources().getColor(R.color.black));

                }
            }
    }

    @Override
    public int getItemCount() {
        return departments.size()+1;
    }

    @Override
    public void onClick(View view) {
        Department department=departments.get((Integer) view.getTag());
        for(Department depart:departments){
                depart.setSelected(false);
        }
        department.setSelected(true);
        orderItem.setDepartment(department);
        this.notifyDataSetChanged();
    }


    class HeadingHolder extends RecyclerView.ViewHolder{
        public TextView department;

        public HeadingHolder(View itemView) {
            super(itemView);
            department=(TextView)itemView.findViewById(R.id.tv_category);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView department;

        public ViewHolder(View itemView) {
            super(itemView);
            department=(TextView)itemView.findViewById(R.id.tv_department);
        }
    }



}
