package model;

import java.io.Serializable;

public class BookInfo implements Serializable{
	private String bookName;
	private String writer;//作者
	private String id;    //索引书号
	private String publish;//出版社
	private String place;  //馆藏地点
	private String condition;//馆藏状态
	
	
	//带参构造函数
	public BookInfo(String bookName, String writer, String id, String publish,
			String place, String condition) {
		super();
		this.bookName = bookName;
		this.writer = writer;
		this.id = id;
		this.publish = publish;
		this.place = place;
		this.condition = condition;
	}
	
	//不带参构造函数
	public BookInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "BookInfo [bookName=" + bookName + ", writer=" + writer
				+ ", id=" + id + ", publish=" + publish + ", place=" + place
				+ ", condition=" + condition + "]";
	}

	//各类get和set方法
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPublish() {
		return publish;
	}
	
	public void setPublish(String publish) {
		this.publish = publish;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
