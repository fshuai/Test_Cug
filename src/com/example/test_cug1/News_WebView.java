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
    private String nid;//����Ժϵ֪ͨ�ĵ�ַ
    private Handler handler;
    //����ж����ĸ�Activity�����˸�Activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_web);
		webView=(WebView)findViewById(R.id.news_web);
		progressBar=(ProgressBar)findViewById(R.id.webViewprogressBar);
		handler=new myHandler();
		Intent intent=getIntent();
		//�ڴ˼���һ���ж��ĸ�Activity������Activity�ķ�֧���
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
		//������ҳ
		webView.loadUrl(URL+id+".html");
		progressBar.setVisibility(View.GONE);
	}
    
}
