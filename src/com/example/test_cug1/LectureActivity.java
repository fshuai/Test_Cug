package com.example.test_cug1;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import model.LectureInfo;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import xml.LectureInfoContentHandler;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SimpleExpandableListAdapter;
import download.HttpDownloader;

public class LectureActivity extends ExpandableListActivity {
	private ProgressBar progressBar;
    private String URL;
    private Handler handler;
    private Handler errhandler;
    private String lecturexml;
    private List<LectureInfo> infos;
    private List<LectureInfo> infos1;//�������Ŀ�ѧ����Ŀ����ӵ���һ��һ����Ŀ�Ķ�����Ŀ
    private List<LectureInfo> infos2;//������Ȼ��ѧ����Ŀ����ӵ��ڶ���һ����Ŀ�Ķ�����Ŀ
    //���������xml�ĵ� ����һ��forѭ������Ӧ����Ϣ���뵽list�У�
    //���������Ϣ�Ѷ�Ӧ�����ݼ��뵽��ͬ��list�У���ʾ����
    //�����������Ϣ������ҳ����ʾ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lecture);
		Intent intent=getIntent();
		URL=intent.getStringExtra("server3");
		handler=new myHandler();
		errhandler=new ErrHandler();
		progressBar=(ProgressBar)findViewById(R.id.lecture_progressBar);
		DownThread t=new DownThread();
		t.start();
	}
	
	private void init(){
		//��ʼ������list��Ϊ���࣬��ʼ��infos1��infos2
		//����һ��level��Ϣ
		infos1=new ArrayList<LectureInfo>();
		infos2=new ArrayList<LectureInfo>();
		for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
			LectureInfo type = (LectureInfo) iterator.next();
			if(type.getLevel().equals("human")){
				infos1.add(type);
			}
			else{
				infos2.add(type);
			}
		}
		
	}
	
	private List<LectureInfo> parse(String xmlstr){
		SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
		List<LectureInfo> infos=new ArrayList<LectureInfo>();
		try{
			XMLReader xmlReader=saxParserFactory.newSAXParser().getXMLReader();
			LectureInfoContentHandler lectureInfoContentHandler=
		            new LectureInfoContentHandler(infos);
            xmlReader.setContentHandler(lectureInfoContentHandler);
            xmlReader.parse(new InputSource(new StringReader(xmlstr)));
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("���������쳣");
		}
		return infos;
		
	}
	
	
	
	class myHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			lecturexml=(String) msg.obj;
			if(lecturexml.equals("")){
				ConnectionThread t=new ConnectionThread();
				t.start();
			}
			else{
			infos=parse(lecturexml);
			init();
			//��listΪһ����Ŀ�ṩ����
			List<Map<String,String>> groups=new ArrayList<Map<String,String>>();
			Map<String,String> map1=new HashMap<String,String>();
			map1.put("group", "���Ŀ�ѧ�ི��");
			Map<String,String> map2=new HashMap<String,String>();
			map2.put("group", "��Ȼ��ѧ�ི��");
			groups.add(map1);
			groups.add(map2);
			//��listΪ��һ��һ����Ŀ�Ķ�����Ŀ�ṩ����
			List<Map<String,String>> child1=new ArrayList<Map<String,String>>();
			//Map<String,String> 
			for (Iterator iterator = infos1.iterator(); iterator.hasNext();) {
				LectureInfo type = (LectureInfo) iterator.next();
				Map<String,String> map=new HashMap<String,String>();
				map.put("child", type.getTitle());
				child1.add(map);
			}
			List<Map<String,String>> child2=new ArrayList<Map<String,String>>();
			for (Iterator iterator = infos2.iterator(); iterator.hasNext();) {
				LectureInfo type = (LectureInfo) iterator.next();
				Map<String,String> map=new HashMap<String,String>();
				map.put("child", type.getTitle());
				child2.add(map);
			}
			//System.out.println("child1-->"+child1.get(0).get("child"));��ȷ
			//����һ��List����List���������洢���еĶ�����Ŀ������
			List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
			childs.add(child1);
			childs.add(child2);
			//����һ��SimpleExpandableListAdapter����
			//1.context
			//2.һ����Ŀ������
			//3.��������һ����Ŀ��ʽ�Ĳ����ļ�
			//4.ָ��һ����Ŀ���ݵ�key
			//5.ָ��һ����Ŀ������ʾ�ؼ���id
			//6.ָ��������Ŀ������
			//7.�������ö�����Ŀ��ʽ�Ĳ����ļ�
			//8.ָ��������Ŀ���ݵ�key
			//9.ָ��������Ŀ������ʾ�ؼ���id
			SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
					LectureActivity.this, groups, R.layout.lecturegroup, new String[]{"group"},
					 new int[]{R.id.lecturegroup}, childs, R.layout.lecturechild,
					     new String[]{"child"},new int[]{R.id.lecturechild});
			setListAdapter(adapter);
			progressBar.setVisibility(View.GONE);
		}
	 }
	}
	
	
	class ErrHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			String name=(String) msg.obj;
			if(name.equals("error")){
				progressBar.setVisibility(View.GONE);
				Intent intent=new Intent();
				intent.setClass(LectureActivity.this,ConnectionErrorActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
	}
	
	
	
	class DownThread extends Thread{
        private String xml;
		@Override
		public void run() {
			xml=downloadXml("lecturelist.xml");
			System.out.println("lecturexml--->"+xml);
			Message msg=handler.obtainMessage();
			msg.obj=xml;
			handler.sendMessage(msg);
		}
		
		private String downloadXml(String str){
			HttpDownloader httpDownloader=new HttpDownloader();
			String result=httpDownloader.download(URL+str);
			return result;
		}
		
	}
	
	class ConnectionThread extends Thread{
        private String err="error";
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg=errhandler.obtainMessage();
			msg.obj=err;
			errhandler.sendMessage(msg);
		}
		
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Intent intent=new Intent();
		Bundle data=new Bundle();
		//��infos������һ��Activity������һ��Activity��ͨ���봫���id�Աȣ���ʾ����Ӧ����Ϣ
		data.putSerializable("data", (Serializable) infos);
		if(groupPosition==0){
		   intent.putExtra("id", infos1.get(childPosition).getId());
		}
		else {
			intent.putExtra("id", infos2.get(childPosition).getId());
		}
		intent.putExtra("server", URL);
		intent.putExtras(data);
		intent.setClass(LectureActivity.this,LectureDetailActivity.class);
		startActivity(intent);
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}
	
}
