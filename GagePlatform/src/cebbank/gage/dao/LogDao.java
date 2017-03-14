package cebbank.gage.dao;

import java.util.Date;
import java.util.List;

import cebbank.gage.model.Log;

public interface LogDao {
	public List<Log> getByProject(int companyId, int projectId, Date startTime, Date endTime);
	
	public List<Log> getByTask(int companyId, int taskId, Date startTime, Date endTime);
	
	public int create(Log log);
}
