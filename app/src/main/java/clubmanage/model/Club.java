package clubmanage.model;

import java.io.Serializable;

public class Club implements Serializable {
	private int club_id;
	private String category_name;
	private String club_icon;
	private String club_cover;
	private String club_name;
	private String club_introduce;
	private String slogan;
	private String club_place;
	private int member_number;
	private byte if_club_end;
	private String intendant;
	public int getClub_id() {
		return club_id;
	}
	public void setClub_id(int club_id) {
		this.club_id = club_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getClub_icon() {
		return club_icon;
	}
	public void setClub_icon(String club_icon) {
		this.club_icon = club_icon;
	}
	public String getClub_cover() {
		return club_cover;
	}
	public void setClub_cover(String club_cover) {
		this.club_cover = club_cover;
	}
	public String getClub_name() {
		return club_name;
	}
	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}
	public String getClub_introduce() {
		return club_introduce;
	}
	public void setClub_introduce(String club_introduce) {
		this.club_introduce = club_introduce;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getClub_place() {
		return club_place;
	}
	public void setClub_place(String club_place) {
		this.club_place = club_place;
	}
	public int getMember_number() {
		return member_number;
	}
	public void setMember_number(int member_number) {
		this.member_number = member_number;
	}
	public byte getIf_club_end() {
		return if_club_end;
	}
	public void setIf_club_end(byte if_club_end) {
		this.if_club_end = if_club_end;
	}
	public String getIntendant() {
		return intendant;
	}
	public void setIntendant(String intendant) {
		this.intendant = intendant;
	}
	
}
