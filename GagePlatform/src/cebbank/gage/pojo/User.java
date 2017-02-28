package cebbank.gage.pojo;

import java.io.Serializable;
import java.sql.Date;

import cebbank.gare.common.RoleEnum;

/**
 * 
 * @author xn
 *
 */
public class User implements Serializable {

	private int id;
	private String  name;
	private  RoleEnum roleId;
	private String password;
	private String telephone;
	private Date registerTime;
	private int status;
	private Date last_loginTime;
	private Date last_changeTime;
	private String note;
	
	public User(){
		
	}
	
	public User(int uid, String uname, int role_id, String password, String telephone, Date register_time, int status,
			Date last_login_time, Date last_change_time, String note) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.role_id = role_id;
		this.password = password;
		this.telephone = telephone;
		this.register_time = register_time;
		this.status = status;
		this.last_login_time = last_login_time;
		this.last_change_time = last_change_time;
		this.note = note;
	}



	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
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

	public Date getRegister_time() {
		return register_time;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public Date getLast_change_time() {
		return last_change_time;
	}

	public void setLast_change_time(Date last_change_time) {
		this.last_change_time = last_change_time;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
