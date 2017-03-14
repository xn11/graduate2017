package cebbank.gage.dao;

import java.util.List;

import cebbank.gage.model.User;

public interface UserDao {
	public List<User> getAll();
	
	public int create(User admin);
	
	public void update(User admin);
	
	public void delete(int id);

}
