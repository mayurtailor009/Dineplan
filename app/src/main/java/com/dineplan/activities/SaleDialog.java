package com.dineplan.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dineplan.R;
import com.dineplan.ShowPortions;
import com.dineplan.adpaters.SaleAdapter;
import com.dineplan.model.OrderItem;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 14/09/16.
 */
public class SaleDialog extends Dialog implements View.OnClickListener{


    private View root;
    private RecyclerView sale;
    private ArrayList<OrderItem> orderItems;
    private Context context;
    private ImageView btn_drop;
    private LinearLayout clearSale;
    private ShowPortions showPortions;
    public SaleDialog(Context context, ArrayList<OrderItem> orderItems, ShowPortions showPortions) {
        super(context,R.style.full_screen_dialog);
        this.context=context;
        this.showPortions=showPortions;
        this.orderItems=orderItems;
    }

    public SaleDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SaleDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale);
        init();
    }


    public void init() {
        clearSale=(LinearLayout)findViewById(R.id.ll_clear_sale);
        btn_drop=(ImageView) findViewById(R.id.btn_drop);
        btn_drop.setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        sale=(RecyclerView)findViewById(R.id.rec_sale);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        sale.setLayoutManager(mLayoutManager);
        sale.setItemAnimator(new DefaultItemAnimator());
        sale.setAdapter(new SaleAdapter(orderItems,context));
        clearSale.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_clear_sale:
                cancel();
                showPortions.resetOrderItems();
                break;
            case R.id.btn_back:
                cancel();
                break;
            case R.id.btn_drop:
                if(clearSale.getVisibility()==View.VISIBLE) {
                    changeArrow(R.drawable.up_arrow_icon);
                    clearSale.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    clearSale.setVisibility(View.GONE);
                                }
                            });
                }
                else {
                    clearSale.setVisibility(View.VISIBLE);
                    clearSale.animate()
                            .translationY(0)
                            .alpha(1.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                }
                            });
                    Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                    a.setFillAfter(true);
                    changeArrow(R.drawable.down_arrow_icon);
                }
                break;
        }
    }


    public void changeArrow(final int resource){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                btn_drop.setImageResource(resource);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }});
        btn_drop.startAnimation(animation);

    }
}
