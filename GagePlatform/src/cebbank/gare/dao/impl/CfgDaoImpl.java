package cebbank.gare.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.pojo.Config;
import cebbank.gare.dao.CfgDao;

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
	public void update(Config cfg) throws DataAccessException {
		getHibernateTemplate().update(cfg);
	}

	/*@Override
	public void delete(int id) throws DataAccessException {
		getHibernateTemplate().delete(new Cfg());
	}*/

	@Override
	public Config getByKey(String key) {
		@SuppressWarnings("unchecked")
		List<Config> cfgList = getHibernateTemplate().find(
				"from Cfg where key = ?", key);
		if(null != cfgList && !cfgList.isEmpty()) {
			return cfgList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Config getById(int id) {
		return getHibernateTemplate().get(Config.class, id);
	}

	
}
