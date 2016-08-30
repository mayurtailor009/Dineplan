package com.dineplan.utility;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * Created by mayur.tailor on 21-03-2016.
 */
public class TouchEffect implements OnTouchListener {

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if(v instanceof ImageView){
			ImageView iv = (ImageView) v;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				iv.setAlpha(150);
			} else if (event.getAction() == MotionEvent.ACTION_UP
					|| event.getAction() == MotionEvent.ACTION_CANCEL) {
				iv.setAlpha(255);
			}
		}
		else {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Drawable d = v.getBackground();
				if( d != null )
				{
					d.mutate();
					d.setAlpha(150);
					v.setBackgroundDrawable(d);
				}

			} else if (event.getAction() == MotionEvent.ACTION_UP
					|| event.getAction() == MotionEvent.ACTION_CANCEL) {
				Drawable d = v.getBackground();
				if( d != null )
				{
					d.setAlpha(255);
					v.setBackgroundDrawable(d);
				}

			}
		}
		return false;
	}

}
