package clubmanage.model;

public class Activity_picture {
	private int picture_id;
	private int activity_id;
	private byte[] picture_details;
	public int getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public byte[] getPicture_details() {
		return picture_details;
	}
	public void setPicture_details(byte[] picture_details) {
		this.picture_details = picture_details;
	}
}
