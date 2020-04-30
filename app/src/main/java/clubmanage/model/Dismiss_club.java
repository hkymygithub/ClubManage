package clubmanage.model;

import java.sql.Timestamp;

public class Dismiss_club {
	private int dismissclub_formid;
	private String uid;
	private String dismiss_name;
	private int club_id;
	private String club_name;
	private String reason;
	private Timestamp propose_time;
	private int state;
	private String handle_people_id;
	public int getDismissclub_formid() {
		return dismissclub_formid;
	}
	public void setDismissclub_formid(int dismissclub_formid) {
		this.dismissclub_formid = dismissclub_formid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDismiss_name() {
		return dismiss_name;
	}
	public void setDismiss_name(String dismiss_name) {
		this.dismiss_name = dismiss_name;
	}
	public int getClub_id() { return club_id; }
	public void setClub_id(int club_id) { this.club_id = club_id; }
	public String getClub_name() {
		return club_name;
	}
	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Timestamp getPropose_time() {
		return propose_time;
	}
	public void setPropose_time(Timestamp propose_time) {
		this.propose_time = propose_time;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getHandle_people_id() {
		return handle_people_id;
	}
	public void setHandle_people_id(String handle_people_id) {
		this.handle_people_id = handle_people_id;
	}
	
}
