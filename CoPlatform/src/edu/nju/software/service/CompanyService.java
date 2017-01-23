package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.Company;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;


public interface CompanyService {
	public GeneralResult<List<Company>> getAll();
	
	public GeneralResult<Company> getById(int id);
	
	public GeneralResult<Integer> create(Company company);
	
	public NoDataResult update(Company company);
	
	public NoDataResult delete(int id);
}
