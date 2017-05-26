package com.example.test_cug1;

import drag.BitmapUtil;
import drag.DragImageView;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;


public class MapFragment1 extends Fragment{
   private View myMainView;
   
   private int window_width, window_height;// 控件宽度
   private DragImageView dragImageView;// 自定义控件
   private int state_height;// 状态栏的高度

   private ViewTreeObserver viewTreeObserver;
   @Override
   public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	LayoutInflater inflater = getActivity().getLayoutInflater();
	myMainView=inflater.inflate(R.layout.mapfragment1, 
			(ViewGroup)getActivity().findViewById(R.id.map_view_pager), false);
	/** 获取可見区域高度 **/
	WindowManager manager = getActivity().getWindowManager();
	window_width = manager.getDefaultDisplay().getWidth();
	window_height = manager.getDefaultDisplay().getHeight();

	dragImageView = (DragImageView) myMainView.findViewById(R.id.school_location);
	Bitmap bmp = BitmapUtil.ReadBitmapById(getActivity(), R.drawable.location,
			window_width, window_height);
	// 设置图片
	dragImageView.setImageBitmap(bmp);
	dragImageView.setmActivity(getActivity());//注入Activity.
	/** 测量状态栏高度 **/
	viewTreeObserver = dragImageView.getViewTreeObserver();
	viewTreeObserver
			.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					if (state_height == 0) {
						// 获取状况栏高度
						Rect frame = new Rect();
						getActivity().getWindow().getDecorView()
								.getWindowVisibleDisplayFrame(frame);
						state_height = frame.top;
						dragImageView.setScreen_H(window_height-state_height);
						dragImageView.setScreen_W(window_width);
					}

				}
			});

  }
   
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	   ViewGroup p = (ViewGroup)myMainView.getParent();
	   //getActivity().setTitle("学校位置");
	   if(p!=null){
		   p.removeAllViewsInLayout();
	   }
	   return myMainView;
   }
   
}
