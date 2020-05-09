package clubmanage.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Create_activity implements Serializable {
	private int activity_approval_id;
	private int club_id;
	private String activity_name;
	private String poster;
	private String area_name;
	private String activity_owner_id;
	private String activity_owner_name;
	private Timestamp activity_start_time;
	private Timestamp activity_end_time;
	private String activity_details;
	private String activity_attention;
	private byte if_public_activity;
	private String activity_category;
	private String reason;
	private Timestamp propose_time;
	private int state;
	private String suggestion;
	private String handle_people_id;
	public int getActivity_approval_id() {
		return activity_approval_id;
	}
	public void setActivity_approval_id(int activity_approval) {
		this.activity_approval_id = activity_approval;
	}
	public int getClub_id() {
		return club_id;
	}
	public void setClub_id(int club_id) {
		this.club_id = club_id;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getActivity_owner_id() {
		return activity_owner_id;
	}
	public void setActivity_owner_id(String activity_owner_id) {
		this.activity_owner_id = activity_owner_id;
	}
	public String getActivity_owner_name() {
		return activity_owner_name;
	}
	public void setActivity_owner_name(String activity_owner_name) {
		this.activity_owner_name = activity_owner_name;
	}
	public Timestamp getActivity_start_time() {
		return activity_start_time;
	}
	public void setActivity_start_time(Timestamp activity_start_time) {
		this.activity_start_time = activity_start_time;
	}
	public Timestamp getActivity_end_time() {
		return activity_end_time;
	}
	public void setActivity_end_time(Timestamp activity_end_time) {
		this.activity_end_time = activity_end_time;
	}
	public String getActivity_details() {
		return activity_details;
	}
	public void setActivity_details(String activity_details) {
		this.activity_details = activity_details;
	}
	public byte getIf_public_activity() {
		return if_public_activity;
	}
	public void setIf_public_activity(byte if_public_activity) {
		this.if_public_activity = if_public_activity;
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
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getHandle_people_id() {
		return handle_people_id;
	}
	public void setHandle_people_id(String handle_people_id) {
		this.handle_people_id = handle_people_id;
	}
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getActivity_category() {
		return activity_category;
	}

	public void setActivity_category(String activity_category) {
		this.activity_category = activity_category;
	}
	public String getActivity_attention() {
		return activity_attention;
	}
	public void setActivity_attention(String activity_attention) {
		this.activity_attention = activity_attention;
	}
}
