package model;

public class NotificationInfo {
	private String college;//院系
	private String title;//通知标题
	private String time;//通知时间
	private String id;
	
	public NotificationInfo(String college, String title, String time, String id) {
		super();
		this.college = college;
		this.title = title;
		this.time = time;
		this.id = id;
	}
	
	
	public NotificationInfo() {
		super();
	}


	@Override
	public String toString() {
		return "NotificationInfo [college=" + college + ", title=" + title
				+ ", time=" + time + ", id=" + id + "]";
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
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
