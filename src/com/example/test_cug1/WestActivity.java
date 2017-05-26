package com.example.test_cug1;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class WestActivity extends Activity {
	
	//private ViewFlipper flipper; 
	//private GestureDetector detector;
	//private static final int FLIP_DISTANCE=60;
	//Animation [] animations=new Animation[4];
	private PhotoSortrView photoSorter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle("西区风光");
		photoSorter = new PhotoSortrView(this);
		setContentView(photoSorter);
		//flipper=(ViewFlipper)findViewById(R.id.west_flipper);
		//flipper添加imageview控件
		//flipper.addView(addImageView(R.drawable.west1));
		//flipper.addView(addImageView(R.drawable.west2));
		//flipper.addView(addImageView(R.drawable.west3));
		//flipper.addView(addImageView(R.drawable.west4));
		//animations[0]=AnimationUtils.loadAnimation(this,R.anim.left_in);
		//animations[1]=AnimationUtils.loadAnimation(this,R.anim.left_out);
		//animations[2]=AnimationUtils.loadAnimation(this,R.anim.right_in);
		//animations[3]=AnimationUtils.loadAnimation(this,R.anim.right_out);
		//创建手势检测器
		//detector=new GestureDetector(this,this);
	}
	//添加imageview的方法
	/*private View addImageView(int resId){
		ImageView imageView=new ImageView(this);
		imageView.setImageResource(resId);
		imageView.setClickable(true);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		return imageView;
	}*/
	
	/*@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}*/
	//@Override
	/*public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(e1.getX()-e2.getX()>FLIP_DISTANCE){
			//设置切换的动画效果
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
	}*/
	//@Override
	/*public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}*/
	/*@Override
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
	}*/
	@Override
	protected void onResume() {
		super.onResume();
		photoSorter.loadImages(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		photoSorter.unloadImages();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			photoSorter.trackballClicked();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
