package com.example.test_cug1;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

public class MapActivity extends FragmentActivity{
    //分页显示学校位置，平面图和乘车路线
	//其中学校位置，平面图支持手势缩放
	//用Fragment和自定义imageview实现
	private ViewPager viewPager;
	private MapFragment1 fragment1;
	private MapFragment2 fragment2;
	private MapFragment3 fragment3;
	//页面列表
	private ArrayList<Fragment> fragmentList;
	//标题列表
	ArrayList<String> titleList= new ArrayList<String>();
	//通过pagerTabStrip可以设置标题的属性
	private PagerTabStrip pagerTabStrip;
	private PagerTitleStrip pagerTitleStrip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("校园导游");
		setContentView(R.layout.mapmain);
		viewPager=(ViewPager)findViewById(R.id.map_view_pager);
		pagerTabStrip=(PagerTabStrip)findViewById(R.id.map_pager_tab);
		
		//设置下划线的颜色
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_dark)); 
		//设置背景的颜色
		pagerTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
		fragment1=new MapFragment1();
		fragment2=new MapFragment2();
		fragment3=new MapFragment3();
		
		fragmentList=new ArrayList<Fragment>();
		fragmentList.add(fragment1);
		fragmentList.add(fragment2);
		fragmentList.add(fragment3);
		
		titleList.add("学校位置");
		titleList.add("平面图");
		titleList.add("乘车路线");
		viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
	}
	
	class MyViewPagerAdapter extends FragmentPagerAdapter{

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titleList.get(position);
		}
	}
}
