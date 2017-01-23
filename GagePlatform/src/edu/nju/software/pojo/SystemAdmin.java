package edu.nju.software.pojo;

import java.io.Serializable;

public class SystemAdmin implements Serializable {

	private static final long serialVersionUID = 1127903055654217309L;
	
	private int id;
	private String userName;
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
