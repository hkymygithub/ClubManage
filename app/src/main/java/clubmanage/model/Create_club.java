package clubmanage.model;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.Serializable;
import java.sql.Timestamp;

public class Create_club implements Serializable {
	public static Create_club createClub=null;
	private int applyclub_formid;
	private String area_name;
	private String uid;
	private String applican_name;
	private String club_name;
	private String club_category;
	private String introduce;
	private String reason;
	private Timestamp propose_time;
	private int state;
	private String handle_people_id;
	private String suggestion;
	public int getApplyclub_formid() {
		return applyclub_formid;
	}
	public void setApplyclub_formid(int applyclub_formid) {
		this.applyclub_formid = applyclub_formid;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getApplican_name() {
		return applican_name;
	}
	public void setApplican_name(String applican_name) {
		this.applican_name = applican_name;
	}
	public String getClub_name() {
		return club_name;
	}
	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}
	public String getClub_category() {
		return club_category;
	}
	public void setClub_category(String club_category) {
		this.club_category = club_category;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
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
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
}
