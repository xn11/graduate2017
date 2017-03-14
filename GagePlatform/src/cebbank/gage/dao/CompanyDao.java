package cebbank.gage.dao;

import java.util.List;

import cebbank.gage.model.Company;

public interface CompanyDao {
	public List<Company> getAll();
	
	public int create(Company company);
	
	public void update(Company company);
	
	public void delete(int id);
}
