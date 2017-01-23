package edu.nju.software.dao;

import java.util.Date;
import java.util.List;

import edu.nju.software.pojo.Log;

public interface LogDao {
	public List<Log> getByProject(int companyId, int projectId, Date startTime, Date endTime);
	
	public List<Log> getByTask(int companyId, int taskId, Date startTime, Date endTime);
	
	public int create(Log log);
}
