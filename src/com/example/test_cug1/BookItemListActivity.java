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
	//��ʾ���������Activity
	//�����Activity�д���һ�����ݿ⣬��ʵ�����ݿ�Ĳ�ѯ����ʾ
	private List<BookInfo> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_item);
		listView=(ListView)findViewById(R.id.itemshow);
		Intent intent=getIntent();
		//��ȡintentЯ��������
		Bundle data=intent.getExtras();
		//��Bundle���ݰ���ȡ������,data������Ƿ��ϲ�ѯ�����������
		list=(List<HashMap<String,String>>)data.getSerializable("data");
		//��list��װ��simpleadapter
		SimpleAdapter adapter = new SimpleAdapter(this
				, list,
				R.layout.libitemline, new String[] { "name", "id" }
				, new int[] {R.id.book_name, R.id.book_id });
		listView.setAdapter(adapter);
		//���������ַ�ʽ�õ�ͼ����Ϣ
		infos=(List<BookInfo>) data.getSerializable("data1");
		listView.setOnItemClickListener(new listViewclickListener());
		//System.out.println("bookinfo--->"+infos.get(0).getBookName().toString());
		
	}
	
	class listViewclickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//��ȡ�������Ŀid����infos���ҳ���Ӧ����Ϣ�����ݸ���ʾ��ϸ��Ϣ��Activity��������һ��Activity����ʾ
			//��ΰ�cursor���Ӧ��bookinfo��ϵ������
			//���������ַ����õ��û�ѡ���ͼ�����ֻ�id
			//��һ��forѭ���������е�infos���ҵ���Ӧ����Ϣ���Ѹ���Ϣ���ݵ���һ����ʾ�����Activity��
			String name=list.get(arg2).get("id");
			BookInfo info1=null;//info1ΪҪ���ݵ�info
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				BookInfo info = (BookInfo) iterator.next();
				if(info.getId().equals(name)){
					info1=info;
					break;//����ѭ��
					}
			}
			//System.out.println("info1-->"+info1.getWriter());//���Գɹ�
			Intent intent=new Intent();
			intent.putExtra("selectedinfo", info1);
			intent.setClass(BookItemListActivity.this, BookDetailInfoActivity.class);
			startActivity(intent);
		}
		
	}
	
}
