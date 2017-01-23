package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.SystemAdmin;
import edu.nju.software.util.GeneralResult;

public interface SystemAdminService {
	public GeneralResult<List<SystemAdmin>> getAll();
	
	public GeneralResult<SystemAdmin> getByUserNameAndPassword(String userName, String password);
	
	public GeneralResult<SystemAdmin> getById(int id);
}
