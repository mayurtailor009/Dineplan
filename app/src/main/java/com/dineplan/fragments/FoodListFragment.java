package com.dineplan.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.dineplan.R;
import com.dineplan.activities.HomeActivity;
import com.dineplan.adpaters.FoodAdapter;


public class FoodListFragment extends BaseFragment implements FoodAdapter.OnItemClickListner, View.OnClickListener{

    View view;

    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    private ImageView ivSearch;
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
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);

        ivSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()){
            case R.id.iv_search:
                animateView(ivSearch, ((HomeActivity)getActivity()).getTargetView());
                break;
        }
    }

    public void setFoodList(){
        foodAdapter = new FoodAdapter(getActivity());
        foodAdapter.setOnItemClickListner(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(foodAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();

        /*TextView tvCategory = (TextView) view.findViewById(R.id.tv_category);
        float x = view.getX();
        float y = view.getY();
        animateDiagonalPan(tvCategory, view, ((HomeActivity)getActivity()).getTargetView());*/
    }

    private void animateDiagonalPan(View sourceView, View calView, View targetView) {
        AnimatorSet animSetXY = new AnimatorSet();
       float xx = calView.getX();
        float yy = calView.getY();

        float dx = targetView.getX();
        float dy = targetView.getY();

        /*ObjectAnimator y = ObjectAnimator.ofFloat(v,
                "translationY",0, -500);

        ObjectAnimator x = ObjectAnimator.ofFloat(v,
                "translationX", v.getX(), tvHere.getX());*/

        float deltaX = targetView.getX() - calView.getLeft();
        float deltaY = targetView.getY() - calView.getTop();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(sourceView, "translationX", deltaX);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(sourceView, "translationY", deltaY);
        ObjectAnimator rotaion = ObjectAnimator.ofFloat(sourceView, "rotation", 360);

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(sourceView, "scaleX", 0.5f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(sourceView, "scaleY", 0.5f);

        animSetXY.playTogether(translateX, translateY, rotaion, scaleDownX, scaleDownY);
        animSetXY.setInterpolator(new LinearInterpolator());
        animSetXY.setDuration(500);
        animSetXY.start();
    }

    private void animateView(View sourceView, View targetView) {
        AnimatorSet animSetXY = new AnimatorSet();
        float xx = sourceView.getX();
        float yy = sourceView.getY();

        float dx = targetView.getX();
        float dy = targetView.getY();

        /*ObjectAnimator y = ObjectAnimator.ofFloat(v,
                "translationY",0, -500);

        ObjectAnimator x = ObjectAnimator.ofFloat(v,
                "translationX", v.getX(), tvHere.getX());*/

        float deltaX = targetView.getX() - sourceView.getLeft();
        float deltaY = targetView.getY() - sourceView.getTop();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(sourceView, "translationX", deltaX);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(sourceView, "translationY", deltaY);
        ObjectAnimator rotaion = ObjectAnimator.ofFloat(sourceView, "rotation", 360);

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(sourceView, "scaleX", 0.5f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(sourceView, "scaleY", 0.5f);

        animSetXY.playTogether(translateX, translateY, rotaion, scaleDownX, scaleDownY);
        animSetXY.setInterpolator(new LinearInterpolator());
        animSetXY.setDuration(500);
        animSetXY.start();
    }
}
