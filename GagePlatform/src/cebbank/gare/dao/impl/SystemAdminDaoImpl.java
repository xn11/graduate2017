package cebbank.gare.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cebbank.gage.pojo.SystemAdmin;
import cebbank.gare.dao.SystemAdminDao;

@Repository
public class SystemAdminDaoImpl extends HibernateDaoBase implements SystemAdminDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAdmin> getAll() {
		return getHibernateTemplate().find("from SystemAdmin");
	}
	

}
