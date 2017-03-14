package cebbank.gage.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.dao.TaskDao;
import cebbank.gage.model.Member;
import cebbank.gage.model.Task;
import cebbank.gage.model.TaskAssign;

@Repository
public class TaskDaoImpl extends HibernateDaoBase implements TaskDao {

	@Override
	public int create(Task task) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(task);
	}

	@Override
	public void update(Task task) throws DataAccessException {
		getHibernateTemplate().update(task);
	}

	@Override
	public void delete(int taskId) throws DataAccessException {
		getHibernateTemplate().delete(new Task(taskId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getByProject(int projectId) throws DataAccessException {
		Session session = super.getSession(true);
		try {
			Query query = session.createQuery("from Task where projectId = " + projectId + " order by id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public Task getById(int id) {
		return getHibernateTemplate().get(Task.class, id);
	}

	@Override
	public void deleteAllByProject(int projectId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("delete from Task where projectId = " + projectId);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public void assignTask(TaskAssign taskAssign) {
		getHibernateTemplate().save(taskAssign);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksByMember(int memberId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("select t from TaskAssign as ta, Task as t, Member as m where ta.taskId = t.id and "
					+ "ta.memberId = m.id and m.id = " + memberId + " order by t.id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	// bug, outEmployee and company
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksByOutEmployee(int companyId, int outEmployeeId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("select t from TaskAssign as ta, Task as t, OutEmployee as oe where  ta.taskId = t.id and "
					+ "ta.outEmployeeId = oe.id and oe.id = " + outEmployeeId + " order by t.id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public void deleteTaskAssignByTask(int taskId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("delete from TaskAssign where taskId = " + taskId);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
	}
	
	@Override
	public void deleteTaskAssign(int taskId, Integer memberId, Integer outEmployeeId) {
		if(null == memberId && null == outEmployeeId) {
			return;
		}
		
		Session session = super.getSession(true);
		String sql = "delete from TaskAssign where taskId = " + taskId;
		if(null != memberId) {
			sql +=  " and memberId = " + memberId;
		}else {
			sql += "and outEmployeeId = " + outEmployeeId;
		}
		try {
			Query query = getSession().createQuery(sql);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksWithChildrenByProject(int projectId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("select t from Task as t where projectId = " + projectId + " order by t.depth asc, id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getRelatedMembers(int taskId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("select m from TaskAssign as ts, Member as m where ts.memberId = m.id and ts.taskId = " + taskId);
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

}
