package com.example.test_cug1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import model.BookInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BookItemListActivity extends Activity{
	private ListView listView;
	private List<HashMap<String,String>> list;
	//显示搜索结果的Activity
	//在这个Activity中创建一个数据库，并实现数据库的查询和显示
	private List<BookInfo> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_item);
		listView=(ListView)findViewById(R.id.itemshow);
		Intent intent=getIntent();
		//获取intent携带的数据
		Bundle data=intent.getExtras();
		//从Bundle数据包中取出数据,data保存的是符合查询结果的数据项
		list=(List<HashMap<String,String>>)data.getSerializable("data");
		//将list封装成simpleadapter
		SimpleAdapter adapter = new SimpleAdapter(this
				, list,
				R.layout.libitemline, new String[] { "name", "id" }
				, new int[] {R.id.book_name, R.id.book_id });
		listView.setAdapter(adapter);
		//可以用这种方式得到图书信息
		infos=(List<BookInfo>) data.getSerializable("data1");
		listView.setOnItemClickListener(new listViewclickListener());
		//System.out.println("bookinfo--->"+infos.get(0).getBookName().toString());
		
	}
	
	class listViewclickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//获取点击的条目id并在infos中找出对应的信息，传递给显示详细信息的Activity，并在下一个Activity中显示
			//如何把cursor与对应的bookinfo联系起来？
			//可以用这种方法得到用户选择的图书名字或id
			//用一个for循环遍历所有的infos，找到对应的信息，把该信息传递到下一个显示结果的Activity中
			String name=list.get(arg2).get("id");
			BookInfo info1=null;//info1为要传递的info
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				BookInfo info = (BookInfo) iterator.next();
				if(info.getId().equals(name)){
					info1=info;
					break;//跳出循环
					}
			}
			//System.out.println("info1-->"+info1.getWriter());//测试成功
			Intent intent=new Intent();
			intent.putExtra("selectedinfo", info1);
			intent.setClass(BookItemListActivity.this, BookDetailInfoActivity.class);
			startActivity(intent);
		}
		
	}
	
}
