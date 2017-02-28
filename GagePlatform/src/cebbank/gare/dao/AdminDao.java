package cebbank.gare.dao;

import java.util.List;

import cebbank.gage.pojo.User;

public interface AdminDao {
	public List<User> getAll();
	
	public int create(User admin);
	
	public void update(User admin);
	
	public void delete(int id);

}
