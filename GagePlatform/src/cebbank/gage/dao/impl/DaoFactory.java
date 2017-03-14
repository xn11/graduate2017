package cebbank.gage.dao.impl;

import cebbank.gage.dao.CompanyDao;
import cebbank.gage.dao.LogDao;
import cebbank.gage.dao.MemberDao;
import cebbank.gage.dao.NewsDao;
import cebbank.gage.dao.OutEmployeeDao;
import cebbank.gage.dao.ProjectDao;
import cebbank.gage.dao.TaskDao;
import cebbank.gage.dao.UserDao;

public class DaoFactory {
	
	private UserDao adminDao;
	private CompanyDao companyDao;
	private LogDao logDao;
	private MemberDao memberDao;
	private NewsDao newsDao;
	private OutEmployeeDao outEmployeeDao;
	private TaskDao taskDao;
	private ProjectDao projectDao;
	
	private static DaoFactory instance = null;
	
	public static DaoFactory instance() {
		if(null == instance) {
			instance = new DaoFactory();
		}
		return instance;
	}

	public UserDao getAdminDao() {
		if(null == adminDao) {
			adminDao = new UserDaoImpl();
		}
		return adminDao;
	}

	public CompanyDao getCompanyDao() {
		if(null == companyDao) {
			companyDao = new CompanyDaoImpl();
		}
		return companyDao;
	}

	public LogDao getLogDao() {
		if(null == logDao) {
			logDao = new LogDaoImpl();
		}
		return logDao;
	}

	public MemberDao getMemberDao() {
		if(null == memberDao) {
			memberDao = new MemberDaoImpl();
		}
		return memberDao;
	}

	public NewsDao getNewsDao() {
		if(null == newsDao) {
			newsDao = new NewsDaoImpl();
		}
		return newsDao;
	}

	public OutEmployeeDao getOutEmployeeDao() {
		if(null == outEmployeeDao) {
			outEmployeeDao = new OutEmployeeDaoImpl();
		}
		return outEmployeeDao;
	}

	public ProjectDao getProjectDao() {
		if(null == projectDao) {
			projectDao = new ProjectDaoImpl();
		}
		return projectDao;
	}
	
	public TaskDao getTaskDao() {
		if(null == taskDao) {
			taskDao = new TaskDaoImpl();
		}
		return taskDao;
	}

}
