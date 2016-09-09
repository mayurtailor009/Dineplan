package com.dineplan.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineplan.R;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        final TextView tvHere = (TextView) findViewById(R.id.tv_here);
        Button btnStart = (Button) findViewById(R.id.btn_start);
        final ImageView imageView = (ImageView) findViewById(R.id.image_view);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateDiagonalPan(imageView, tvHere);
            }
        });
    }

    private void animateDiagonalPan(View source, View target) {
        AnimatorSet animSetXY = new AnimatorSet();
        float xx = source.getX();
        float yy = source.getY();

        float dx = target.getX();
        float dy = target.getY();


        float deltaX = target.getX() - source.getLeft();
        float deltaY = target.getY() - source.getTop();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(source, "translationX", deltaX);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(source, "translationY", deltaY);
        ObjectAnimator rotaion = ObjectAnimator.ofFloat(source, "rotation", 360);

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(source, "scaleX", 0.5f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(source, "scaleY", 0.5f);

        animSetXY.playTogether(translateX, translateY, rotaion, scaleDownX, scaleDownY);
        animSetXY.setInterpolator(new LinearInterpolator());
        animSetXY.setDuration(500);
        animSetXY.start();
    }
}
