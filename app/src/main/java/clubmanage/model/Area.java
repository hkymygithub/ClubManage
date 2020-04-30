package clubmanage.model;

public class Area {
	private String area_name;
	private byte specialties;
	private byte usable;
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public byte getSpecialties() {
		return specialties;
	}
	public void setSpecialties(byte specialties) {
		this.specialties = specialties;
	}
	public byte getUsable() {
		return usable;
	}
	public void setUsable(byte usable) {
		this.usable = usable;
	}
}
