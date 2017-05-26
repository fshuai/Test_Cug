package com.example.test_cug1;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.BookInfo;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import xml.BookInfoContentHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import download.HttpDownloader;
   
public class LibraryActivity extends Activity{
	//数据库操作有问题，最后再做--->已解决
	//退出应用是要把表中的内容清空
    private AutoCompleteTextView autoCompleteTextView;
    private Button searchButton;
    private String libxml;
    private String URL;
    private List<String> list=null; //存储书名，与autocompletetextview联系起来
    private List<BookInfo> bookInfos=null; //存储每本书的相关信息
    private Handler handler;
    private boolean connection=true;//判断网络连接的变量
    private MyDatabaseHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library);
		autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autolib);
		searchButton=(Button)findViewById(R.id.libButton);
		Intent intent=getIntent();
		URL=intent.getStringExtra("server2");
		//绑定监听
		searchButton.setOnClickListener(new ButtonListener());
		dbHelper=new MyDatabaseHelper(this,"test_lib",null, 1);
		dbHelper.getReadableDatabase();
		list=new ArrayList<String>();
		handler=new myHandler();
		updateInfo();
	}
	
	//把数据项的name和id传入，然后根据id可以找到是第几个数据，在传入下一个Activity

	//定义一个按钮监听，获取editview的内容，并启动显示结果的Activity
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String item=autoCompleteTextView.getText().toString();
			dbHelper.getWritableDatabase();
			//edittext未输入内容是如何检测？？
			if(connection==false){
				Intent intent=new Intent();
				intent.setClass(LibraryActivity.this, ConnectionErrorActivity.class);
				startActivity(intent);
				finish();
			}
			else if(item.equals("")){
				AlertDialog.Builder builder1=new AlertDialog.Builder(LibraryActivity.this);
				builder1.setTitle("Sorry!");
				builder1.setMessage("请输入关键字");
				builder1.setPositiveButton("确定",new AlertButtonListener());
				builder1.show();
			}
			else{
			Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
					"select * from book where name like ? or id like ?",
					new String[] { "%" + item + "%", "%" + item + "%" });
			ArrayList<HashMap<String, String>> result=new 
					                   ArrayList<HashMap<String,String>>();
			while(cursor.moveToNext()){
			System.out.println("item-->"+cursor.getString(0));
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("name", cursor.getString(0).toString());
			map.put("id", cursor.getString(1).toString());
			result.add(map);
			}
			if(result.isEmpty()){
				//搜索结果为空打开一个新的Activity提示用户未找到相应的搜索结果
				//System.out.println("未找到响应的搜索结果");
				AlertDialog.Builder builder=new AlertDialog.Builder(LibraryActivity.this);
				builder.setTitle("Sorry!");
				builder.setMessage("未找到相应信息");
				builder.setPositiveButton("确定",new AlertButtonListener());
				//setPositiveButton(builder);
				builder.show();
			}
			else{
			//data携带搜索结果的信息
			Bundle data=new Bundle();
			data.putSerializable("data", result);
			//让data1携带bookinfos信息
			Bundle data1=new Bundle();
			data1.putSerializable("data1", (Serializable) bookInfos);
			Intent intent=new Intent();
			intent.setClass(LibraryActivity.this, BookItemListActivity.class);
			intent.putExtras(data);
			intent.putExtras(data1);
			startActivity(intent);
			}
			}
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		//可以这样清除数据
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		db.delete("book", null, null);
		//System.out.println("执行了destroy方法");
	}
	
	//定义一个对话框按钮的监听器,可以在此定义相应的监听内容
	class AlertButtonListener implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			autoCompleteTextView.setText("");
		}
		
	}
	
	private void updateInfo(){
		DownloadThread t=new DownloadThread();
		t.start();
	}
	
	//自定义handler接收线程下载的xml文件
	class myHandler extends Handler{
	 @Override
	 public void handleMessage(Message msg) {
			libxml=(String)msg.obj;
			bookInfos=parse(libxml);
			updatelist(bookInfos);//时书名与autocompletetextview联系起来
			for (Iterator iterator = bookInfos.iterator(); iterator.hasNext();) {
				BookInfo bookInfo = (BookInfo) iterator.next();
				ContentValues values=new ContentValues();
				values.put("name", bookInfo.getBookName());
				values.put("id", bookInfo.getId());
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				db.insert("book", null, values);
			}
		}
	}
	
	//下载xml文件的线程
	//用一个handler接收线程发出的xml信息
	class DownloadThread extends Thread{
        private String xml;//注释掉此行代码看看效果
		@Override
		public void run() {
			try{
			xml=downloadXml("bookresources.xml");
			//System.out.println("libxml--->"+xml);
			Message msg=handler.obtainMessage();
			msg.obj=xml;
			handler.sendMessage(msg);
			}
			catch(Exception e){
				/*Toast toast=Toast.makeText(LibraryActivity.this, "请检查网络连接", Toast.LENGTH_LONG);
				toast.show();
				connection=false;*/
			}
		}
		
		private String downloadXml(String url){
			HttpDownloader httpDownloader=new HttpDownloader();
			String result=httpDownloader.download(URL+url);
			return result;
		}
	}
	
	    //解析xml函数
		private List<BookInfo> parse(String xmlStr){
			SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
			List<BookInfo> infos=new ArrayList<BookInfo>();
			try{
				XMLReader xmlReader=saxParserFactory.newSAXParser().getXMLReader();
				BookInfoContentHandler bookInfoContentHandler=
						            new BookInfoContentHandler(infos);
				xmlReader.setContentHandler(bookInfoContentHandler);
				xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			}
			catch(Exception e){
				Toast toast=Toast.makeText(LibraryActivity.this, "请检查网络连接", Toast.LENGTH_LONG);
				toast.show();
				connection=false;
			}
			return infos;
		}
		
		
		//将所有的书名放入list中，然后在与autocompletetextview连接
		private void updatelist(List<BookInfo> info){
			//list=new ArrayList<String>();
			for (Iterator iterator = info.iterator(); iterator.hasNext();) {
				BookInfo bookInfo = (BookInfo) iterator.next();
				list.add(bookInfo.getBookName());
			}
			ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>
			                                    (this,R.layout.lib_item,list);
			autoCompleteTextView.setAdapter(arrayAdapter);
		}
}
