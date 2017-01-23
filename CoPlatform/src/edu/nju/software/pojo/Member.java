package edu.nju.software.pojo;

public class Member {
	private int id;
	private String name;
	private int companyId;
	private String workId;
	private String password;
	private String wxNumber;
	private String qqNumber;
	private String phone;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Member() {
	}

	public Member(int id) {
		this.id = id;
	}

	public Member(String name, int companyId, String workId, String password, String qqNumber,
			String wxNumber, String phone) {
		this.name = name;
		this.companyId = companyId;
		this.workId = workId;
		this.password = password;
		this.qqNumber = qqNumber;
		this.wxNumber = wxNumber;
		this.phone = phone;
	}
	
	public Member(int id, String name, int companyId, String workId,
			String qqNumber, String wxNumber, String phone) {
		this.id = id;
		this.name = name;
		this.companyId = companyId;
		this.workId = workId;
		this.qqNumber = qqNumber;
		this.wxNumber = wxNumber;
		this.phone = phone;
	}
	
	/**
	 * for create member, without id.
	 * @param name
	 * @param company
	 * @param workId
	 * @param wxNumber
	 * @param qqNumber
	 * @param phone
	 * @param openId
	 */
	public Member(String name, int companyId, String workId,
			String password, String wxNumber, String qqNumber, String phone,
			String openId) {
		this.name = name;
		this.companyId = companyId;
		this.workId = workId;
		this.password = password;
		this.wxNumber = wxNumber;
		this.qqNumber = qqNumber;
		this.phone = phone;
		this.openId = openId;
	}
	
	/**
	 * for all fields
	 * @param id
	 * @param name
	 * @param company
	 * @param workId
	 * @param password
	 * @param wxNumber
	 * @param qqNumber
	 * @param phone
	 * @param openId
	 */
	public Member(int id, String name, int companyId, String workId,
			String password, String wxNumber, String qqNumber, String phone,
			String openId) {
		this.id = id;
		this.name = name;
		this.companyId = companyId;
		this.workId = workId;
		this.password = password;
		this.wxNumber = wxNumber;
		this.qqNumber = qqNumber;
		this.phone = phone;
		this.openId = openId;
	}

}
