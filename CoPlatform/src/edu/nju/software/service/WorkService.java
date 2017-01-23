package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface WorkService {
	public GeneralResult<List<Project>> getProjectsByCompany(int companyId);

	public GeneralResult<Project> getProjectByCompanyAndId(int companyId,
			int projectId);

	public GeneralResult<Project> getProjectById(int id);

	public GeneralResult<Integer> createProject(Project project);

	public NoDataResult updateProject(Project project);

	public NoDataResult deleteProject(int projectId);

	public GeneralResult<Integer> createTask(Task task);

	public NoDataResult updateTask(Task task, String sessionId);

	public NoDataResult deleteTask(int taskId);

	public NoDataResult deleteSubTasks(int projectId, int taskId);

	public GeneralResult<List<Task>> getTasksByProject(int projectId);

	public GeneralResult<Task> getTaskByProjectAndId(int projectId, int taskId);

	public GeneralResult<Task> getTaskById(int id);

	public NoDataResult assignTaskToMember(int taskId, int memberId);

	public NoDataResult assignTaskToOutEmployee(int taskId, int outEmployeeId,
			int companyId);

	public NoDataResult deleteAssignToMmeber(int taskId, int memberId);

	public NoDataResult deleteAssignToOutEmployee(int taskId, int outEmployeeId);

	public GeneralResult<List<Task>> getTasksWithChildrenByProject(int projectId);

	public GeneralResult<List<Member>> getRelatedMembers(int taskId);
}
