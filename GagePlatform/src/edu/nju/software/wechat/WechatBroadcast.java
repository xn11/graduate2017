package edu.nju.software.wechat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sword.wechat4j.message.CustomerMsg;
import org.sword.wechat4j.response.ArticleResponse;

import cebbank.gage.pojo.User;
import cebbank.gare.common.EmployeeType;
import cebbank.gare.common.TaskStatus;
import cebbank.gage.pojo.Log;
import cebbank.gage.pojo.Member;
import cebbank.gage.pojo.News;
import cebbank.gage.pojo.OutEmployee;
import cebbank.gage.pojo.SystemAdmin;
import edu.nju.software.service.AdminService;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.service.SystemAdminService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

/**
 * 
 * 微信群发类
 *
 */
@Component
public class WechatBroadcast {
	@Autowired
	private MemberService memberService;

	@Autowired
	private OutEmployeeService outEmployeeService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private SystemAdminService systemAdminService;

	private List<String> getOpenIdList(int companyId) {
		List<String> openIdList = new ArrayList<String>();
		// member
		GeneralResult<List<Member>> memberResult = memberService
				.getAllByCompany(companyId);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			List<Member> memberList = memberResult.getData();
			if (null != memberList && !memberList.isEmpty()) {
				for (Member member : memberList) {
					String openId = member.getOpenId();
					if (null == openId || StringUtils.isBlank(openId)) {
						continue;
					}
					openIdList.add(openId);
				}
			}
		}
		// outEmployee
		GeneralResult<List<OutEmployee>> outEmployeeResult = outEmployeeService
				.getByCompany(companyId);
		if (outEmployeeResult.getResultCode() == ResultCode.NORMAL) {
			List<OutEmployee> outEmployeeList = outEmployeeResult.getData();
			if (null != outEmployeeList && !outEmployeeList.isEmpty()) {
				for (OutEmployee outEmployee : outEmployeeList) {
					String openId = outEmployee.getOpenId();
					if (null == openId || StringUtils.isBlank(openId)) {
						continue;
					}
					openIdList.add(openId);
				}
			}
		}
		return openIdList;
	}

	// 群发资讯
	public void broadcastNews(News news) {
		ArticleResponse newsRsp = WeChatRsp.getNewsRsp(news, null);

		/*
		 * ArticleResponse newsRsp = new ArticleResponse(); String parameter =
		 * "?newsId=" + news.getId();
		 * 
		 * newsRsp.setTitle(news.getTitle());
		 * newsRsp.setDescription(news.getContent());
		 * newsRsp.setPicUrl("http://njucowork-pic.stor.sinaapp.com/info.jpg");
		 * newsRsp.setUrl(WeChatInstruct.DIMAIN + "/wechat/NewsDetail" +
		 * parameter);
		 */

		// UserManager userManager = new UserManager();
		// List<Object> openIdList = userManager.allSubscriber();
		List<String> openIdList = getOpenIdList(1);

		for (String openId : openIdList) {
			CustomerMsg msg = new CustomerMsg(openId);

			msg.sendNews(newsRsp);
		}
	}

	// 群发资讯列表
	public void broadcastNewsList(List<News> newsList) {
		List<ArticleResponse> newsRsps = new ArrayList<ArticleResponse>();

		int index = 1;
		for (News news : newsList) {
			String picURL = "http://njucowork-pic.stor.sinaapp.com/number"
					+ index + ".png";
			ArticleResponse newsRsp = WeChatRsp.getNewsRsp(news, picURL);
			if (null == newsRsp) {
				continue;
			}
			newsRsps.add(newsRsp);
			index++;
		}

		// UserManager userManager = new UserManager();
		// List<Object> openIdList = userManager.allSubscriber();
		List<String> openIdList = getOpenIdList(1);

		for (String openId : openIdList) {
			CustomerMsg msg = new CustomerMsg(openId);

			msg.sendNews(newsRsps);
		}
	}

	// 任务状态修改推送
	public void broadcastChanges(List<String> openIDList, Log change) {
		for (Object openId : openIDList) {
			String openIdString = String.valueOf(openId);
			CustomerMsg customerMsg = new CustomerMsg(openIdString);
			String name = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			if (change.getCreatorType() == EmployeeType.MEMBER) {
				GeneralResult<Member> memberResult = memberService
						.getById(change.getCreatorId());
				Member member = memberResult.getData();

				if (null != member) {
					name = name + "员工" + member.getName();
				} else {
					name += "某员工";
				}

			} else if (change.getCreatorType() == EmployeeType.OUT_EMPLOYEE) {
				GeneralResult<OutEmployee> outEmployeeResult = outEmployeeService
						.getById(change.getCreatorId());
				OutEmployee outEmployee = outEmployeeResult.getData();

				if (null != outEmployee) {
					name = name + "外聘人员" + outEmployee.getName();
				} else {
					name += "某外聘人员";
				}
			} else if (change.getCreatorType() == EmployeeType.ADMIN) {
				GeneralResult<User> adminResult = adminService.getById(change
						.getCreatorId());
				User admin = adminResult.getData();

				if (null != admin) {
					name = name + "管理员" + admin.getName();
				} else {
					name += "某管理员";
				}
			} else if (change.getCreatorType() == EmployeeType.SYSTEM_ADMIN) {
				GeneralResult<SystemAdmin> systemAdminResult = systemAdminService
						.getById(change.getCreatorId());
				SystemAdmin systemAdmin = systemAdminResult.getData();

				if (null != systemAdmin) {
					name = name + "系统管理员" + systemAdmin.getUserName();
				} else {
					name += "某系统管理员";
				}
			}

			String message = "任务状态修改提示：您好，您所参与的"
					+ change.getProject().getName() + "-"
					+ change.getTask().getName() + "任务状态已经于"
					+ dateFormat.format(change.getCreatedTime()) + "被" + name
					+ "由“" + TaskStatus.getStatusStr(change.getOriginStatus())
					+ "”改为“"
					+ TaskStatus.getStatusStr(change.getCurrentStatus())
					+ "”，敬请关注。";

			customerMsg.sendText(message);
		}
	}
}
