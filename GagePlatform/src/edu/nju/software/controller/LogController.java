package edu.nju.software.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cebbank.gage.pojo.Log;
import cebbank.gage.pojo.Project;
import cebbank.gage.pojo.Task;
import edu.nju.software.service.LogService;
import edu.nju.software.util.CoHashMap;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class LogController {

	@Autowired
	private LogService logService;

	@RequestMapping(value = { "/ProjectLogList" }, method = RequestMethod.GET)
	public ModelAndView getProjectLogList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		Map<String, Object> model = new CoHashMap(request);
		// startTime and endTime set null for test
		GeneralResult<Map<Project, List<Log>>> projectLogResult = logService
				.getProjectLogs(companyId, null, null);
		if (projectLogResult.getResultCode() == ResultCode.NORMAL) {
			model.put("projectLogs", projectLogResult.getData());
		}

		return new ModelAndView("projectLogList", "model", model);
	}

	@RequestMapping(value = { "/TaskLogList" }, method = RequestMethod.GET)
	public ModelAndView getTaskLogList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);

		Map<String, Object> model = new CoHashMap(request);
		// startTime and endTime set null for test
		GeneralResult<Map<Task, List<Log>>> taskLogResult = logService
				.getTaskLogs(companyId, projectId, null, null);
		if (taskLogResult.getResultCode() == ResultCode.NORMAL) {
			model.put("taskLogs", taskLogResult.getData());
		}

		return new ModelAndView("taskLogList", "model", model);
	}

}
