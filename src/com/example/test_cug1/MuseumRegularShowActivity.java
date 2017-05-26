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
		setTitle("常设展览");
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
				    //弹出自定义toast
				    case 0:ToastShow("地球奥秘展厅",
				    		"从独特的行星、地球的物质组成、生物圈、不安稳的大地等方面反映了地球46亿年的沧桑历史。",
				    		R.drawable.toast1);
				    	   break;
				    case 1:ToastShow("生命起源厅",
				    		"从生命起源开始，以地质历史中的生物进化为主线陈列，展现地球生命38亿年的进化历程，突出重大事件。",
				             R.drawable.toast2);
				    	   break;
				    case 2:ToastShow("珠宝玉石展厅",
				    		"向人们介绍奇光异彩的宝玉石世界、鉴赏以及趣话，展示了精美绝伦的宝石、五彩缤纷的玉石。",
				    		R.drawable.toast3);
				    	   break;
				    case 3:ToastShow("张和先生捐赠古生物专题展",
				    		"用260余件珍贵的古生物化石标本，为你揭开辽西世界级化石宝库的神秘面纱",
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
