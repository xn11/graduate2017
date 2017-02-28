package edu.nju.software.service;

import java.util.List;

import cebbank.gage.pojo.Company;
import cebbank.gage.pojo.OutEmployee;
import cebbank.gage.pojo.Task;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface OutEmployeeService {
	
	public GeneralResult<List<OutEmployee>> getByCompany(int companyId);
	
	public GeneralResult<List<Company>> getRelatedCompanies(int outEmployeeId);
	
	public GeneralResult<List<Task>> getTasks(int companyId, int outEmployeeId);
	
	public GeneralResult<OutEmployee> getById(int outEmployeeId);
	
	public GeneralResult<OutEmployee> getByOpenId(String openId);
	
	public GeneralResult<OutEmployee> getByPhoneAndPassword(String phone, String password);
	
	public NoDataResult update(OutEmployee outEmployee);
}
