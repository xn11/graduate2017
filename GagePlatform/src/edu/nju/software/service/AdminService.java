package edu.nju.software.service;

import java.util.List;

import cebbank.gage.pojo.User;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface AdminService {
	public GeneralResult<List<User>> getAll();
	
	public GeneralResult<User> getById(int id);
	
	public GeneralResult<User> getByMailAndPassword(String mail, String password);
	
	public GeneralResult<Integer> create(User admin);
	
	public NoDataResult update(User admin);
	
	public NoDataResult delete(int id);
}
