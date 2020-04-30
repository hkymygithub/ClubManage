package clubmanage.model;

import java.sql.Timestamp;

public class Join_club {
	private int joinclub_formid;
	private int club_id;
	private String uid;
	private String join_name;
	private String reason;
	private Timestamp propose_time;
	public int getJoinclub_formid() {
		return joinclub_formid;
	}
	public void setJoinclub_formid(int joinclub_formid) {
		this.joinclub_formid = joinclub_formid;
	}
	public int getClub_id() {
		return club_id;
	}
	public void setClub_id(int club_id) {
		this.club_id = club_id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getJoin_name() {
		return join_name;
	}
	public void setJoin_name(String join_name) {
		this.join_name = join_name;
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
	private int state;
	private String handle_people_id;
	
}
