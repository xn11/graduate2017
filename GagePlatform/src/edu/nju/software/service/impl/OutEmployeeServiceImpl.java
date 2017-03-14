package edu.nju.software.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.dao.OutEmployeeDao;
import cebbank.gage.dao.TaskDao;
import cebbank.gage.model.Company;
import cebbank.gage.model.OutEmployee;
import cebbank.gage.model.Task;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class OutEmployeeServiceImpl implements OutEmployeeService {
	private static Logger logger = LoggerFactory
			.getLogger(MemberServiceImpl.class);

	private static final String COMPANY_OUT_EMPLOYEE_CACHE_KEY = "company_out_employee_cache_key_%d";

	private static final String OUT_EMPLOYEE_RELATED_COMPANY_CACHE_KEY = "out_employee_related_company_cache_key_%d";

	private static final String OUT_EMPLOYEE_KEY_FORMAT = "out_employee_%d";

	private static final String OUT_EMPLOYEE_OPENID_CACHE_KEY = "out_employee_openid_%d";

	@Autowired
	private OutEmployeeDao outEmployeeDao;
	@Autowired
	private TaskDao taskDao;

	@Override
	public GeneralResult<List<OutEmployee>> getByCompany(int companyId) {
		GeneralResult<List<OutEmployee>> result = new GeneralResult<List<OutEmployee>>();
		@SuppressWarnings("unchecked")
		List<OutEmployee> outEmployeeList = (List<OutEmployee>) CoCacheManager
				.get(String.format(COMPANY_OUT_EMPLOYEE_CACHE_KEY, companyId));
		if (null != outEmployeeList && !outEmployeeList.isEmpty()) {
			result.setData(outEmployeeList);
		} else {
			try {
				outEmployeeList = outEmployeeDao.getByCompany(companyId);
				if (null != outEmployeeList && !outEmployeeList.isEmpty()) {
					result.setData(outEmployeeList);
					CoCacheManager.put(String.format(
							COMPANY_OUT_EMPLOYEE_CACHE_KEY, companyId),
							outEmployeeList);
				} else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no out employee data in company, companyId = "
							+ companyId);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<List<Company>> getRelatedCompanies(int outEmployeeId) {
		GeneralResult<List<Company>> result = new GeneralResult<List<Company>>();
		@SuppressWarnings("unchecked")
		List<Company> companyList = (List<Company>) CoCacheManager.get(String
				.format(OUT_EMPLOYEE_RELATED_COMPANY_CACHE_KEY, outEmployeeId));
		if (null != companyList && !companyList.isEmpty()) {
			result.setData(companyList);
		} else {
			try {
				companyList = outEmployeeDao.getRelatedCompanies(outEmployeeId);
				if (null != companyList && !companyList.isEmpty()) {
					result.setData(companyList);
					CoCacheManager.put(String.format(
							OUT_EMPLOYEE_RELATED_COMPANY_CACHE_KEY,
							outEmployeeId), companyList);
				} else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no related company data, outEmployeeId = "
							+ outEmployeeId);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<List<Task>> getTasks(int companyId, int outEmployeeId) {
		GeneralResult<List<Task>> result = new GeneralResult<List<Task>>();
		try {
			List<Task> taskList = taskDao.getTasksByOutEmployee(companyId,
					outEmployeeId);
			if (null != taskList && !taskList.isEmpty()) {
				result.setData(taskList);
			} else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no task find, outEmployeeId: "
						+ outEmployeeId + ",companyId: " + companyId);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<OutEmployee> getById(int outEmployeeId) {
		GeneralResult<OutEmployee> result = new GeneralResult<OutEmployee>();
		OutEmployee outEmployee = (OutEmployee) CoCacheManager.get(String
				.format(OUT_EMPLOYEE_KEY_FORMAT, outEmployeeId));
		if (null != outEmployee) {
			result.setData(outEmployee);
		} else {
			try {
				outEmployee = outEmployeeDao.getById(outEmployeeId);
				result.setData(outEmployee);
				CoCacheManager.put(
						String.format(OUT_EMPLOYEE_KEY_FORMAT, outEmployeeId),
						outEmployee);
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<OutEmployee> getByOpenId(String openId) {
		GeneralResult<OutEmployee> result = new GeneralResult<OutEmployee>();
		OutEmployee outEmployee = (OutEmployee) CoCacheManager
				.get(OUT_EMPLOYEE_OPENID_CACHE_KEY +openId);
		if (null != outEmployee) {
			result.setData(outEmployee);
		} else {
			try {
				outEmployee = outEmployeeDao.getByOpenId(openId);
				if (null != outEmployee) {
					result.setData(outEmployee);
					CoCacheManager
							.put(OUT_EMPLOYEE_OPENID_CACHE_KEY+
									openId, outEmployee);
				} else {
					result.setResultCode(ResultCode.E_NO_DATA);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<OutEmployee> getByPhoneAndPassword(String phone,
			String password) {
		GeneralResult<OutEmployee> result = new GeneralResult<OutEmployee>();
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			return result;
		}

		OutEmployee outEmployee = outEmployeeDao.getByPhoneAndPassword(phone,
				password);

		if (null == outEmployee) {
			result.setResultCode(ResultCode.E_NO_DATA);
			result.setMessage("error, no out employee, phone: " + phone
					+ ", password: " + password);
		} else {
			result.setData(outEmployee);
		}

		return result;
	}

	@Override
	public NoDataResult update(OutEmployee outEmployee) {
		NoDataResult result = new NoDataResult();
		try {
			outEmployeeDao.update(outEmployee);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}

		CoCacheManager.remove(String.format(OUT_EMPLOYEE_KEY_FORMAT,
				outEmployee.getId()));
		CoCacheManager.remove(String
				.format(OUT_EMPLOYEE_KEY_FORMAT, outEmployee.getId()));
		return result;
	}

}
