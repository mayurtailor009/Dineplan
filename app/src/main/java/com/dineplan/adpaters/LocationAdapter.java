package com.dineplan.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.Location;

import java.util.ArrayList;


/**
 * Created by sandeepjoshi on 01/09/16.
 */
public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private RadioButton selectedRadioButton=null;
    private ArrayList<Location> locations;
    private Location selectedLocation=null;
    private View vi;
    public LocationAdapter(Context context, ArrayList<Location> locations,View vi,Location selectedLocation){
        this.context= context;
        this.locations=locations;
        this.vi=vi;
        this.selectedLocation=selectedLocation;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b) {
            vi.setVisibility(View.VISIBLE);
            Location location=(Location)compoundButton.getTag();
            if (selectedRadioButton != null)
                selectedRadioButton.setChecked(false);
            selectedRadioButton=(RadioButton)compoundButton;
            for(Location loc:locations){
                location.setSelected(false);
            }
            location.setSelected(b);
        }
    }


    class ViewHolderFood extends RecyclerView.ViewHolder {
        private TextView tvItemName, tvPrice;
        private RadioButton radioButton;
        public ViewHolderFood(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            radioButton = (RadioButton)itemView.findViewById(R.id.rd_btn);

        }
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View v = null;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);

        return new ViewHolderFood(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderFood viewHolderFood = (ViewHolderFood) holder;
        viewHolderFood.radioButton.setTag(locations.get(position));
        viewHolderFood.tvItemName.setText(locations.get(position).getName());
        if(selectedLocation!=null && selectedLocation.getId()==locations.get(position).getId()) {
            vi.setVisibility(View.VISIBLE);
            viewHolderFood.radioButton.setChecked(true);
            locations.get(position).setSelected(true);
        }
        viewHolderFood.radioButton.setOnCheckedChangeListener(this);
    }

}
