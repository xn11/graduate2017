package cebbank.gare.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import cebbank.gage.model.Log;
import cebbank.gare.dao.LogDao;

@Repository
public class LogDaoImpl extends HibernateDaoBase implements LogDao {

	@Override
	public int create(Log log) {
		return (Integer) getHibernateTemplate().save(log);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> getByProject(int companyId, int projectId, Date startTime,
			Date endTime) {
		return getHibernateTemplate().find("from Log where company_id = ? and project_id = ?",
				companyId, projectId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> getByTask(int companyId, int taskId, Date startTime,
			Date endTime) {
		return getHibernateTemplate().find("from Log where company_id = ? and task_id = ?",
				companyId, taskId);
	}

}
