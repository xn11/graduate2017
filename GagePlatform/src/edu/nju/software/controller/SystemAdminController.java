package edu.nju.software.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cebbank.gage.pojo.Company;
import cebbank.gage.pojo.SystemAdmin;
import edu.nju.software.service.CompanyService;
import edu.nju.software.service.SystemAdminService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;
import edu.nju.software.util.UserInfoStorage;

@Controller
public class SystemAdminController {

	@Autowired
	private SystemAdminService systemAdminService;
	@Autowired
	private CompanyService companyService;

	@RequestMapping(value = "/System/Login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("system/login");
	}

	@RequestMapping(value = "/System/Login", method = RequestMethod.POST)
	public void loginPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			response.sendRedirect(request.getContextPath() + "/System/Login");
			return;
		}

		GeneralResult<SystemAdmin> systemAdminResult = systemAdminService
				.getByUserNameAndPassword(userName, password);
		if (systemAdminResult.getResultCode() == ResultCode.NORMAL) {
			UserInfoStorage.putSystemAdmin(request.getSession(true).getId(),
					systemAdminResult.getData());
			response.sendRedirect(request.getContextPath()
					+ "/System/CompanyList");
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/System/Login");
			return;
		}
	}

	@RequestMapping(value = "/System/CompanyList", method = RequestMethod.GET)
	public ModelAndView memberList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Company>> companyResult = companyService.getAll();
		if (companyResult.getResultCode() == ResultCode.NORMAL) {
			model.put("companys", companyResult.getData());
		}
		return new ModelAndView("system/companyList", "model", model);
	}
}
