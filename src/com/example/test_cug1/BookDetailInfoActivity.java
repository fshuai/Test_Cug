package com.example.test_cug1;

import model.BookInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BookDetailInfoActivity extends Activity {
	//显示用户所选图书详细信息的界面
	private TextView name;
	private TextView writer;
	private TextView publish;
	private TextView place;
	private TextView id;
	private TextView condition;
	
	private BookInfo info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookdetail);
		Intent intent=getIntent();
		info=(BookInfo)intent.getSerializableExtra("selectedinfo");
		name=(TextView)findViewById(R.id.detail_name);
		writer=(TextView)findViewById(R.id.detail_writer);
		publish=(TextView)findViewById(R.id.detail_publish);
		place=(TextView)findViewById(R.id.detail_place);
		id=(TextView)findViewById(R.id.detail_id);
		condition=(TextView)findViewById(R.id.detail_condition);
		init();
	}
    
	private void init(){
		//更新界面的操作
		name.setText(info.getBookName());
		writer.setText(info.getWriter());
		publish.setText(info.getPublish());
		place.setText(info.getPlace());
		id.setText(info.getId());
		condition.setText(info.getCondition());
	}
}
