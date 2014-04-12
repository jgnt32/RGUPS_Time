package ru.rgups.time.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class CalendarHint extends TextView {

	public CalendarHint(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public CalendarHint(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public CalendarHint(Context context) {
		super(context);

	}
	
	public void showHint(){
		setVisibility(View.VISIBLE);
		this.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				setVisibility(View.GONE);
			}
		}, 600);
	}

	public void show(){
		Animation animation = new TranslateAnimation(0,0,1000,0);
		animation.setDuration(1000);
		this.setAnimation(animation);
		animation.setAnimationListener(new ShowAnimationListener(this));
		this.animate();
		
	}
	
	public void hide(){
		Animation animation = new TranslateAnimation(0,0,0,1000);
		animation.setDuration(1000);
		this.setAnimation(animation);
		animation.setAnimationListener(new HidingAnimationsListener(this));
		this.animate();
	}

	private class ShowAnimationListener implements AnimationListener{

		private View mView;

		public ShowAnimationListener(View v) {
			mView = v;
				
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			mView.setVisibility(View.VISIBLE);
		}
		
	}
	
	private class HidingAnimationsListener implements AnimationListener{

		private View mView;
		
		public HidingAnimationsListener(View v) {
			mView = v;
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			mView.setVisibility(View.GONE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
