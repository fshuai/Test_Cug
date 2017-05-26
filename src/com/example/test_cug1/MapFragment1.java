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
   
   private int window_width, window_height;// �ؼ����
   private DragImageView dragImageView;// �Զ���ؼ�
   private int state_height;// ״̬���ĸ߶�

   private ViewTreeObserver viewTreeObserver;
   @Override
   public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	LayoutInflater inflater = getActivity().getLayoutInflater();
	myMainView=inflater.inflate(R.layout.mapfragment1, 
			(ViewGroup)getActivity().findViewById(R.id.map_view_pager), false);
	/** ��ȡ��Ҋ����߶� **/
	WindowManager manager = getActivity().getWindowManager();
	window_width = manager.getDefaultDisplay().getWidth();
	window_height = manager.getDefaultDisplay().getHeight();

	dragImageView = (DragImageView) myMainView.findViewById(R.id.school_location);
	Bitmap bmp = BitmapUtil.ReadBitmapById(getActivity(), R.drawable.location,
			window_width, window_height);
	// ����ͼƬ
	dragImageView.setImageBitmap(bmp);
	dragImageView.setmActivity(getActivity());//ע��Activity.
	/** ����״̬���߶� **/
	viewTreeObserver = dragImageView.getViewTreeObserver();
	viewTreeObserver
			.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					if (state_height == 0) {
						// ��ȡ״�����߶�
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
	   //getActivity().setTitle("ѧУλ��");
	   if(p!=null){
		   p.removeAllViewsInLayout();
	   }
	   return myMainView;
   }
   
}
