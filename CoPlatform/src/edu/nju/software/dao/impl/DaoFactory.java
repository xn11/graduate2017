package edu.nju.software.dao.impl;

import edu.nju.software.dao.AdminDao;
import edu.nju.software.dao.CompanyDao;
import edu.nju.software.dao.LogDao;
import edu.nju.software.dao.MemberDao;
import edu.nju.software.dao.NewsDao;
import edu.nju.software.dao.OutEmployeeDao;
import edu.nju.software.dao.ProjectDao;
import edu.nju.software.dao.TaskDao;

public class DaoFactory {
	
	private AdminDao adminDao;
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

	public AdminDao getAdminDao() {
		if(null == adminDao) {
			adminDao = new AdminDaoImpl();
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
