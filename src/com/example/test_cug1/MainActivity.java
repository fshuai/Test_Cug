package com.example.test_cug1;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    
	private ViewPager viewPager;
	private Fragment1 fragment1;
	private Fragment2 fragment2;
	private Fragment3 fragment3;
	private ArrayList<Fragment> fragmentList;
	private static final int ABOUT=1;
	private ListView listView;
	private Time t;
	//显示日期的textView
	private TextView timeView;
	//打开其他Activity时，把服务器地址传到其他Activity中
	private static final String URL="http://192.168.191.1:8080/cug/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		listView=(ListView)findViewById(R.id.List);
		timeView=(TextView)findViewById(R.id.index_time);
		
		fragment1=new Fragment1();
		fragment2=new Fragment2();
		fragment3=new Fragment3();
		
		fragmentList=new ArrayList<Fragment>();
		fragmentList.add(fragment1);
		fragmentList.add(fragment2);
		fragmentList.add(fragment3);
		viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		//设置listview
		ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();

			HashMap<String,Object> map1=new HashMap<String,Object>();
			map1.put("ItemImage", R.drawable.news1);
			map1.put("ItemTitle", "[学校新闻] 点击获取最新校园信息");
			listItem.add(map1);
			HashMap<String,Object> map2=new HashMap<String,Object>();
			map2.put("ItemImage", R.drawable.noti);
			map2.put("ItemTitle", "[院系通知] 点击获取各学院最新通知");
			listItem.add(map2);
			HashMap<String,Object> map3=new HashMap<String,Object>();
			map3.put("ItemImage", R.drawable.library);
			map3.put("ItemTitle", "[图书信息查询] 实时了解图书馆借阅信息");
			listItem.add(map3);
			HashMap<String,Object> map4=new HashMap<String,Object>();
			map4.put("ItemImage", R.drawable.lecture);
			map4.put("ItemTitle", "[讲座信息] 点击获取各讲坛最新讲座信息");
			listItem.add(map4);
			HashMap<String,Object> map5=new HashMap<String,Object>();
			map5.put("ItemImage", R.drawable.museum);
			map5.put("ItemTitle", "[逸夫博物馆] 及时更新博物馆展览信息");
			listItem.add(map5);
			HashMap<String,Object> map6=new HashMap<String,Object>();
			map6.put("ItemImage", R.drawable.map);
			map6.put("ItemTitle", "[校园导游] 了解学校位置、平面图及乘车路线");
			listItem.add(map6);
			//添加学校导游和博物馆信息
			//[校园导游] 了解学校位置、平面图及乘车路线
			
		SimpleAdapter listItemAdapter=new SimpleAdapter(this,listItem,R.layout.firstlist,
				new String[]{"ItemImage","ItemTitle"},new int[]{R.id.newsImage1,R.id.newText1});
		listView.setAdapter(listItemAdapter);
		//为listview添加点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//System.out.println("position-->"+arg2);
				switch(arg2){
				  case 0:Intent intent0 =new Intent();
				         intent0.setClass(MainActivity.this, NewsItemActivity.class);
				         intent0.putExtra("server0", URL);
				         startActivity(intent0);
				         break;
				  case 1:Intent intent1=new Intent();
				         intent1.setClass(MainActivity.this, NotiItemListActivity.class);
				         intent1.putExtra("server1", URL);
				         startActivity(intent1);
					     break;
				  case 2:Intent intent2=new Intent();
				         intent2.setClass(MainActivity.this, LibraryActivity.class);
				         intent2.putExtra("server2", URL);
				         startActivity(intent2);
				         break;
				  case 3:Intent intent3=new Intent();
				         intent3.setClass(MainActivity.this, LectureActivity.class);
				         intent3.putExtra("server3", URL);
				         startActivity(intent3);
				         break;
				  case 4:Intent intent4=new Intent();
			             intent4.setClass(MainActivity.this, MuseumActivity.class);
			             intent4.putExtra("server5", URL);
			             startActivity(intent4);
			             break;
				  case 5:Intent intent5=new Intent();
				         intent5.setClass(MainActivity.this, MapActivity.class);
				         startActivity(intent5);
				         break;
				  
				         
				  default:break;
				}
			}
		});
		timeShow();
	}
	
	private void timeShow(){
		//显示时间的函数
		t=new Time();
		t.setToNow();
		int month=t.month+1;
		String show=t.year+"."+month+"."+t.monthDay+" "+"星期"+week(t.weekDay);
		timeView.setText(show);
	}
	
	private String week(int i){
		switch(i){
		case 1:return "一";
		case 2:return "二";
		case 3:return "三";
		case 4:return "四";
		case 5:return "五";
		case 6:return "六";
		case 0:return "日";
		default:return "error";
		}
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
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, ABOUT,1, "关于");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==ABOUT){
		    Toast toast=Toast.makeText(MainActivity.this, 
		    		"范帅帅：1051865439@qq.com",Toast.LENGTH_LONG);
		    toast.show();
		}
		return true;
	}
	
}
