package cebbank.gage.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.dao.UserDao;
import cebbank.gage.model.User;

@Repository
public class UserDaoImpl extends HibernateDaoBase implements UserDao {
	
	public UserDaoImpl() {}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from User");
	}

	@Override
	public int create(User user) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(user);
	}

	@Override
	public void update(User user) throws DataAccessException {
		getHibernateTemplate().update(user);
	}

	@Override
	public void delete(int id) throws DataAccessException {
		getHibernateTemplate().delete(new User(id));
	}

	
}
