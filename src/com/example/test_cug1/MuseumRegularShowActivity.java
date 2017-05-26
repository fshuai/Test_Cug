package com.example.test_cug1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MuseumRegularShowActivity extends Activity{
    private ListView listView;
    private Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regularshow);
		setTitle("����չ��");
		listView=(ListView)findViewById(R.id.regularshow_list);
		ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map1=new HashMap<String,Object>();
		map1.put("itemimage", R.drawable.regularshow1);
		listItem.add(map1);
		HashMap<String,Object> map2=new HashMap<String,Object>();
		map2.put("itemimage", R.drawable.regularshow2);
		listItem.add(map2);
		HashMap<String,Object> map3=new HashMap<String,Object>();
		map3.put("itemimage", R.drawable.regularshow3);
		listItem.add(map3);
		HashMap<String,Object> map4=new HashMap<String,Object>();
		map4.put("itemimage", R.drawable.regularshow4);
		listItem.add(map4);
		SimpleAdapter listItemAdapter=new SimpleAdapter(this, listItem, R.layout.regularlist,
				                      new String[]{"itemimage"}, new int[]{R.id.regular_show_image});
		listView.setAdapter(listItemAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				    //�����Զ���toast
				    case 0:ToastShow("�������չ��",
				    		"�Ӷ��ص����ǡ������������ɡ�����Ȧ�������ȵĴ�صȷ��淴ӳ�˵���46����Ĳ�ɣ��ʷ��",
				    		R.drawable.toast1);
				    	   break;
				    case 1:ToastShow("������Դ��",
				    		"��������Դ��ʼ���Ե�����ʷ�е��������Ϊ���߳��У�չ�ֵ�������38����Ľ������̣�ͻ���ش��¼���",
				             R.drawable.toast2);
				    	   break;
				    case 2:ToastShow("�鱦��ʯչ��",
				    		"�����ǽ��������ʵı���ʯ���硢�����Լ�Ȥ����չʾ�˾������׵ı�ʯ������ͷ׵���ʯ��",
				    		R.drawable.toast3);
				    	   break;
				    case 3:ToastShow("�ź���������������ר��չ",
				    		"��260������Ĺ����ﻯʯ�걾��Ϊ��ҿ��������缶��ʯ�����������ɴ",
				    		R.drawable.toast4);
				    	   break;
				}
			}
		});
	}
	
	private void ToastShow(String title,String content,int resId){
		timer=new Timer();
		LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.definedtoast,
 		               (ViewGroup)findViewById(R.id.toast_layout));
        TextView toast_title=(TextView)view.findViewById(R.id.toast_title);
        toast_title.setText(title);
        TextView toast_content=(TextView)view.findViewById(R.id.toast_text);
        toast_content.setText(content);
        ImageView imageView=(ImageView)view.findViewById(R.id.toast_image);
        imageView.setImageResource(resId);
        Toast toast=new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        RemindTask r=new RemindTask(toast);
        timer.schedule(r, 0);
	}
	
	class RemindTask extends TimerTask{
		private Toast toast;
		public RemindTask(Toast toast){
			this.toast=toast;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long firstTime=System.currentTimeMillis();
			while(System.currentTimeMillis()-firstTime<=4000){
				toast.show();
			}
			timer.cancel();
		}
	}
} 
