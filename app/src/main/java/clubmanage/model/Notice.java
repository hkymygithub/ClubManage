package clubmanage.model;

import java.sql.Timestamp;

public class Notice {
	private int notice_id;
	private int club_id;
	private String notice_details;
	private Timestamp notice_time;
	public int getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}
	public int getClub_id() {
		return club_id;
	}
	public void setClub_id(int club_id) {
		this.club_id = club_id;
	}
	public String getNotice_details() {
		return notice_details;
	}
	public void setNotice_details(String notice_details) {
		this.notice_details = notice_details;
	}
	public Timestamp getNotice_time() {
		return notice_time;
	}
	public void setNotice_time(Timestamp notice_time) {
		this.notice_time = notice_time;
	}
	
}
