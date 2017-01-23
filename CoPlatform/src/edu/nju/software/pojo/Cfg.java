package edu.nju.software.pojo;

import java.io.Serializable;

public class Cfg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String cfgkey;
	private String cfgvalue;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCfgkey() {
		return cfgkey;
	}

	public void setCfgkey(String cfgkey) {
		this.cfgkey = cfgkey;
	}

	public String getCfgvalue() {
		return cfgvalue;
	}

	public void setCfgvalue(String cfgvalue) {
		this.cfgvalue = cfgvalue;
	}

	public Cfg() {}

	public Cfg(String cfgkey, String cfgvalue) {
		this.cfgkey = cfgkey;
		this.cfgvalue = cfgvalue;
	}
	
}
