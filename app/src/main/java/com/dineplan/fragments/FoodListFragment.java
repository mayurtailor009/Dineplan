package com.dineplan.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dineplan.R;
import com.dineplan.adpaters.FoodAdapter;


public class FoodListFragment extends BaseFragment {

    View view;

    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_list, container, false);

        init();

        setFoodList();

        return view;
    }

    private void init() {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

    }

    @Override
    public void onClick(View arg0) {

    }

    public void setFoodList(){
        foodAdapter = new FoodAdapter(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(foodAdapter);
    }
}
