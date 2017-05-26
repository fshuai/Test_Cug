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
	//���ݿ���������⣬�������--->�ѽ��
	//�˳�Ӧ����Ҫ�ѱ��е��������
    private AutoCompleteTextView autoCompleteTextView;
    private Button searchButton;
    private String libxml;
    private String URL;
    private List<String> list=null; //�洢��������autocompletetextview��ϵ����
    private List<BookInfo> bookInfos=null; //�洢ÿ����������Ϣ
    private Handler handler;
    private boolean connection=true;//�ж��������ӵı���
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
		//�󶨼���
		searchButton.setOnClickListener(new ButtonListener());
		dbHelper=new MyDatabaseHelper(this,"test_lib",null, 1);
		dbHelper.getReadableDatabase();
		list=new ArrayList<String>();
		handler=new myHandler();
		updateInfo();
	}
	
	//���������name��id���룬Ȼ�����id�����ҵ��ǵڼ������ݣ��ڴ�����һ��Activity

	//����һ����ť��������ȡeditview�����ݣ���������ʾ�����Activity
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String item=autoCompleteTextView.getText().toString();
			dbHelper.getWritableDatabase();
			//edittextδ������������μ�⣿��
			if(connection==false){
				Intent intent=new Intent();
				intent.setClass(LibraryActivity.this, ConnectionErrorActivity.class);
				startActivity(intent);
				finish();
			}
			else if(item.equals("")){
				AlertDialog.Builder builder1=new AlertDialog.Builder(LibraryActivity.this);
				builder1.setTitle("Sorry!");
				builder1.setMessage("������ؼ���");
				builder1.setPositiveButton("ȷ��",new AlertButtonListener());
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
				//�������Ϊ�մ�һ���µ�Activity��ʾ�û�δ�ҵ���Ӧ���������
				//System.out.println("δ�ҵ���Ӧ���������");
				AlertDialog.Builder builder=new AlertDialog.Builder(LibraryActivity.this);
				builder.setTitle("Sorry!");
				builder.setMessage("δ�ҵ���Ӧ��Ϣ");
				builder.setPositiveButton("ȷ��",new AlertButtonListener());
				//setPositiveButton(builder);
				builder.show();
			}
			else{
			//dataЯ�������������Ϣ
			Bundle data=new Bundle();
			data.putSerializable("data", result);
			//��data1Я��bookinfos��Ϣ
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
		//���������������
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		db.delete("book", null, null);
		//System.out.println("ִ����destroy����");
	}
	
	//����һ���Ի���ť�ļ�����,�����ڴ˶�����Ӧ�ļ�������
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
	
	//�Զ���handler�����߳����ص�xml�ļ�
	class myHandler extends Handler{
	 @Override
	 public void handleMessage(Message msg) {
			libxml=(String)msg.obj;
			bookInfos=parse(libxml);
			updatelist(bookInfos);//ʱ������autocompletetextview��ϵ����
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
	
	//����xml�ļ����߳�
	//��һ��handler�����̷߳�����xml��Ϣ
	class DownloadThread extends Thread{
        private String xml;//ע�͵����д��뿴��Ч��
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
				/*Toast toast=Toast.makeText(LibraryActivity.this, "������������", Toast.LENGTH_LONG);
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
	
	    //����xml����
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
				Toast toast=Toast.makeText(LibraryActivity.this, "������������", Toast.LENGTH_LONG);
				toast.show();
				connection=false;
			}
			return infos;
		}
		
		
		//�����е���������list�У�Ȼ������autocompletetextview����
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
