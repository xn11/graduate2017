package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.Admin;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface AdminService {
	public GeneralResult<List<Admin>> getAll();
	
	public GeneralResult<Admin> getById(int id);
	
	public GeneralResult<Admin> getByMailAndPassword(String mail, String password);
	
	public GeneralResult<Integer> create(Admin admin);
	
	public NoDataResult update(Admin admin);
	
	public NoDataResult delete(int id);
}
