package cebbank.gage.model;

import java.io.Serializable;
import java.sql.Date;

import cebbank.gare.common.RoleEnum;

/**
 * 
 * @author xn
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private RoleEnum roleId;
	private String password;
	private String telephone;
	private Date registerTime;
	private int status;
	private Date lastLoginTime;
	private Date lastChangeTime;
	private String note;

	public User() {

	}

	public User(int id) {
		this.id = id;
	}
	
	public User(String name, RoleEnum roleId, String password, String telephone, int status) {
		super();
		this.name = name;
		this.roleId = roleId;
		this.password = password;
		this.telephone = telephone;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", roleId=" + roleId + ", password=" + password + ", telephone="
				+ telephone + ", registerTime=" + registerTime + ", status=" + status + ", lastLoginTime="
				+ lastLoginTime + ", lastChangeTime=" + lastChangeTime + ", note=" + note + "]";
	}

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

	public RoleEnum getRoleId() {
		return roleId;
	}

	public void setRoleId(RoleEnum roleId) {
		this.roleId = roleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastChangeTime() {
		return lastChangeTime;
	}

	public void setLastChangeTime(Date lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
