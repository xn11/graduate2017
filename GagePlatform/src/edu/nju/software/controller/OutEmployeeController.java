package edu.nju.software.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cebbank.gage.pojo.OutEmployee;
import cebbank.gage.pojo.Task;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.util.CoHashMap;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralJsonResult;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class OutEmployeeController {

	@Autowired
	private OutEmployeeService outEmployeeService;

	@RequestMapping(value = { "/OutEmployeeList" }, method = RequestMethod.GET)
	public ModelAndView outEmployeeList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<OutEmployee>> outEmployeeResult = outEmployeeService
				.getByCompany(companyId);
		if (outEmployeeResult.getResultCode() == ResultCode.NORMAL) {
			model.put("outEmployees", outEmployeeResult.getData());
		}

		return new ModelAndView("outEmployeeList", "model", model);
	}

	@RequestMapping(value = { "/GetOutEmployeeList" }, method = RequestMethod.GET)
	@ResponseBody
	public GeneralJsonResult<List<OutEmployee>> getOutEmployeeList(
			HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		GeneralResult<List<OutEmployee>> outEmployeResult = outEmployeeService
				.getByCompany(companyId);
		return new GeneralJsonResult<List<OutEmployee>>(outEmployeResult);
	}

	@RequestMapping(value = { "/GetOutEmployeeInfo" }, method = RequestMethod.GET)
	public ModelAndView getOutEmployeeInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int outEmployeeId = CoUtils.getRequestIntValue(request,
				"outEmployeeId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<OutEmployee> outEmployeeResult = outEmployeeService
				.getById(outEmployeeId);
		if (outEmployeeResult.getResultCode() == ResultCode.NORMAL) {
			model.put("outEmployee", outEmployeeResult.getData());
		}
		return new ModelAndView("outEmployeeInfo", "model", model);
	}

	@RequestMapping(value = { "/GetOutEmployeeTasks" }, method = RequestMethod.GET)
	public ModelAndView memberTaskList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		int outEmployeeId = CoUtils.getRequestIntValue(request,
				"outEmployeeId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<Task>> taskResult = outEmployeeService.getTasks(
				companyId, outEmployeeId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("tasks", taskResult.getData());
		}
		return new ModelAndView("taskList", "model", model);
	}
}
