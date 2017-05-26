package com.example.test_cug1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class News_WebView extends Activity{
    private WebView webView;
    private ProgressBar progressBar;
    private String URL;
    private String id;
    private String nid;//加载院系通知的地址
    private Handler handler;
    //如何判断是哪个Activity启动了该Activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_web);
		webView=(WebView)findViewById(R.id.news_web);
		progressBar=(ProgressBar)findViewById(R.id.webViewprogressBar);
		handler=new myHandler();
		Intent intent=getIntent();
		//在此加上一个判断哪个Activity启动该Activity的分支语句
		URL=intent.getStringExtra("servern");
		id=intent.getStringExtra("newsid");
		LoadThread t=new LoadThread();
		t.start();
		//init();
	}
	
	class myHandler extends Handler{
        private String rec;
		@Override
		public void handleMessage(Message msg) {
			rec=(String) msg.obj;
			if(rec.equals("ok")){
				init();
			}
		}
	}
	
	class LoadThread extends Thread{
        private String ok="ok";
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
				Message msg=handler.obtainMessage();
				msg.obj=ok;
				handler.sendMessage(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void init(){
		//加载网页
		webView.loadUrl(URL+id+".html");
		progressBar.setVisibility(View.GONE);
	}
    
}
