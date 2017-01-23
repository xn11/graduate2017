package edu.nju.software.controller;

import java.io.UnsupportedEncodingException;
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

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Task;
import edu.nju.software.service.CompanyService;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.CoHashMap;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralJsonResult;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private CompanyService companyService;

	@RequestMapping(value = { "/MemberList" }, method = RequestMethod.GET)
	public ModelAndView memberList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<Member>> memberResult = memberService
				.getAllByCompany(companyId);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("members", memberResult.getData());
		}

		return new ModelAndView("memberList", "model", model);
	}

	@RequestMapping(value = { "/GetMemberList" }, method = RequestMethod.GET)
	@ResponseBody
	public GeneralJsonResult<List<Member>> getMemberList(
			HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		GeneralResult<List<Member>> memberResult = memberService
				.getAllByCompany(companyId);
		return new GeneralJsonResult<List<Member>>(memberResult);
	}

	@RequestMapping(value = { "/GetMemberInfo" }, method = RequestMethod.GET)
	public ModelAndView getMemberInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<Member> memberResult = memberService.getById(memberId);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("member", memberResult.getData());
		}
		return new ModelAndView("memberInfo", "model", model);
	}

	@RequestMapping(value = { "/UpdateMember" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateMember(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		int id = CoUtils.getRequestIntValue(request, "memberId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		String name = request.getParameter("name");
		String workId = request.getParameter("workId");
		String qqNumber = request.getParameter("qqNumber");
		String wxNumber = request.getParameter("wxNumber");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");

		if (null != name) {
			name = name.trim();
		}
		if (null != workId) {
			workId = workId.trim();
		}
		if (null != qqNumber) {
			qqNumber = qqNumber.trim();
		}
		if (null != wxNumber) {
			wxNumber = wxNumber.trim();
		}
		if (null != phone) {
			phone = phone.trim();
		}
		if (null != password) {
			password = password.trim();
		}

		Member member = new Member(id, name, companyId, workId, password,
				qqNumber, wxNumber, phone, null);

		NoDataResult result = memberService.update(member);
		return new NoDataJsonResult(result);
	}

	@RequestMapping(value = { "/DeleteMember" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteMember(HttpServletRequest request,
			HttpServletResponse response) {
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);
		GeneralResult<Member> memberResult = memberService.getById(memberId);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			NoDataResult result = memberService.delete(memberId);
			return new NoDataJsonResult(result);
		} else {
			return new NoDataJsonResult(memberResult.getResultCode(),
					memberResult.getMessage());
		}
	}

	@RequestMapping(value = { "/GetMemberTasks" }, method = RequestMethod.GET)
	public ModelAndView memberTaskList(HttpServletRequest request,
			HttpServletResponse response) {
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);
		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<Task>> taskResult = memberService.getTasks(memberId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("tasks", taskResult.getData());
		}
		return new ModelAndView("taskList", "model", model);
	}

	@RequestMapping(value = { "/CreateMember" }, method = RequestMethod.GET)
	public ModelAndView createMemberGet(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		GeneralResult<Company> companyResult = companyService
				.getById(companyId);
		Member member = new Member();
		if (companyResult.getResultCode() == ResultCode.NORMAL) {
			member.setCompanyId(companyId);
		} else {
			throw new IllegalArgumentException();
		}

		Map<String, Object> model = new CoHashMap(request);
		model.put("member", member);
		return new ModelAndView("memberInfo", "model", model);
	}

	@RequestMapping(value = { "/CreateMember" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult createMemberPost(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		String name = request.getParameter("name");
		String workId = request.getParameter("workId");
		String qqNumber = request.getParameter("qqNumber");
		String wxNumber = request.getParameter("wxNumber");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");

		if (null != name) {
			name = name.trim();
		}
		if (null != workId) {
			workId = workId.trim();
		}
		if (null != qqNumber) {
			qqNumber = qqNumber.trim();
		}
		if (null != wxNumber) {
			wxNumber = wxNumber.trim();
		}
		if (null != phone) {
			phone = phone.trim();
		}

		if (null != password && password.trim().length() > 0) {
			password = password.trim();
		} else {
			password = phone.substring(phone.length() - 6);
		}

		Member member = new Member(name, companyId, workId, password, qqNumber,
				wxNumber, phone, null);

		GeneralResult<Integer> result = memberService.create(member);
		return new NoDataJsonResult(result);
	}
}
