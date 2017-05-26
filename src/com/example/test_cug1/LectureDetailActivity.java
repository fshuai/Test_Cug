package com.example.test_cug1;

import java.util.Iterator;
import java.util.List;

import model.LectureInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LectureDetailActivity extends Activity {
    private String URL;
    private String id;//idΪѡ???d
    private String masterid;//??????d
    private List<LectureInfo> infos;
    private TextView titleView;
    private TextView timeView;
    private TextView placeView;
    private TextView masterView;
    private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lecturedetail);
		titleView=(TextView)findViewById(R.id.lecture_detail_title);
		timeView=(TextView)findViewById(R.id.lecture_detail_time);
		placeView=(TextView)findViewById(R.id.lecture_detail_place);
		masterView=(TextView)findViewById(R.id.lecture_detail_master);
		button=(Button)findViewById(R.id.lecture_detail_button);
		//?ȷ
		Intent intent=getIntent();
		URL=intent.getStringExtra("server");
		id=intent.getStringExtra("id");
		Bundle data=intent.getExtras();
		infos=(List<LectureInfo>) data.getSerializable("data");
		init();
	}
	
	private void init(){
		LectureInfo result = null;
		for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
			LectureInfo type = (LectureInfo) iterator.next();
			if(type.getId().equals(id)){
				result=type;
				break;
			}
		}
		masterid=result.getMasterid();
		titleView.setText(result.getTitle());
		timeView.setText(result.getTime());
		placeView.setText(result.getPlace());
		masterView.setText(result.getMaster());
		button.setOnClickListener(new ButtonListener());
	}
	
	//??button??ӵ??????
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent=new Intent();
			intent.putExtra("servern", URL);
			intent.putExtra("newsid", masterid);
			intent.setClass(LectureDetailActivity.this, News_WebView.class);
			startActivity(intent);
		}
		
	}
}
