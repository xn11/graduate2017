package edu.nju.software.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.pojo.User;
import cebbank.gage.pojo.Log;
import cebbank.gage.pojo.Member;
import cebbank.gage.pojo.OutEmployee;
import cebbank.gage.pojo.Project;
import cebbank.gage.pojo.SystemAdmin;
import cebbank.gage.pojo.Task;
import cebbank.gage.pojo.TaskAssign;
import cebbank.gare.common.EmployeeType;
import cebbank.gare.common.TaskStatus;
import cebbank.gare.dao.LogDao;
import cebbank.gare.dao.ProjectDao;
import cebbank.gare.dao.TaskDao;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;
import edu.nju.software.util.UserInfoStorage;
import edu.nju.software.wechat.WechatBroadcast;

@Service
public class WorkServiceImpl implements WorkService {
	private static final String COMPANY_PROJECT_CACHE_KEY_FORMAT = "company_project_cache_key_%d";

	private static final String PROJECT_CACHE_KEY_FOMAT = "project_%d";

	private static final String PROJECT_TASK_CACHE_KEY_FOMAT = "projct_task_%d";

	private static final String TASK_CACHE_KEY_FORMAT = "task_%d";

	private static final String TASK_PATH_SEPARATOR = "_";

	private static Logger logger = LoggerFactory
			.getLogger(WorkServiceImpl.class);

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private LogDao logDao;
	
	@Autowired
	private WechatBroadcast wechatBroadcast;

