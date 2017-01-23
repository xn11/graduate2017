package edu.nju.software.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.OutEmployee;
import edu.nju.software.pojo.Task;
import edu.nju.software.service.CompanyService;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;
import edu.nju.software.util.UserInfoStorage;

@Controller
public class WeChatTaskController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(WorkController.class);

	@SuppressWarnings("unused")
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private MemberService memberService;

	@Autowired
	private OutEmployeeService outEmployeeService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private WorkService workService;

	@RequestMapping(value = { "/wechat/MyTasks" }, method = RequestMethod.GET)
	public ModelAndView wxMemberTaskList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String openId = request.getParameter("openId");
		if (StringUtils.isBlank(openId)) {
			response.sendRedirect(request.getContextPath() + "/Error");
		}

		GeneralResult<Member> memberResult = memberService.getByOpenId(openId);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			Member member = memberResult.getData();
			String sessionId = request.getSession(true).getId();
			UserInfoStorage.putMember(sessionId, member);

			Map<String, Object> model = new HashMap<String, Object>();
			GeneralResult<List<Task>> taskResult = memberService
					.getTasks(member.getId());
			if (taskResult.getResultCode() == ResultCode.NORMAL) {
				model.put("openId", openId);
				model.put("tasks", taskResult.getData());
			}
			return new ModelAndView("wechat/taskList", "model", model);
		} else {
			// 外聘人员
			GeneralResult<OutEmployee> outEmployeeResult = outEmployeeService
					.getByOpenId(openId);
			if (outEmployeeResult.getResultCode() == ResultCode.NORMAL) {
				OutEmployee outEmployee = outEmployeeResult.getData();
				String sessionId = request.getSession(true).getId();
				UserInfoStorage.putOutEmployee(sessionId, outEmployee);

				Map<String, Object> model = new HashMap<String, Object>();

				// 获取外聘人员在各个公司参与的项目
				GeneralResult<List<Company>> companyListResult = companyService
						.getAll();
				List<Company> companyList = companyListResult.getData();
				List<Task> tasksList = new ArrayList<Task>();

				for (Company company : companyList) {
					GeneralResult<List<Task>> taskResult = outEmployeeService
							.getTasks(company.getId(), outEmployee.getId());

					if (taskResult.getResultCode() == ResultCode.NORMAL) {
						tasksList.addAll(taskResult.getData());
					}
				}

				model.put("openId", openId);
				model.put("tasks", tasksList);
				return new ModelAndView("wechat/taskList", "model", model);
			} else {
				response.sendRedirect(request.getContextPath()
						+ "/wechat?openId=" + openId);
				return null;
			}
		}

	}

	// TODO
	@RequestMapping(value = { "/wechat/TaskInfo" }, method = RequestMethod.GET)
	public ModelAndView wxTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);

		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Task> taskResult = null;
		taskResult = workService.getTaskById(taskId);

		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("wxtask", taskResult.getData());
		}
		return new ModelAndView("wechat/taskInfo", "model", model);
	}

	@RequestMapping(value = { "/wechat/UpdateTask" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult wxUpdateTask(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int status = CoUtils.getRequestIntValue(request, "status", true);

		GeneralResult<Task> taskResult = workService.getTaskById(taskId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			Task task = taskResult.getData();
			task.setStatus(status);
			NoDataResult result = workService.updateTask(task, request
					.getSession(true).getId());
			return new NoDataJsonResult(result);
		} else {
			return new NoDataJsonResult(taskResult);
		}

	}

}
