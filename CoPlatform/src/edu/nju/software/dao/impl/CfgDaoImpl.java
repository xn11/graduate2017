package edu.nju.software.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.CfgDao;
import edu.nju.software.pojo.Cfg;

@Repository
public class CfgDaoImpl extends HibernateDaoBase implements CfgDao {
	
	public CfgDaoImpl() {}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Cfg> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from Admin");
	}

	@Override
	public int create(Cfg cfg) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(cfg);
	}*/

	@Override
	public void update(Cfg cfg) throws DataAccessException {
		getHibernateTemplate().update(cfg);
	}

	/*@Override
	public void delete(int id) throws DataAccessException {
		getHibernateTemplate().delete(new Cfg());
	}*/

	@Override
	public Cfg getByKey(String key) {
		@SuppressWarnings("unchecked")
		List<Cfg> cfgList = getHibernateTemplate().find(
				"from Cfg where key = ?", key);
		if(null != cfgList && !cfgList.isEmpty()) {
			return cfgList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Cfg getById(int id) {
		return getHibernateTemplate().get(Cfg.class, id);
	}

	
}