	@Override
	public GeneralResult<Project> getProjectById(int id) {
		GeneralResult<Project> result = new GeneralResult<Project>();
		Project project = (Project) CoCacheManager.get(String.format(
				PROJECT_CACHE_KEY_FOMAT, id));
		if (null != project) {
			result.setData(project);
		} else {
			try {
				project = projectDao.getById(id);
				result.setData(project);
				CoCacheManager.put(String.format(PROJECT_CACHE_KEY_FOMAT, id),
						project);
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Integer> createProject(Project project) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = projectDao.create(project);
			result.setData(outId);
			long time = System.currentTimeMillis();
			Date date = new Date(time);
			Log log = new Log("创建项目" + project.getName(), "创建项目"
					+ project.getName(), project, null, 1, 1, date,
					project.getCompanyId(), 1, 1);
			logDao.create(log);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}

		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY_FORMAT,
				project.getCompanyId()));
		return result;
	}

	@Override
	public NoDataResult updateProject(Project project) {
		NoDataResult result = new NoDataResult();
		try {
			projectDao.update(project);
			long time = System.currentTimeMillis();
			Date date = new Date(time);
			Log log = new Log("修改项目" + project.getName(), "修改项目"
					+ project.getName() + "状态到" + project.getProgress(),
					project, null, 1, 1, date, project.getCompanyId(), 1, 1);
			logDao.create(log);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}

		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY_FORMAT,
				project.getCompanyId()));
		CoCacheManager.remove(String.format(PROJECT_CACHE_KEY_FOMAT,
				project.getId()));
		return result;
	}

	@Transactional
	@Override
	public NoDataResult deleteProject(int projectId) {
		NoDataResult result = new NoDataResult();

		GeneralResult<Project> projectResult = getProjectById(projectId);
		if (projectResult.getResultCode() == ResultCode.NORMAL) {

			long time = System.currentTimeMillis();
			Date date = new Date(time);
			Log log = new Log("删除项目" + projectResult.getData().getName(),
					"删除项目" + projectResult.getData().getName(),
					projectResult.getData(), null, 1, 1, date, projectResult
							.getData().getCompanyId(), 1, 1);
			logDao.create(log);

			CoCacheManager.remove(String.format(
					COMPANY_PROJECT_CACHE_KEY_FORMAT, projectResult.getData()
							.getCompanyId()));
		}

		CoCacheManager
				.remove(String.format(PROJECT_CACHE_KEY_FOMAT, projectId));

		CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT,
				projectId));

		try {

			projectDao.deleteTaskAssign(projectId);
			projectDao.delete(projectId);
			taskDao.deleteAllByProject(projectId);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	@Override
	public GeneralResult<List<Project>> getProjectsByCompany(int companyId) {
		GeneralResult<List<Project>> result = new GeneralResult<List<Project>>();
		@SuppressWarnings("unchecked")
		List<Project> projectList = (List<Project>) CoCacheManager.get(String
				.format(COMPANY_PROJECT_CACHE_KEY_FORMAT, companyId));
		if (null != projectList && !projectList.isEmpty()) {
			result.setData(projectList);
		} else {
			try {
				projectList = projectDao.getByCompany(companyId);
				if (null != projectList && !projectList.isEmpty()) {
					result.setData(projectList);
					CoCacheManager.put(String.format(
							COMPANY_PROJECT_CACHE_KEY_FORMAT, companyId),
							projectList);
				} else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no project data in company, companyId = "
							+ companyId);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Project> getProjectByCompanyAndId(int companyId,
			int projectId) {
		GeneralResult<Project> result = new GeneralResult<Project>();
		GeneralResult<List<Project>> companyProjctResult = getProjectsByCompany(companyId);
		if (companyProjctResult.getResultCode() == ResultCode.NORMAL) {
			for (Project project : companyProjctResult.getData()) {
				if (project.getId() == projectId) {
					result.setData(project);
					break;
				}
			}
			if (null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		} else {
			result.setResultCode(companyProjctResult.getResultCode());
			result.setMessage(companyProjctResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Integer> createTask(Task task) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = taskDao.create(task);
			result.setData(outId);

			long time = System.currentTimeMillis();
			Date date = new Date(time);

			GeneralResult<Project> projectResult = getProjectById(task
					.getProjectId());
			Log log = new Log("创建任务" + task.getName(), "创建任务" + task.getName(),
					projectResult.getData(), task, task.getStatus(),
					task.getStatus(), date, projectResult.getData()
							.getCompanyId(), 1, 1);
			logDao.create(log);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}

		CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT,
				task.getProjectId()));
		return result;
	}

	@Override
	public NoDataResult updateTask(Task task, String sessionId) {
		NoDataResult result = new NoDataResult();

		try {
			int originStatus = 0;
			int creatorId = 0;
			int creatorType = 0;

			Task originTask = taskDao.getById(task.getId());
			originStatus = originTask.getStatus();

			taskDao.update(task);
			long time = System.currentTimeMillis();
			Date date = new Date(time);
			GeneralResult<Project> projectResult = getProjectById(task
					.getProjectId());

			// 获取creatorId和creatorType
			User admin = null;
			SystemAdmin systemAdmin = null;
			Member member = null;
			OutEmployee outEmployee = null;

			if (null != (admin = (User) UserInfoStorage.getAdmin(sessionId))) {
				creatorId = admin.getId();
				creatorType = EmployeeType.ADMIN;
			} else if (null != (systemAdmin = (SystemAdmin) UserInfoStorage
					.getSystemAdmin(sessionId))) {
				creatorId = systemAdmin.getId();
				creatorType = EmployeeType.SYSTEM_ADMIN;
			} else if (null != (member = (Member) UserInfoStorage
					.getMember(sessionId))) {
				creatorId = member.getId();
				creatorType = EmployeeType.MEMBER;
			} else if (null != (outEmployee = (OutEmployee) UserInfoStorage
					.getOutEmployee(sessionId))) {
				creatorId = outEmployee.getId();
				creatorType = EmployeeType.OUT_EMPLOYEE;
			}

			Log log = new Log("修改任务" + task.getName(), "修改任务" + task.getName()
					+ "状态由“" + TaskStatus.getStatusStr(originStatus) + "”到“"
					+ TaskStatus.getStatusStr(task.getStatus()) + "”",
					projectResult.getData(), task, originStatus,
					task.getStatus(), date, projectResult.getData()
							.getCompanyId(), creatorId, creatorType);
			logDao.create(log);

			// 任务状态修改微信推送
			GeneralResult<List<Member>> memberListResult = getRelatedMembers(task
					.getId());
			List<Member> memberList = memberListResult.getData();
			List<String> openIDList = new ArrayList<String>();

			for (Member broadcastMember : memberList) {
				String openID = broadcastMember.getOpenId();
				if (null != openID && !StringUtils.isBlank(openID)) {
					openIDList.add(openID);
				}
			}

			wechatBroadcast.broadcastChanges(openIDList, log);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}

		CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT,
				task.getProjectId()));
		CoCacheManager
				.remove(String.format(TASK_CACHE_KEY_FORMAT, task.getId()));
		return result;
	}

	@Override
	public NoDataResult deleteTask(int taskId) {
		NoDataResult result = new NoDataResult();

		GeneralResult<Task> taskResult = getTaskById(taskId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {

			long time = System.currentTimeMillis();
			Date date = new Date(time);
			Task task = taskResult.getData();
			GeneralResult<Project> projectResult = getProjectById(task
					.getProjectId());

			Log log = new Log("删除任务" + task.getName(), "删除任务" + task.getName(),
					projectResult.getData(), task, task.getStatus(),
					task.getStatus(), date, projectResult.getData()
							.getCompanyId(), 1, 1);
			logDao.create(log);
			CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT,
					taskResult.getData().getProjectId()));
		}
		CoCacheManager.remove(String.format(TASK_CACHE_KEY_FORMAT, taskId));

		try {

			taskDao.deleteTaskAssignByTask(taskId);
			taskDao.delete(taskId);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}

		return result;
	}

	@Override
	public NoDataResult deleteSubTasks(int projectId, int taskId) {
		NoDataResult result = new NoDataResult(ResultCode.NORMAL);
		GeneralResult<List<Task>> taskResult = getTasksByProject(projectId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			for (Task task : taskResult.getData()) {
				if (inPath(task.getPath(), taskId)) {
					NoDataResult deleteResult = deleteTask(task.getId());
					if (deleteResult.getResultCode() != ResultCode.NORMAL) {
						result.setResultCode(deleteResult.getResultCode());
					}
				}
			}
		}
		return result;
	}

	@Override
	public GeneralResult<List<Task>> getTasksByProject(int projectId) {
		GeneralResult<List<Task>> result = new GeneralResult<List<Task>>();
		@SuppressWarnings("unchecked")
		List<Task> taskList = (List<Task>) CoCacheManager.get(String.format(
				PROJECT_TASK_CACHE_KEY_FOMAT, projectId));
		if (null != taskList && !taskList.isEmpty()) {
			result.setData(taskList);
		} else {
			try {
				taskList = taskDao.getByProject(projectId);
				if (null != taskList && !taskList.isEmpty()) {
					result.setData(taskList);
					CoCacheManager.put(String.format(
							PROJECT_TASK_CACHE_KEY_FOMAT, projectId), taskList);
				} else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no task data in project, projectId = "
							+ projectId);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Task> getTaskByProjectAndId(int projectId, int taskId) {
		GeneralResult<Task> result = new GeneralResult<Task>();
		GeneralResult<List<Task>> projectTaskResult = getTasksByProject(projectId);
		if (projectTaskResult.getResultCode() == ResultCode.NORMAL) {
			for (Task task : projectTaskResult.getData()) {
				if (task.getId() == taskId) {
					result.setData(task);
					break;
				}
			}
			if (null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		} else {
			result.setResultCode(projectTaskResult.getResultCode());
			result.setMessage(projectTaskResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Task> getTaskById(int id) {
		GeneralResult<Task> result = new GeneralResult<Task>();
		Task task = (Task) CoCacheManager.get(String.format(
				TASK_CACHE_KEY_FORMAT, id));
		if (null != task) {
			result.setData(task);
		} else {
			try {
				task = taskDao.getById(id);
				result.setData(task);
				CoCacheManager.put(String.format(TASK_CACHE_KEY_FORMAT, id),
						task);
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public NoDataResult assignTaskToMember(int taskId, int memberId) {
		NoDataResult result = new NoDataResult();
		if (!checkTaskAssigned(taskId, memberId, 0, 0)) {
			try {
				taskDao.assignTask(new TaskAssign(taskId, memberId, 0));
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
				result.setMessage(e.getMessage());
			}
		} else {
			result.setResultCode(ResultCode.E_TASK_ASSIGNED);
			result.setMessage("task is assigned");
		}
		return result;
	}

	@Override
	public NoDataResult assignTaskToOutEmployee(int taskId, int outEmployeeId,
			int companyId) {
		NoDataResult result = new NoDataResult();
		if (!checkTaskAssigned(taskId, 0, outEmployeeId, companyId)) {
			try {
				taskDao.assignTask(new TaskAssign(taskId, 0, outEmployeeId));
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
				result.setMessage(e.getMessage());
			}
		} else {
			result.setResultCode(ResultCode.E_TASK_ASSIGNED);
			result.setMessage("task is assigned");
		}
		return result;
	}

	private boolean checkTaskAssigned(int taskId, int memberId,
			int outEmployeeId, int companyId) {
		if ((taskId <= 0) || (memberId <= 0 && outEmployeeId <= 0)) {
			throw new IllegalArgumentException();
		}

		Task task = taskDao.getById(taskId);
		if (null == task) {
			throw new IllegalArgumentException();
		}

		List<Task> taskList = null;
		if (memberId > 0) {
			taskList = taskDao.getTasksByMember(memberId);
		} else {
			taskList = taskDao.getTasksByOutEmployee(companyId, outEmployeeId);
		}

		for (Task taskItem : taskList) {
			if (taskItem.getId() == taskId) {
				return true;
			}
		}
		return false;
	}

	@Override
	public GeneralResult<List<Task>> getTasksWithChildrenByProject(int projectId) {
		GeneralResult<List<Task>> result = new GeneralResult<List<Task>>();
		try {
			List<Task> taskList = taskDao
					.getTasksWithChildrenByProject(projectId);
			if (null != taskList && !taskList.isEmpty()) {
				result.setData(taskList);
			} else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no tasks, projectId: " + projectId);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<List<Member>> getRelatedMembers(int taskId) {
		GeneralResult<List<Member>> result = new GeneralResult<List<Member>>();
		try {
			List<Member> memberList = taskDao.getRelatedMembers(taskId);
			if (null != memberList && !memberList.isEmpty()) {
				result.setData(memberList);
			} else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no related member, memberId: " + taskId);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private HashMap convertToTaskForest(LinkedHashMap taskTree) {
		HashMap taskForest = new HashMap();
		for (Iterator iterator = taskTree.keySet().iterator(); iterator
				.hasNext();) {
			Object key = (Integer) iterator.next();
			Task task = (Task) taskTree.get(key);
			if (0 == task.getParentId()) {
				System.out.println("root task: " + task.getName());
				taskForest.put(task, new LinkedHashMap());
			} else {
				int parentId = task.getParentId();
				Task parent = (Task) taskTree.get(parentId);
				System.out.println("child task: " + task.getName());
				System.out.println("parent task: " + parent.getName());
				LinkedHashMap parentValue = (LinkedHashMap) findNode(
						taskForest, parent);
				if (null != parentValue) {
					parentValue.put(task, new LinkedHashMap());
				}

			}
		}
		return taskForest;
	}

	@SuppressWarnings("rawtypes")
	private HashMap findNode(HashMap taskTree, Task task) {
		if (null == taskTree || taskTree.isEmpty() || null == task) {
			return null;
		}

		if (taskTree.keySet().contains(task)) {
			return (HashMap) taskTree.get(task);
		} else {
			for (Iterator iterator = taskTree.keySet().iterator(); iterator
					.hasNext();) {
				Task key = (Task) iterator.next();
				HashMap value = (HashMap) taskTree.get(key);
				HashMap result = findNode(value, task);
				if (result != null) {
					return result;
				}
			}
			return null;
		}
	}

	private boolean inPath(String path, int id) {
		if (StringUtils.isBlank(path) || id == 0) {
			return false;
		}
		String[] nodes = path.split(TASK_PATH_SEPARATOR);
		if (nodes.length == 0) {
			return false;
		}

		String idStr = String.valueOf(id);
		for (String node : nodes) {
			if (node.equals(idStr)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public NoDataResult deleteAssignToMmeber(int taskId, int memberId) {
		NoDataResult result = new NoDataResult();
		try {
			taskDao.deleteTaskAssign(taskId, memberId, null);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult deleteAssignToOutEmployee(int taskId, int outEmployeeId) {
		NoDataResult result = new NoDataResult();
		try {
			taskDao.deleteTaskAssign(taskId, null, outEmployeeId);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}
