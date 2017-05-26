package com.example.test_cug1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment3 extends Fragment{
	
	private View myView;
	private ImageView image3;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		myView = inflater.inflate
				(R.layout.fragment3, (ViewGroup)getActivity().findViewById(R.id.viewPager), false);
		image3=(ImageView)myView.findViewById(R.id.fimage3);
		System.out.println("第三个Fragment");
		image3.setOnClickListener(new imageListener());
	}
	
	class imageListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("image3被点击");
			Intent intent=new Intent();
			intent.setClass(getActivity(), EastActivity.class);
			startActivity(intent);
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup p = (ViewGroup) myView.getParent();
		if(p!=null){
			p.removeAllViewsInLayout();
		}
		return myView;
	}
}
