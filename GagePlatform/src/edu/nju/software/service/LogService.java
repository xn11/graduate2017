package edu.nju.software.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cebbank.gage.pojo.Log;
import cebbank.gage.pojo.Project;
import cebbank.gage.pojo.Task;
import edu.nju.software.util.GeneralResult;

public interface LogService {
	
	public GeneralResult<Map<Project, List<Log>>> getProjectLogs(int companyId, Date startTime, Date endTime);
	
	public GeneralResult<Map<Task, List<Log>>> getTaskLogs(int companyId, int projectId, Date startTime, Date endTime);
	
	public GeneralResult<Integer> create(Log log);
	
}
