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
	//��ʾ���ڵ�textView
	private TextView timeView;
	//������Activityʱ���ѷ�������ַ��������Activity��
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
		//����listview
		ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();

			HashMap<String,Object> map1=new HashMap<String,Object>();
			map1.put("ItemImage", R.drawable.news1);
			map1.put("ItemTitle", "[ѧУ����] �����ȡ����У԰��Ϣ");
			listItem.add(map1);
			HashMap<String,Object> map2=new HashMap<String,Object>();
			map2.put("ItemImage", R.drawable.noti);
			map2.put("ItemTitle", "[Ժϵ֪ͨ] �����ȡ��ѧԺ����֪ͨ");
			listItem.add(map2);
			HashMap<String,Object> map3=new HashMap<String,Object>();
			map3.put("ItemImage", R.drawable.library);
			map3.put("ItemTitle", "[ͼ����Ϣ��ѯ] ʵʱ�˽�ͼ��ݽ�����Ϣ");
			listItem.add(map3);
			HashMap<String,Object> map4=new HashMap<String,Object>();
			map4.put("ItemImage", R.drawable.lecture);
			map4.put("ItemTitle", "[������Ϣ] �����ȡ����̳���½�����Ϣ");
			listItem.add(map4);
			HashMap<String,Object> map5=new HashMap<String,Object>();
			map5.put("ItemImage", R.drawable.museum);
			map5.put("ItemTitle", "[�ݷ����] ��ʱ���²����չ����Ϣ");
			listItem.add(map5);
			HashMap<String,Object> map6=new HashMap<String,Object>();
			map6.put("ItemImage", R.drawable.map);
			map6.put("ItemTitle", "[У԰����] �˽�ѧУλ�á�ƽ��ͼ���˳�·��");
			listItem.add(map6);
			//���ѧУ���κͲ������Ϣ
			//[У԰����] �˽�ѧУλ�á�ƽ��ͼ���˳�·��
			
		SimpleAdapter listItemAdapter=new SimpleAdapter(this,listItem,R.layout.firstlist,
				new String[]{"ItemImage","ItemTitle"},new int[]{R.id.newsImage1,R.id.newText1});
		listView.setAdapter(listItemAdapter);
		//Ϊlistview��ӵ���¼�
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
		//��ʾʱ��ĺ���
		t=new Time();
		t.setToNow();
		int month=t.month+1;
		String show=t.year+"."+month+"."+t.monthDay+" "+"����"+week(t.weekDay);
		timeView.setText(show);
	}
	
	private String week(int i){
		switch(i){
		case 1:return "һ";
		case 2:return "��";
		case 3:return "��";
		case 4:return "��";
		case 5:return "��";
		case 6:return "��";
		case 0:return "��";
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
		menu.add(0, ABOUT,1, "����");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==ABOUT){
		    Toast toast=Toast.makeText(MainActivity.this, 
		    		"��˧˧��1051865439@qq.com",Toast.LENGTH_LONG);
		    toast.show();
		}
		return true;
	}
	
}
