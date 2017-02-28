package cebbank.gare.dao;

import java.util.List;

import cebbank.gage.pojo.Member;
import cebbank.gage.pojo.Task;
import cebbank.gage.pojo.TaskAssign;

public interface TaskDao {
	public int create(Task task);
	
	public void update(Task task);
	
	public void delete(int taskId);
	
	public void deleteAllByProject(int projectId);
	
	public List<Task> getByProject(int projectId);
	
	public Task getById(int id);
	
	public void assignTask(TaskAssign taskAssign);
	
	public List<Task> getTasksByMember(int memberId);
	
	public List<Task> getTasksByOutEmployee(int companyId, int outEmployeeId);
	
	public void deleteTaskAssignByTask(int taskId);
	
	public void deleteTaskAssign(int taskId, Integer memberId, Integer outEmployeeId);
	
	public List<Task> getTasksWithChildrenByProject(int projectId);
	
	public List<Member> getRelatedMembers(int taskId);
	
}
