package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.Admin;

public interface AdminDao {
	public List<Admin> getAll();
	
	public int create(Admin admin);
	
	public void update(Admin admin);
	
	public void delete(int id);

}
