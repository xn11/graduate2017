package edu.nju.software.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Admin;
import edu.nju.software.service.AdminService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;
import edu.nju.software.util.UserInfoStorage;

@Controller
public class LoginController {

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = { "/", "/Login" }, method = RequestMethod.GET)
	public ModelAndView loginView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("login", null);
	}

	@RequestMapping(value = { "/Login" }, method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(mail) || StringUtils.isBlank(password)) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

		mail = mail.trim();
		password = password.trim();
		GeneralResult<Admin> adminResult = adminService.getByMailAndPassword(
				mail, password);
		if (adminResult.getResultCode() == ResultCode.NORMAL) {
			Admin admin = adminResult.getData();
			// for normal environment
			/*
			 * Gson gson = new Gson(); CoUtils.addCookie(response,
			 * "currentAdmin", URLEncoder.encode(gson.toJson(admin), "UTF-8"),
			 * 3600);
			 */
			String sessionId = request.getSession(true).getId();
			UserInfoStorage.putAdmin(sessionId, admin);
			response.sendRedirect(request.getContextPath() + "/"
					+ "MemberList?companyId="
					+ adminResult.getData().getCompanyId());
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

	}

	@RequestMapping(value = { "/Logout", "/logout" })
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Cookie cookie = new Cookie("currentAdmin", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		response.sendRedirect(request.getContextPath() + "/Login");
		return;
	}
}
