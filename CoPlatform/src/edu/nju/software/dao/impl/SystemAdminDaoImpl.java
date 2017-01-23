package edu.nju.software.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.nju.software.dao.SystemAdminDao;
import edu.nju.software.pojo.SystemAdmin;

@Repository
public class SystemAdminDaoImpl extends HibernateDaoBase implements SystemAdminDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAdmin> getAll() {
		return getHibernateTemplate().find("from SystemAdmin");
	}
	

}
