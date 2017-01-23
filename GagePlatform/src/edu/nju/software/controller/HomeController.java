package edu.nju.software.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.service.AdminService;

@Controller
public class HomeController {

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = { "/Error", "/error" }, method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("error", null);
	}
}
