package edu.nju.software.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.software.pojo.Member;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralJsonResult;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class ValidateController {

	@Autowired
	private WorkService workService;

	@RequestMapping(value = { "/CheckCreateSubTask" }, method = RequestMethod.GET)
	@ResponseBody
	public GeneralJsonResult<Integer> checkCreateSubTask(
			HttpServletRequest request, HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		GeneralResult<List<Member>> taskAssignResult = workService
				.getRelatedMembers(taskId);

		int resultCode = 0;
		if (taskAssignResult.getResultCode() == ResultCode.NORMAL) {
			resultCode = 1;
		}
		return new GeneralJsonResult<Integer>(resultCode);
	}

}
