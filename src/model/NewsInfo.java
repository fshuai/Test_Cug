package model;

public class NewsInfo {
     private String title;
     private String time;
     private String id;
     
     //���ι��캯��
	public NewsInfo(String title, String time, String id) {
		super();
		this.title = title;
		this.time = time;
		this.id = id;
	}
	
	//�����ι��캯��
	public NewsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "NewsInfo [title=" + title + ", time=" + time+ ",id=" + id + "]";
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
     
}
