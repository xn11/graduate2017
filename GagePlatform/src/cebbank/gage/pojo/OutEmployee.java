package cebbank.gage.pojo;


public class OutEmployee {
	private int id;
	private String name;
	private String wxNumber;
	private String qqNumber;
	private String phone;
	private String password;
	private String openId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWxNumber() {
		return wxNumber;
	}
	public void setWxNumber(String wxNumber) {
		this.wxNumber = wxNumber;
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public OutEmployee() {}
	
	public OutEmployee(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
