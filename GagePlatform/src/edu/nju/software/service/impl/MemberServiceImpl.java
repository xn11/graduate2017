package edu.nju.software.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.dao.MemberDao;
import cebbank.gage.dao.TaskDao;
import cebbank.gage.model.Member;
import cebbank.gage.model.Task;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class MemberServiceImpl implements MemberService {
	
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	private static final String COMPANY_MEMBER_CACHE_KEY = "company_member_cache_key_%d";
	
	private static final String MEMBER_CACHE_KEY = "member_%d";
	
	private static final String MEMBER_OPENID_CACHE_KEY = "member_openid_%d";
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private TaskDao taskDao;
	
	@Override
	public GeneralResult<List<Member>> getAllByCompany(int companyId) {
		GeneralResult<List<Member>> result = new GeneralResult<List<Member>>();
		@SuppressWarnings("unchecked")
		List<Member> memberList = (List<Member>) CoCacheManager.get(String.format(COMPANY_MEMBER_CACHE_KEY, companyId));
		if(null != memberList && !memberList.isEmpty()) {
			result.setData(memberList);
		}else {
			try {
				memberList = memberDao.getByCompany(companyId);
				if(null != memberList && !memberList.isEmpty()) {
					result.setData(memberList);
					CoCacheManager.put(String.format(COMPANY_MEMBER_CACHE_KEY, companyId), memberList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no member data in company, companyId = " + companyId);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Member> getByCompanyAndId(int companyId, int memberId) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		GeneralResult<List<Member>> companyMemberResult = getAllByCompany(companyId);
		if(companyMemberResult.getResultCode() == ResultCode.NORMAL) {
			for(Member member : companyMemberResult.getData()) {
				if(member.getId() == memberId) {
					result.setData(member);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(companyMemberResult.getResultCode());
			result.setMessage(companyMemberResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Member> getByCompanyAndWorkId(int companyId,
			String workId) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		GeneralResult<List<Member>> companyMemberResult = getAllByCompany(companyId);
		if(companyMemberResult.getResultCode() == ResultCode.NORMAL) {
			for(Member member : companyMemberResult.getData()) {
				if(member.getWorkId().equals(workId)) {
					result.setData(member);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(companyMemberResult.getResultCode());
			result.setMessage(companyMemberResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Integer> create(Member member) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = memberDao.create(member);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(COMPANY_MEMBER_CACHE_KEY, member.getCompanyId()));
		return result;
	}

	@Override
	public NoDataResult update(Member member) {
		NoDataResult result = new NoDataResult();
		try {
			memberDao.update(member);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(COMPANY_MEMBER_CACHE_KEY, member.getCompanyId()));
		CoCacheManager.remove(String.format(MEMBER_CACHE_KEY, member.getId()));
		return result;
	}

	@Override
	public NoDataResult delete(int id) {
		NoDataResult result = new NoDataResult();
		
		GeneralResult<Member> memberResult = getById(id);
		if(memberResult.getResultCode() == ResultCode.NORMAL) {
			CoCacheManager.remove(String.format(COMPANY_MEMBER_CACHE_KEY, memberResult.getData().getCompanyId()));
		}
		CoCacheManager.remove(String.format(MEMBER_CACHE_KEY, id));
		
		try {
			memberDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}

		return result;
	}

	@Override
	public GeneralResult<Member> getById(int id) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		Member member = (Member) CoCacheManager.get(String.format(MEMBER_CACHE_KEY, id));
		if(null != member) {
			result.setData(member);
		}else {
			try {
				member = memberDao.getById(id);
				result.setData(member);
				CoCacheManager.put(String.format(MEMBER_CACHE_KEY, id), member);
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}
	


	@Override
	public GeneralResult<Member> getByOpenId(String openId) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		Member member = (Member) CoCacheManager.get(MEMBER_OPENID_CACHE_KEY + openId);
		if(null != member) {
			result.setData(member);
		}else {
			try {
				member = memberDao.getByOpenId(openId);
				if(null != member) {
					result.setData(member);
					CoCacheManager.put(MEMBER_OPENID_CACHE_KEY + openId, member);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}


	@Override
	public GeneralResult<List<Task>> getTasks(int memberId) {
		GeneralResult<List<Task>> result = new GeneralResult<List<Task>>();
		try {
			List<Task> taskList = taskDao.getTasksByMember(memberId);
			if(null != taskList && !taskList.isEmpty()) {
				result.setData(taskList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no task find, memberId: " + memberId);
			}
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Member> getByPhoneAndPassword(String phone,
			String password) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		if(StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			return result;
		}
		
		Member member = memberDao.getByPhoneAndPassword(phone, password);
		
		if(null == member){
			result.setResultCode(ResultCode.E_NO_DATA);
			result.setMessage("error, no member, phone: " + phone + ", password: " + password);
		}else {
			result.setData(member);
		}
		
		return result;
	}

	@Override
	public GeneralResult<List<Member>> getByName(String name) {
		GeneralResult<List<Member>> result = new GeneralResult<List<Member>>();
		List<Member> memberList = null;
		try {
			memberList = memberDao.getByName(name);
			result.setData(memberList);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}
