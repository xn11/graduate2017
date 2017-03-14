package cebbank.gage.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.dao.ProjectDao;
import cebbank.gage.model.Project;

@Repository
public class ProjectDaoImpl extends HibernateDaoBase implements ProjectDao {

	@Override
	public Project getById(int id) throws DataAccessException {
		return getHibernateTemplate().get(Project.class, id);
	}

	@Override
	public int create(Project project) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(project);
	}

	@Override
	public void update(Project project)  throws DataAccessException {
		getHibernateTemplate().update(project);
	}

	@Override
	public void delete(int id)  throws DataAccessException {
		Project project = new Project(id);
		getHibernateTemplate().delete(project);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getByCompany(int companyId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("from Project where companyId = " + companyId + " order by id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public void deleteTaskAssign(int projectId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("delete from TaskAssign where id > 0 and taskId in (select id from Task where projectId = "
					+ projectId + ")");
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
	}

}
