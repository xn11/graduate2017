package cebbank.gare.dao;

import java.util.List;

import cebbank.gage.pojo.Company;
import cebbank.gage.pojo.OutEmployee;

public interface OutEmployeeDao {
	
	public List<OutEmployee> getByCompany(int companyId);
	
	public List<Company> getRelatedCompanies(int outEmployeeId);
	
	public OutEmployee getById(int outEmployeeId);

    public OutEmployee getByOpenId(String openId);
	
	public OutEmployee getByPhoneAndPassword(String phone, String password);
	
	public void update(OutEmployee outEmployee);
}
