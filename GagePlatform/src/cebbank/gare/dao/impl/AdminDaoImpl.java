package cebbank.gare.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.pojo.User;
import cebbank.gare.dao.AdminDao;

@Repository
public class AdminDaoImpl extends HibernateDaoBase implements AdminDao {
	
	public AdminDaoImpl() {}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from Admin");
	}

	@Override
	public int create(User admin) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(admin);
	}

	@Override
	public void update(User admin) throws DataAccessException {
		getHibernateTemplate().update(admin);
	}

	@Override
	public void delete(int id) throws DataAccessException {
		getHibernateTemplate().delete(new User(id));
	}

	
}
