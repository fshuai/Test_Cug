package com.example.test_cug1;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import xml.NewsInfoContentHandler;
import xml.NotifInfoContentHandler;

import model.NotificationInfo;
import download.HttpDownloader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class NotiItemListActivity extends Activity{
	private String URL;
	private Handler handler;
	private String notixml;
	private NotificationInfo info;
	private List<NotificationInfo> infos;
	//private ArrayList<HashMap<String,String>> list;
	private ListView listView;
	private Spinner spinner;
	private ProgressBar progressBar;
	private Handler errhandler;
	//��μ�����ͬѧԺ����Ϣ??
	//ѡ��ͬ����Ŀ����Ը�����ص�id��ȡ��Ŀ�����ѧԺ�����α������е�info�������ϣ�����뵽list<>selectedinfos��Ȼ����ʾ����.
	//0�������ѧԺ��1��������ѧԺ��2�����ѧԺ,3�����Ź�ѧԺ��4������ѧԺ
	//�ȼ���һ��spinner
	//�ɹ����spinner����ѧԺ���㷨��������Ҫ��ȡ��ѡ��Ϣ��id:����һ��ȫ�ֱ���List<String>�������ĳ��spinner�󣬽�����������Ϣ
	//���η����List<String>�У�Ȼ����Ի�ȡ��id
	private List<String> selectid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noti_item);
		listView=(ListView)findViewById(R.id.noti_item_list);
		spinner=(Spinner)findViewById(R.id.noti_spinner);
		progressBar=(ProgressBar)findViewById(R.id.noti_item_progressBar);
		View.inflate(this, R.layout.footer, null);
		//���մ�mainactivity��������url��Ϣ
		Intent intent=getIntent();
		URL=intent.getStringExtra("server1");
		//list=new ArrayList<HashMap<String,String>>();
		handler=new myHandler();
		errhandler=new ErrorHandler();
		selectid=new ArrayList<String>();
		listView.setOnItemClickListener(new Listlistener());
		//��spinner���ѡ�����
	    //spinner.setOnItemSelectedListener(new SpinnerListener());
		//System.out.println("URL--->"+URL);
		init();
	}
	
	//����spinner�ļ�����
	class SpinnerListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("Spinner-->"+arg2);
			//0:��ѡ��ѧԺ
			//1:����֪ͨ
			//2:����ѧԺ 3:�����ѧԺ
			//4:��ѧԺ      5:�Ź�ѧԺ      6������ѧԺ  7������ѧԺ
			//8:��ԴѧԺ  9������ѧԺ    10:�����ѧԺ  11���鱦ѧԺ
			//12:��������ѧԺ  13:������ýѧԺ  14�����Ĺ�ѧԺ
			switch(arg2){
			//ѡ���˻���ѧԺ
			//ʲô��ûѡ��Ӧ����ʾΪ�հ�
			  case 0:selectedInfos("nothingselected");
			         setTitle("��ϸ��Ϣ");
				     break;
			  case 1:selectall();
			         setTitle("ȫ��֪ͨ");
			         break;
			  case 2:selectedInfos("electric");
			         setTitle("����ѧԺ֪ͨ");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 3:selectedInfos("cs");
			         setTitle("�����ѧԺ֪ͨ");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 4:selectedInfos("geology");
			         setTitle("��ѧԺ֪ͨ");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 5:selectedInfos("infomationproject");
			         setTitle("�Ź�ѧԺ֪ͨ");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 6:selectedInfos("economymanage");
			         setTitle("����ѧԺ֪ͨ");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 7:selectedInfos("project");
			         setTitle("����ѧԺ֪ͨ");
			         break;
			  case 8:selectedInfos("resource");
			         setTitle("��ԴѧԺ֪ͨ");
			         break;
			  case 9:selectedInfos("environment");
			         setTitle("����ѧԺ֪ͨ");
			         break;
			  case 10:selectedInfos("english");
			         setTitle("�����ѧԺ֪ͨ");
			         break;
			  case 11:selectedInfos("diamond");
			         setTitle("�鱦ѧԺ֪ͨ");
			         break;
			  case 12:selectedInfos("publicmanagement");
			         setTitle("��������ѧԺ֪ͨ");
			         break;
			  case 13:selectedInfos("art");
			         setTitle("�����봫ýѧԺ֪ͨ");
			         break;
			  case 14:selectedInfos("lisiguang");
			         setTitle("���Ĺ�ѧԺ֪ͨ");
			  default:break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	}
	//����ѧԱ���֣�������������List<NotificationInfo>
    //List<NotificationInfo> result = null;
	private void selectedInfos(String col){
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		selectid.clear();
		for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
			NotificationInfo type = (NotificationInfo) iterator.next();
			if(type.getCollege().equals(col)){
				//result.add(type);
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("title", type.getTitle());
				map.put("time", type.getTime());
				list.add(map);
				selectid.add(type.getId());
			}
			SimpleAdapter adapter=new SimpleAdapter(NotiItemListActivity.this,
					list,R.layout.notilistitem,new String[]{"title","time"},
			         new int[]{R.id.noti_title,R.id.noti_time});
			listView.setAdapter(adapter);
		
		}
	}
	
	private void selectall(){
		//ѡ����ʾȫ��֪ͨʱִ��
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		selectid.clear();
		for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
			NotificationInfo type = (NotificationInfo) iterator.next();
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("title", type.getTitle());
			map.put("time", type.getTime());
			list.add(map);
			selectid.add(type.getId());
		}
		//�ڴ���ӷ�ҳ��ʾ��Ч��
		SimpleAdapter adapter=new SimpleAdapter (NotiItemListActivity.this,
				list,R.layout.notilistitem,new String[]{"title","time"},
				new int[]{R.id.noti_title,R.id.noti_time});
		listView.setAdapter(adapter);
	}
	
	class Listlistener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent=new Intent();
			intent.putExtra("servern", URL);
			intent.putExtra("newsid", selectid.get(arg2).toString());//ȡ��id
			intent.setClass(NotiItemListActivity.this, News_WebView.class);
			startActivity(intent);
		}
		
	}
	
	private void init(){
		DownThread t=new DownThread();
		t.start();
	}
	
	class myHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			notixml=(String) msg.obj;
			System.out.println("notixml--->"+notixml);
			infos=parse(notixml);
			spinner.setOnItemSelectedListener(new SpinnerListener());
			//��notixmlΪ�գ��������߳���ת��ConnectionerrActivity
			if(notixml.equals("")){
				ConnectionThread t=new ConnectionThread();
				t.start();
			}
			else{
				progressBar.setVisibility(View.GONE);
			}
			/*for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				NotificationInfo info = (NotificationInfo) iterator.next();
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("title", info.getTitle());
				map.put("time", info.getTime());
				list.add(map);
			}
			SimpleAdapter adapter=new SimpleAdapter(NotiItemListActivity.this,
					list,R.layout.notilistitem,new String[]{"title","time"},
			         new int[]{R.id.noti_title,R.id.noti_time});
			listView.setAdapter(adapter);*/
		}
		
	}
	
	class ConnectionThread extends Thread{
        private String err="error";
		@Override
		public void run() {
			try{
				Thread.sleep(2000);
				Message msg=errhandler.obtainMessage();
				msg.obj=err;
				errhandler.sendMessage(msg);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	class ErrorHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			String name=(String) msg.obj;
			if(name.equals("error")){
				progressBar.setVisibility(View.GONE);
				Intent intent=new Intent();
				intent.setClass(NotiItemListActivity.this, ConnectionErrorActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
	}
	
	class DownThread extends Thread{
        private String xml;
		@Override
		public void run() {
			try{
			 xml=downXml("notificationlist.xml");
			 Message msg=handler.obtainMessage();
			 msg.obj=xml;
			 handler.sendMessage(msg);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		private String downXml(String url){
			HttpDownloader httpDownloader=new HttpDownloader();
			String result=httpDownloader.download(URL+url);
			return result;
		}
	}
	
	private List<NotificationInfo> parse(String str){
		SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
		List<NotificationInfo> info1=new ArrayList<NotificationInfo>();
		try{
			XMLReader xmlReader=saxParserFactory.newSAXParser().getXMLReader();
			NotifInfoContentHandler notifInfoHandler=new NotifInfoContentHandler(info1);
			xmlReader.setContentHandler(notifInfoHandler);
			xmlReader.parse(new InputSource(new StringReader(str)));
			/*for (Iterator iterator = info1.iterator(); iterator.hasNext();) {
				NotificationInfo notificationInfo = (NotificationInfo) iterator
						.next();
				System.out.println(notificationInfo);
				
			}*/
		}
		catch(Exception e){
	            e.printStackTrace();
		}
		return info1;
	}

}
