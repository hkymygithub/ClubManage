package clubmanage.model;

import java.sql.Timestamp;

public class Sign_up {
	private int entry_formid;
	private String uid;
	private int activity_id;
	private Timestamp propose_time;
	public int getEntry_formid() {
		return entry_formid;
	}
	public void setEntry_formid(int entry_formid) {
		this.entry_formid = entry_formid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public Timestamp getPropose_time() {
		return propose_time;
	}
	public void setPropose_time(Timestamp propose_time) {
		this.propose_time = propose_time;
	}
}
