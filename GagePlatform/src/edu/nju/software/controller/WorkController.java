package edu.nju.software.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cebbank.gage.model.Company;
import cebbank.gage.model.Member;
import cebbank.gage.model.Project;
import cebbank.gage.model.Task;
import edu.nju.software.service.CompanyService;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoHashMap;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralJsonResult;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WorkController {

	private static Logger logger = LoggerFactory
			.getLogger(WorkController.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final int TASK_DEPTH_STEP = 1;

	private static final String TASK_PATH_SEPARATOR = "_";

	@Autowired
	private CompanyService companyService;
	@Autowired
	private WorkService workService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OutEmployeeService outEmployeeService;

	@RequestMapping(value = { "/GetProjectInfo" }, method = RequestMethod.GET)
	public ModelAndView getProjectInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", false);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<Project> projectResult = null;
		if (companyId == 0) {
			projectResult = workService.getProjectById(projectId);
		} else {
			projectResult = workService.getProjectByCompanyAndId(companyId,
					projectId);
		}

		if (projectResult.getResultCode() == ResultCode.NORMAL) {
			model.put("project", projectResult.getData());
		}
		return new ModelAndView("projectInfo", "model", model);
	}

	@RequestMapping(value = { "/WorkList" }, method = RequestMethod.GET)
	public ModelAndView getWorkList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<Project>> projectResult = workService
				.getProjectsByCompany(companyId);
		if (projectResult.getResultCode() == ResultCode.NORMAL) {
			Map<Project, List<Task>> workMap = new HashMap<Project, List<Task>>();
			for (Project project : projectResult.getData()) {
				if (null == project) {
					continue;
				}
				GeneralResult<List<Task>> taskResult = workService
						.getTasksByProject(project.getId());
				if (taskResult.getResultCode() == ResultCode.NORMAL) {
					workMap.put(project, taskResult.getData());
				} else {
					workMap.put(project, null);
				}
			}
			model.put("works", workMap);
		}

		return new ModelAndView("workList", "model", model);
	}

	@RequestMapping(value = { "/ProjectList" }, method = RequestMethod.GET)
	public ModelAndView getProjectList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<Project>> projectResult = workService
				.getProjectsByCompany(companyId);
		if (projectResult.getResultCode() == ResultCode.NORMAL) {
			model.put("projects", projectResult.getData());
		}

		return new ModelAndView("projectList", "model", model);
	}

	@RequestMapping(value = { "/UpdateProject" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateProject(HttpServletRequest request,
			HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		String name = request.getParameter("name");
		String description = request.getParameter("description");

		String startDateStr = request.getParameter("startDate").trim();
		String startTimeStr = request.getParameter("startTime").trim();
		String endDateStr = request.getParameter("endDate").trim();
		String endTimeStr = request.getParameter("endTime").trim();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = CoUtils.parseDate(startDateStr + " " + startTimeStr,
					DATE_FORMAT);
			endDate = CoUtils.parseDate(endDateStr + " " + endTimeStr,
					DATE_FORMAT);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new NoDataJsonResult(ResultCode.E_OTHER_ERROR, null);
		}

		double progress = CoUtils.getRequestDoubleValue(request, "progress",
				true);
		if (null != name) {
			name = name.trim();
		}
		if (null != description) {
			description = description.trim();
		}

		Project project = new Project(projectId, companyId, name, description,
				startDate, endDate, progress);

		NoDataResult result = workService.updateProject(project);
		return new NoDataJsonResult(result);
	}

	@RequestMapping(value = { "/DeleteProject" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteProject(HttpServletRequest request,
			HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		NoDataResult result = workService.deleteProject(projectId);
		return new NoDataJsonResult(result);
	}

	@RequestMapping(value = { "/TaskList" }, method = RequestMethod.GET)
	public ModelAndView getTaskList(HttpServletRequest request,
			HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<Task>> taskResult = workService
				.getTasksByProject(projectId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("tasks", taskResult.getData());
		}

		return new ModelAndView("taskList", "model", model);
	}

	@RequestMapping(value = { "/GetTaskInfo" }, method = RequestMethod.GET)
	public ModelAndView getTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", false);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<Task> taskResult = null;
		if (projectId == 0) {
			taskResult = workService.getTaskById(taskId);
		} else {
			taskResult = workService.getTaskByProjectAndId(projectId, taskId);
		}

		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("task", taskResult.getData());
		}

		GeneralResult<List<Member>> memberResult = workService
				.getRelatedMembers(taskId);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("members", memberResult.getData());
		}
		return new ModelAndView("taskInfo", "model", model);
	}

	@RequestMapping(value = { "/UpdateTask" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateTask(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);

		GeneralResult<Task> taskResult = workService.getTaskById(taskId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {

			String name = request.getParameter("name");
			String description = request.getParameter("description");

			String startDateStr = request.getParameter("startDate").trim();
			String startTimeStr = request.getParameter("startTime").trim();
			String endDateStr = request.getParameter("endDate").trim();
			String endTimeStr = request.getParameter("endTime").trim();
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = CoUtils.parseDate(
						startDateStr + " " + startTimeStr, DATE_FORMAT);
				endDate = CoUtils.parseDate(endDateStr + " " + endTimeStr,
						DATE_FORMAT);
			} catch (ParseException e) {
				logger.error(e.getMessage());
				return new NoDataJsonResult(ResultCode.E_OTHER_ERROR, null);
			}

			int status = CoUtils.getRequestIntValue(request, "status", true);

			if (null != name) {
				name = name.trim();
			}
			if (null != description) {
				description = description.trim();
			}

			int depth = CoUtils.getRequestIntValue(request, "depth", false);
			if (depth == 0) {
				depth += TASK_DEPTH_STEP;
			}

			String path = request.getParameter("path");
			if (null != path) {
				path = path.trim();
			}

			Task task = new Task(taskId, projectId, name, description, 0,
					status, depth, startDate, endDate, path, taskResult
							.getData().getIsLeaf());

			NoDataResult result = workService.updateTask(task, request
					.getSession(true).getId());
			return new NoDataJsonResult(result);
		} else {
			return new NoDataJsonResult(taskResult);
		}
	}

	@RequestMapping(value = { "/DeleteTask" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteTask(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		GeneralResult<Task> taskResult = workService.getTaskById(taskId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			NoDataResult deleteResult = workService.deleteTask(taskId);
			NoDataResult deleteSubResult = workService.deleteSubTasks(
					taskResult.getData().getProjectId(), taskId);
			if (deleteResult.getResultCode() != ResultCode.NORMAL) {
				return new NoDataJsonResult(deleteResult);
			}
			if (deleteSubResult.getResultCode() != ResultCode.NORMAL) {
				return new NoDataJsonResult(deleteSubResult);
			}
			return new NoDataJsonResult(deleteResult);
		} else {
			return new NoDataJsonResult(taskResult);
		}

	}

	@RequestMapping(value = { "/MemberTaskAssign" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult memberTaskAssign(HttpServletRequest request,
			HttpServletResponse response) {
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);

		NoDataResult taskAssignResult = workService.assignTaskToMember(taskId,
				memberId);
		return new NoDataJsonResult(taskAssignResult);
	}

	@RequestMapping(value = { "/OutEmployeeTaskAssign" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult outEmployeeTaskAssign(HttpServletRequest request,
			HttpServletResponse response) {
		int outEmployeeId = CoUtils.getRequestIntValue(request,
				"outEmployeeId", true);
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		NoDataResult taskAssignResult = workService.assignTaskToOutEmployee(
				taskId, outEmployeeId, companyId);
		return new NoDataJsonResult(taskAssignResult);
	}

	@RequestMapping(value = { "/CreateProject" }, method = RequestMethod.GET)
	public ModelAndView createProjectGet(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		GeneralResult<Company> companyResult = companyService
				.getById(companyId);
		Project project = new Project();
		if (companyResult.getResultCode() == ResultCode.NORMAL) {
			project.setCompanyId(companyId);
		} else {
			throw new IllegalArgumentException();
		}

		Map<String, Object> model = new CoHashMap(request);
		model.put("project", project);
		return new ModelAndView("projectInfo", "model", model);
	}

	@RequestMapping(value = { "/CreateProject" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult createProjectPost(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		String name = request.getParameter("name");
		String description = request.getParameter("description");

		String startDateStr = request.getParameter("startDate").trim();
		String startTimeStr = request.getParameter("startTime").trim();
		String endDateStr = request.getParameter("endDate").trim();
		String endTimeStr = request.getParameter("endTime").trim();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = CoUtils.parseDate(startDateStr + " " + startTimeStr,
					DATE_FORMAT);
			endDate = CoUtils.parseDate(endDateStr + " " + endTimeStr,
					DATE_FORMAT);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new NoDataJsonResult(ResultCode.E_OTHER_ERROR, null);
		}

		double progress = 0.0;
		if (null != name) {
			name = name.trim();
		}
		if (null != description) {
			description = description.trim();
		}

		Project project = new Project(companyId, name, description, startDate,
				endDate, progress);

		GeneralResult<Integer> createResult = workService
				.createProject(project);
		return new NoDataJsonResult(createResult);
	}

	@RequestMapping(value = { "/CreateTask" }, method = RequestMethod.GET)
	public ModelAndView createTaskGet(HttpServletRequest request,
			HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", false);
		int parentId = CoUtils.getRequestIntValue(request, "parentId", false);

		GeneralResult<Project> projectResult = workService
				.getProjectById(projectId);
		if (companyId != 0) {
			projectResult = workService.getProjectByCompanyAndId(companyId,
					projectId);
		} else {
			projectResult = workService.getProjectById(projectId);
		}

		Task task = new Task();
		task.setParentId(parentId);
		if (projectResult.getResultCode() == ResultCode.NORMAL) {
			task.setProjectId(projectId);
		} else {
			throw new IllegalArgumentException();
		}

		Map<String, Object> model = new CoHashMap(request);
		model.put("task", task);
		if (parentId == 0) {
			return new ModelAndView("taskInfo", "model", model);
		} else {
			return new ModelAndView("subTaskInfo", "model", model);
		}

	}

	@RequestMapping(value = { "/CreateTask" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult createTaskPost(HttpServletRequest request,
			HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);

		String name = request.getParameter("name");
		String description = request.getParameter("description");

		String startDateStr = request.getParameter("startDate").trim();
		String startTimeStr = request.getParameter("startTime").trim();
		String endDateStr = request.getParameter("endDate").trim();
		String endTimeStr = request.getParameter("endTime").trim();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = CoUtils.parseDate(startDateStr + " " + startTimeStr,
					DATE_FORMAT);
			endDate = CoUtils.parseDate(endDateStr + " " + endTimeStr,
					DATE_FORMAT);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new NoDataJsonResult(ResultCode.E_OTHER_ERROR, null);
		}

		int status = CoUtils.getRequestIntValue(request, "status", true);

		if (null != name) {
			name = name.trim();
		}
		if (null != description) {
			description = description.trim();
		}

		int depth = CoUtils.getRequestIntValue(request, "depth", false);
		if (depth == 0) {
			depth += TASK_DEPTH_STEP;
		}

		String path = null;
		int parentId = CoUtils.getRequestIntValue(request, "parentId", false);
		// root task
		if (parentId != 0) {
			GeneralResult<Task> taskResult = workService.getTaskByProjectAndId(
					projectId, parentId);
			if (taskResult.getResultCode() == ResultCode.NORMAL) {
				Task task = taskResult.getData();
				depth = task.getDepth() + TASK_DEPTH_STEP;
				path = (null == task.getPath() ? "" : task.getPath())
						+ TASK_PATH_SEPARATOR + task.getId();
			}
		}

		Task task = new Task(projectId, name, description, parentId, status,
				depth, startDate, endDate, path, true);

		GeneralResult<Integer> result = workService.createTask(task);
		return new NoDataJsonResult(result);
	}

	@RequestMapping(value = { "/GetTaskTree" }, method = RequestMethod.GET)
	@ResponseBody
	public GeneralJsonResult<List<Task>> getTaskTree(
			HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		GeneralResult<List<Task>> taskResult = workService
				.getTasksWithChildrenByProject(projectId);
		return new GeneralJsonResult<List<Task>>(taskResult);
	}

	@RequestMapping(value = { "/TaskTree" }, method = RequestMethod.GET)
	public ModelAndView taskTree(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		HashMap<String, Object> model = new CoHashMap(request);
		GeneralResult<List<Project>> projectResult = workService
				.getProjectsByCompany(companyId);
		if (projectResult.getResultCode() == ResultCode.NORMAL) {
			model.put("projects", projectResult.getData());
		}
		return new ModelAndView("taskTree", "model", model);
	}

	@RequestMapping(value = { "/TaskDetail" }, method = RequestMethod.GET)
	public ModelAndView taskDetail(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", false);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<Task> taskResult = null;
		if (projectId == 0) {
			taskResult = workService.getTaskById(taskId);
		} else {
			taskResult = workService.getTaskByProjectAndId(projectId, taskId);
		}

		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("task", taskResult.getData());
		}

		GeneralResult<List<Member>> memberResult = workService
				.getRelatedMembers(taskId);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("members", memberResult.getData());
		}
		return new ModelAndView("taskDetail", "model", model);
	}

	@RequestMapping(value = "/DeleteAssignToMember", method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteAssignToMember(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);

		NoDataResult result = workService
				.deleteAssignToMmeber(taskId, memberId);
		return new NoDataJsonResult(result);
	}

	@RequestMapping(value = "/DeleteAssignToOutEmployee", method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteAssignToOutEmployee(
			HttpServletRequest request, HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int outEmployeeId = CoUtils.getRequestIntValue(request,
				"outEmployeeId", true);

		NoDataResult result = workService.deleteAssignToOutEmployee(taskId,
				outEmployeeId);
		return new NoDataJsonResult(result);
	}
}
