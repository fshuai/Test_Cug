package com.example.test_cug1;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MuseumProductActivity extends Activity implements OnGestureListener{
	
	private GestureDetector detector;
	private ViewFlipper flipper;
	private Animation[] animations=new Animation[4];
    private final static int FLIP_DISTANCE=60;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.museum_product);
		setTitle("»¯Ê¯¿óÎï");
		detector=new GestureDetector(this,this);
		flipper=(ViewFlipper)findViewById(R.id.museum_flipper);
		
		flipper.addView(addImageView(R.drawable.product1));
		flipper.addView(addImageView(R.drawable.product2));
		flipper.addView(addImageView(R.drawable.product3));
		flipper.addView(addImageView(R.drawable.product4));
		flipper.addView(addImageView(R.drawable.product5));
		flipper.addView(addImageView(R.drawable.product6));
		flipper.addView(addImageView(R.drawable.product7));
		
		animations[0]=AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1]=AnimationUtils.loadAnimation(this, R.anim.left_out);
		animations[2]=AnimationUtils.loadAnimation(this, R.anim.right_in);
		animations[3]=AnimationUtils.loadAnimation(this, R.anim.right_out);
	}
	
	private View addImageView(int resId){
		ImageView imageView=new ImageView(this);
		imageView.setImageResource(resId);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		return imageView;
	}
	

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(e1.getX()-e2.getX()>FLIP_DISTANCE){
			flipper.setInAnimation(animations[0]);
			flipper.setOutAnimation(animations[1]);
			flipper.showPrevious();
		}
		else if(e2.getX()-e1.getX()>FLIP_DISTANCE){
			flipper.setInAnimation(animations[2]);
			flipper.setOutAnimation(animations[3]);
			flipper.showNext();
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return detector.onTouchEvent(event);
	}
    
}
