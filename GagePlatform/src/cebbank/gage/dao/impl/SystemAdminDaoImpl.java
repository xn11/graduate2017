package cebbank.gage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cebbank.gage.dao.SystemAdminDao;
import cebbank.gage.model.SystemAdmin;

@Repository
public class SystemAdminDaoImpl extends HibernateDaoBase implements SystemAdminDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAdmin> getAll() {
		return getHibernateTemplate().find("from SystemAdmin");
	}
	

}
