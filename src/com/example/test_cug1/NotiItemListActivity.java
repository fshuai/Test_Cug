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
	//如何检索不同学院的信息??
	//选择不同的条目后可以根据相关的id获取条目代表的学院，依次遍历所有的info，若符合，则加入到list<>selectedinfos中然后显示出来.
	//0代表机电学院，1代表计算机学院，2代表地学院,3代表信工学院，4代表经管学院
	//先加入一个spinner
	//成功完成spinner检索学院的算法，接下来要获取所选信息的id:定义一个全局变量List<String>，当点击某个spinner后，将检索到的信息
	//依次放入该List<String>中，然后可以获取该id
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
		//接收从mainactivity传递来的url信息
		Intent intent=getIntent();
		URL=intent.getStringExtra("server1");
		//list=new ArrayList<HashMap<String,String>>();
		handler=new myHandler();
		errhandler=new ErrorHandler();
		selectid=new ArrayList<String>();
		listView.setOnItemClickListener(new Listlistener());
		//给spinner添加选择监听
	    //spinner.setOnItemSelectedListener(new SpinnerListener());
		//System.out.println("URL--->"+URL);
		init();
	}
	
	//定义spinner的监听器
	class SpinnerListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("Spinner-->"+arg2);
			//0:请选择学院
			//1:所有通知
			//2:机电学院 3:计算机学院
			//4:地学院      5:信工学院      6：经管学院  7：工程学院
			//8:资源学院  9：环境学院    10:外国语学院  11：珠宝学院
			//12:公共管理学院  13:艺术传媒学院  14：李四光学院
			switch(arg2){
			//选择了机电学院
			//什么都没选择，应该显示为空白
			  case 0:selectedInfos("nothingselected");
			         setTitle("详细信息");
				     break;
			  case 1:selectall();
			         setTitle("全部通知");
			         break;
			  case 2:selectedInfos("electric");
			         setTitle("机电学院通知");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 3:selectedInfos("cs");
			         setTitle("计算机学院通知");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 4:selectedInfos("geology");
			         setTitle("地学院通知");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 5:selectedInfos("infomationproject");
			         setTitle("信工学院通知");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 6:selectedInfos("economymanage");
			         setTitle("经管学院通知");
			         //System.out.println("id--->"+selectid.get(arg2).toString());
				     break;
			  case 7:selectedInfos("project");
			         setTitle("工程学院通知");
			         break;
			  case 8:selectedInfos("resource");
			         setTitle("资源学院通知");
			         break;
			  case 9:selectedInfos("environment");
			         setTitle("环境学院通知");
			         break;
			  case 10:selectedInfos("english");
			         setTitle("外国语学院通知");
			         break;
			  case 11:selectedInfos("diamond");
			         setTitle("珠宝学院通知");
			         break;
			  case 12:selectedInfos("publicmanagement");
			         setTitle("公共管理学院通知");
			         break;
			  case 13:selectedInfos("art");
			         setTitle("艺术与传媒学院通知");
			         break;
			  case 14:selectedInfos("lisiguang");
			         setTitle("李四光学院通知");
			  default:break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	}
	//检索学员名字，返回满足条件List<NotificationInfo>
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
		//选择显示全部通知时执行
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
		//在此添加分页显示的效果
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
			intent.putExtra("newsid", selectid.get(arg2).toString());//取出id
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
			//若notixml为空，则启动线程跳转到ConnectionerrActivity
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
