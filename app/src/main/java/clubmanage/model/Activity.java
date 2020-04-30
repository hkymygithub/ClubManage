package clubmanage.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Activity implements Serializable {
	private int activity_id;
	private int club_id;
	private String activity_name;
	private byte[] poster;
	private Timestamp activity_start_time;
	private Timestamp activity_end_time;
	private String activity_introduce;
	private String activity_place;
	private String activity_attention;
	private Byte if_public_activity;
	private String activity_category;
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public int getClub_id() {
		return club_id;
	}
	public void setClub_id(int club_id) {
		this.club_id = club_id;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
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
	public String getActivity_introduce() {
		return activity_introduce;
	}
	public void setActivity_introduce(String activity_introduce) {
		this.activity_introduce = activity_introduce;
	}
	public String getActivity_place() {
		return activity_place;
	}
	public void setActivity_place(String activity_place) {
		this.activity_place = activity_place;
	}
	public String getActivity_attention() {
		return activity_attention;
	}
	public void setActivity_attention(String activity_attention) {
		this.activity_attention = activity_attention;
	}
	public Byte getIf_public_activity() {
		return if_public_activity;
	}
	public void setIf_public_activity(Byte if_public_activity) {
		this.if_public_activity = if_public_activity;
	}
	public String getaAtivity_category() {
		return activity_category;
	}
	public void setActivity_category(String activity_category) {
		this.activity_category = activity_category;
	}
	public byte[] getPoster() {
		return poster;
	}

	public void setPoster(byte[] poster) {
		this.poster = poster;
	}
}
