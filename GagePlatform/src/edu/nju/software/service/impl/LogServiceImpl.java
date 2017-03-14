package edu.nju.software.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.dao.LogDao;
import cebbank.gage.model.Log;
import cebbank.gage.model.Project;
import cebbank.gage.model.Task;
import edu.nju.software.service.LogService;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Service
public class LogServiceImpl implements LogService {
	private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	
	@Autowired
	private LogDao logDao;
	
	@Autowired
	private WorkService workService;
	
	@Override
	public GeneralResult<Map<Project, List<Log>>> getProjectLogs(int companyId, Date startTime,
			Date endTime) {
		GeneralResult<Map<Project, List<Log>>> result = new GeneralResult<Map<Project, List<Log>>>();
		GeneralResult<List<Project>> projectResult = workService.getProjectsByCompany(companyId);
		if(projectResult.getResultCode() == ResultCode.NORMAL) {
			Map<Project, List<Log>> projectLogs = new HashMap<Project, List<Log>>();
			for(Project project : projectResult.getData()) {
				List<Log> logList = null;
				try {
					logList = logDao.getByProject(companyId, project.getId(), startTime, endTime);
				}catch(DataAccessException e) {
					logger.error(e.getMessage());
					result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
					result.setMessage(e.getMessage());
				}
				if(null != logList && !logList.isEmpty()) {
					projectLogs.put(project, logList);
				}
			}
			result.setData(projectLogs);
		}else {
			result.setResultCode(projectResult.getResultCode());
			result.setMessage(projectResult.getMessage());
		}

		return result;
	}

	@Override
	public GeneralResult<Map<Task, List<Log>>> getTaskLogs(int companyId, int projectId, Date startTime,
			Date endTime) {
		GeneralResult<Map<Task, List<Log>>> result = new GeneralResult<Map<Task, List<Log>>>();
		GeneralResult<List<Task>> taskResult = workService.getTasksByProject(projectId);
		if(taskResult.getResultCode() == ResultCode.NORMAL) {
			Map<Task, List<Log>> taskLogs = new HashMap<Task, List<Log>>();
			for(Task task : taskResult.getData()) {
				List<Log> logList = null;
				try {
					logList = logDao.getByTask(companyId, task.getId(), startTime, endTime);
				}catch(DataAccessException e) {
					logger.error(e.getMessage());
					result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
					result.setMessage(e.getMessage());
				}
				if(null != logList && !logList.isEmpty()) {
					taskLogs.put(task, logList);
				}
			}
			result.setData(taskLogs);
		}else {
			result.setResultCode(taskResult.getResultCode());
			result.setMessage(taskResult.getMessage());
		}

		return result;
	}

	@Override
	public GeneralResult<Integer> create(Log log) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = logDao.create(log);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}
