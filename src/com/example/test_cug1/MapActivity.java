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
    //��ҳ��ʾѧУλ�ã�ƽ��ͼ�ͳ˳�·��
	//����ѧУλ�ã�ƽ��ͼ֧����������
	//��Fragment���Զ���imageviewʵ��
	private ViewPager viewPager;
	private MapFragment1 fragment1;
	private MapFragment2 fragment2;
	private MapFragment3 fragment3;
	//ҳ���б�
	private ArrayList<Fragment> fragmentList;
	//�����б�
	ArrayList<String> titleList= new ArrayList<String>();
	//ͨ��pagerTabStrip�������ñ��������
	private PagerTabStrip pagerTabStrip;
	private PagerTitleStrip pagerTitleStrip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("У԰����");
		setContentView(R.layout.mapmain);
		viewPager=(ViewPager)findViewById(R.id.map_view_pager);
		pagerTabStrip=(PagerTabStrip)findViewById(R.id.map_pager_tab);
		
		//�����»��ߵ���ɫ
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_dark)); 
		//���ñ�������ɫ
		pagerTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
		fragment1=new MapFragment1();
		fragment2=new MapFragment2();
		fragment3=new MapFragment3();
		
		fragmentList=new ArrayList<Fragment>();
		fragmentList.add(fragment1);
		fragmentList.add(fragment2);
		fragmentList.add(fragment3);
		
		titleList.add("ѧУλ��");
		titleList.add("ƽ��ͼ");
		titleList.add("�˳�·��");
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
