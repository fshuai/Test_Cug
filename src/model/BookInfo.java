package model;

import java.io.Serializable;

public class BookInfo implements Serializable{
	private String bookName;
	private String writer;//����
	private String id;    //�������
	private String publish;//������
	private String place;  //�ݲصص�
	private String condition;//�ݲ�״̬
	
	
	//���ι��캯��
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
	
	//�����ι��캯��
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

	//����get��set����
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
