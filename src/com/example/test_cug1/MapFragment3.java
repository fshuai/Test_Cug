package com.example.test_cug1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment3 extends Fragment{
	private View myMainView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		myMainView=inflater.inflate(R.layout.mapfragment3, 
				(ViewGroup)getActivity().findViewById(R.id.map_view_pager), false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup p = (ViewGroup)myMainView.getParent();
		   if(p!=null){
			   p.removeAllViewsInLayout();
		   }
		   return myMainView;
	}
}
