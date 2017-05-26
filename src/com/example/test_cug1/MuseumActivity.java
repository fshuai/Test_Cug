package com.example.test_cug1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

public class MuseumActivity extends Activity{
	
	private Button visitButton;
	private Button productButton;
	private Spinner museumShow;
	private String URL;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.museumindex);
		setTitle("�ݷ����");
		Intent intent=getIntent();
		URL=intent.getStringExtra("server5");
		//System.out.println(URL);
		visitButton=(Button)findViewById(R.id.museum_guide);
		productButton=(Button)findViewById(R.id.museum_product);
		museumShow=(Spinner)findViewById(R.id.museum_show);
		webView=(WebView)findViewById(R.id.museum_web);
		visitButton.setOnClickListener(new ButtonListener());
		museumShow.setOnItemSelectedListener(new spinnerShowListener());
		productButton.setOnClickListener(new ButtonListener());
		webView.loadUrl(URL+"museum.html");
	}
	
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			//����һ���Ի���
			switch(v.getId()){
			case R.id.museum_guide:
			    String name=time1();
			    AlertDialog.Builder builder=new AlertDialog.Builder(MuseumActivity.this);
			    builder.setTitle("��ѯ���");
			    builder.setMessage(name);
			    builder.setPositiveButton("ȷ��",new AlertButtonListener());
			    builder.show();
			break;
			case R.id.museum_product:
				Intent intent=new Intent();
				intent.setClass(MuseumActivity.this, MuseumProductActivity.class);
				startActivity(intent);
				break;
			default:break;
			}
		}
		
	}
	
	class AlertButtonListener implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
	}
	
	private String time1(){
		//��ȡ��ǰʱ����ι�ʱ��Ƚϣ����رȽϽ��
		String result="ok";
		Time t=new Time();
		t.setToNow();
		int hour=t.hour;
		int minute=t.minute;
		System.out.println("hour-->"+hour+"."+minute);
		int week=t.weekDay;
		System.out.println("week-->"+week);
		if(week<=5 && week>=1){
			if((hour>=9 && hour<=12) || (hour>=15 && hour<=17)){
				result="����ʱ���ڣ����Բι�";
			}
			else if(hour==8 && minute>=30){
				result="����ʱ���ڣ����Բι�";
			}
			else {
				result="���ڿ���ʱ����";
			}
		}
		else{
		   if(hour>=9 && hour<=16){
			   result="����ʱ���ڣ����Բι�";
		   }
		   else {
			   result="���ڿ���ʱ����";
		   }
		}
		return result;
	}
	
	class spinnerShowListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch(arg2){
			   //1Ϊ����չ��
			   case 1:Intent intent1=new Intent();
			          intent1.setClass(MuseumActivity.this, MuseumRegularShowActivity.class);
			          startActivity(intent1);
				      break;
			   //2Ϊ����չ��
			   case 2:Intent intent2=new Intent();
			          intent2.setClass(MuseumActivity.this, News_WebView.class);
			          intent2.putExtra("servern", URL);
			          intent2.putExtra("newsid", "newshow");
			          startActivity(intent2);
				      break;
			   default:break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
		
	}
	
}
