package com.example.test_cug1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class EastActivity extends Activity{
    
	private int[] imageIds=new int[]{
			R.drawable.east1,R.drawable.east2,R.drawable.east3,
			R.drawable.east4,R.drawable.east5,R.drawable.east6,R.drawable.east7};
	@SuppressWarnings("deprecation")
	private Gallery gallery;
	private ImageView imageView;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.east);
		setTitle("东区风光");
		gallery=(Gallery)findViewById(R.id.east_gallery);
		imageView=(ImageView)findViewById(R.id.east_image);
		BaseAdapter adapter=new BaseAdapter(){

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return imageIds.length;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ImageView imageView=new ImageView(EastActivity.this);
				imageView.setImageResource(imageIds[position]);
				imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				imageView.setLayoutParams(new Gallery.LayoutParams(70, 100));
				//TypeArray typeArray = obtainStyledAttributes()
				return imageView;
			}
			
		};
		gallery.setAdapter(adapter);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				imageView.setImageResource(imageIds[arg2]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

}
