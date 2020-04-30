package clubmanage.model;

import java.sql.Timestamp;

public class Comment {
	private int comment_id;
	private int activity_id;
	private String uid;
	private String comment_details;
	private Timestamp comment_time;
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getComment_details() {
		return comment_details;
	}
	public void setComment_details(String comment_details) {
		this.comment_details = comment_details;
	}
	public Timestamp getComment_time() {
		return comment_time;
	}
	public void setComment_time(Timestamp comment_time) {
		this.comment_time = comment_time;
	}
}
