package cebbank.gage.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.dao.CompanyDao;
import cebbank.gage.model.Company;

@Repository
public class CompanyDaoImpl extends HibernateDaoBase implements CompanyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from Company");
	}

	@Override
	public int create(Company company) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(company);
	}

	@Override
	public void update(Company company) throws DataAccessException {
		getHibernateTemplate().update(company);
	}

	@Override
	public void delete(int id) throws DataAccessException {
		Company company = new Company();
		company.setId(id);
		getHibernateTemplate().delete(company);
	}

}
