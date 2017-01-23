package edu.nju.software.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.AdminDao;
import edu.nju.software.pojo.Admin;

@Repository
public class AdminDaoImpl extends HibernateDaoBase implements AdminDao {
	
	public AdminDaoImpl() {}

	@SuppressWarnings("unchecked")
	@Override
	public List<Admin> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from Admin");
	}

	@Override
	public int create(Admin admin) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(admin);
	}

	@Override
	public void update(Admin admin) throws DataAccessException {
		getHibernateTemplate().update(admin);
	}

	@Override
	public void delete(int id) throws DataAccessException {
		getHibernateTemplate().delete(new Admin(id));
	}

	
}
