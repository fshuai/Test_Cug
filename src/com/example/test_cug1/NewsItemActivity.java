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

import model.NewsInfo;

import download.HttpDownloader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class NewsItemActivity extends Activity{
    private ListView newslist;
    private ProgressBar progressBar;
    private Handler handler;
    private Handler errhandler;
    private String newsxml;
    private List<NewsInfo> infos;
    private ArrayList<HashMap<String,String>> list;
    //在mainActivity中定义一个常量，每次跳转到其他Activity时，都传递改常量，这样可以方便修改
    private String URL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsitem);
		newslist=(ListView)findViewById(R.id.newsitemshow);
		progressBar=(ProgressBar)findViewById(R.id.newsitemprogressBar);
		handler=new myHandler();
		errhandler=new ErrHandler();
		list=new ArrayList<HashMap<String,String>>();
		Intent intent=getIntent();
		URL=intent.getStringExtra("server0");
		init();
		newslist.setOnItemClickListener(new itemclickListener());
	}
	
	//给点击条目添加监听
	class itemclickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("id-->"+infos.get(arg2).getId());
			Intent intent=new Intent();
			intent.putExtra("servern", URL);
			intent.putExtra("newsid", infos.get(arg2).getId());
			intent.setClass(NewsItemActivity.this, News_WebView.class);
			startActivity(intent);
		}
	}
	
	private void init(){
		//启动线程
		Thread t=new downThread();
		t.start();
	}
	
	//自定义一个handler，处理下载的xml信息
	class myHandler extends Handler{
      //在handlemessage中设置list,显示列表信息
		@Override
		public void handleMessage(Message msg) {
			newsxml=(String)msg.obj;
			if(newsxml.equals("")){
				connectionThread t=new connectionThread();
				t.start();
				}
			else{
			progressBar.setVisibility(View.GONE);
			infos=parse(newsxml);
			//先把list初始化
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				NewsInfo type = (NewsInfo) iterator.next();
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("title", type.getTitle());
				map.put("time", type.getTime());
				list.add(map);
			}
			SimpleAdapter adapter=new SimpleAdapter(NewsItemActivity.this,
					list, R.layout.newslistitem,
					new String[]{"title","time"},new int[]{R.id.news_title,R.id.news_time});
			newslist.setAdapter(adapter);
			}
			//如果newsxml为空，就新开一个线程，该线程休眠5s，然后隐藏进度条，并打开连接错误的Activity
		}
	}
	
	class ErrHandler extends Handler{
        private String rec;
		@Override
		public void handleMessage(Message msg) {
			rec=(String) msg.obj;
			if(rec.equals("error")){
			  progressBar.setVisibility(View.GONE);
			  Intent intent=new Intent();
			  intent.setClass(NewsItemActivity.this,ConnectionErrorActivity.class);
			  startActivity(intent);
			  finish();//测试是否管用
			}
		}
		
	}
	
	class connectionThread extends Thread{
        private String err="error";
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
				Message msg=errhandler.obtainMessage();
				msg.obj=err;
				errhandler.sendMessage(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//创建一个下载xml文件的线程
	class downThread extends Thread{
        private String xml;
		@Override
		public void run() {
			try{
				xml=downXML("newslist.xml");
				System.out.println("newsxml--->"+xml);
				Message msg=handler.obtainMessage();
				msg.obj=xml;
				handler.sendMessage(msg);
			}
			catch(Exception e){
				Toast toast=Toast.makeText(NewsItemActivity.this, "请检查网络连接", Toast.LENGTH_LONG);
				toast.show();
				progressBar.setVisibility(View.GONE);
				Intent intent=new Intent();
				intent.setClass(NewsItemActivity.this, ConnectionErrorActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
		private String downXML(String url){
			HttpDownloader httpDownloader=new HttpDownloader();
			String result=httpDownloader.download(URL+url);
			return result;
		}
		
	}
	
	private List<NewsInfo> parse(String str){
		SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
		List<NewsInfo> info1=new ArrayList<NewsInfo>();
		try{
			XMLReader xmlReader=saxParserFactory.newSAXParser().getXMLReader();
			NewsInfoContentHandler newsInfoContentHandler=new NewsInfoContentHandler(info1);
			xmlReader.setContentHandler(newsInfoContentHandler);
			xmlReader.parse(new InputSource(new StringReader(str)));
		}
		catch(Exception e){
	
		}
		return info1;
	}
}
