package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.Company;

public interface CompanyDao {
	public List<Company> getAll();
	
	public int create(Company company);
	
	public void update(Company company);
	
	public void delete(int id);
}
