package model;

import java.io.Serializable;

public class LectureInfo implements Serializable{
	private String id;
	private String title;
	private String time;
	private String place;
	private String master;
	private String masterid;
	private String level;//代表是人文科学还是自然科学类
	
	public LectureInfo(String id, String title, String time, String place,
			String master, String masterid,String level) {
		super();
		this.id = id;
		this.title = title;
		this.time = time;
		this.place = place;
		this.master = master;
		this.masterid = masterid;
		this.level=level;
	}
	
	
	public LectureInfo() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "LectureInfo [id=" + id + ", title=" + title + ", time=" + time
				+ ", place=" + place + ", master=" + master + ", masterid="
				+ masterid + ", level=" + level + "]";
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getMasterid() {
		return masterid;
	}
	public void setMasterid(String masterid) {
		this.masterid = masterid;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}
	
}
