package edu.nju.software.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.model.SystemAdmin;
import cebbank.gare.dao.SystemAdminDao;
import edu.nju.software.service.SystemAdminService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {
	private static Logger logger = LoggerFactory
			.getLogger(SystemAdminServiceImpl.class);

	private static final String ALL_SYSTEM_ADMIN_CACHE_KEY = "all_system_admin_cache";

	@Autowired
	private SystemAdminDao systemAdminDao;

	@Override
	public GeneralResult<List<SystemAdmin>> getAll() {
		GeneralResult<List<SystemAdmin>> result = new GeneralResult<List<SystemAdmin>>();
		@SuppressWarnings("unchecked")
		List<SystemAdmin> systemAdminList = (List<SystemAdmin>) CoCacheManager
				.get(ALL_SYSTEM_ADMIN_CACHE_KEY);
		if (null != systemAdminList && !systemAdminList.isEmpty()) {
			result.setData(systemAdminList);
		} else {
			try {
				systemAdminList = systemAdminDao.getAll();
				if (null != systemAdminList && !systemAdminList.isEmpty()) {
					result.setData(systemAdminList);
					CoCacheManager.put(ALL_SYSTEM_ADMIN_CACHE_KEY,
							systemAdminList);
				} else {
					result.setResultCode(ResultCode.E_NO_DATA);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				result.setMessage(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			}
		}
		return result;
	}

	@Override
	public GeneralResult<SystemAdmin> getByUserNameAndPassword(String userName,
			String password) {
		GeneralResult<SystemAdmin> result = new GeneralResult<SystemAdmin>();
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			return result;
		}

		GeneralResult<List<SystemAdmin>> allResult = getAll();
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			for (SystemAdmin systemAdmin : allResult.getData()) {
				if (systemAdmin.getUserName().equals(userName)
						&& systemAdmin.getPassword().equals(password)) {
					result.setData(systemAdmin);
					break;
				}
			}
			if (null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no admin data, admin userName: " + userName);
			}
		} else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}

		return result;
	}

	@Override
	public GeneralResult<SystemAdmin> getById(int id) {
		GeneralResult<SystemAdmin> result = new GeneralResult<SystemAdmin>();
		GeneralResult<List<SystemAdmin>> allResult = getAll();
		if (allResult.getResultCode() == ResultCode.NORMAL) {
			for (SystemAdmin systemAdmin : allResult.getData()) {
				if (systemAdmin.getId() == id) {
					result.setData(systemAdmin);
					break;
				}
			}
			if (null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no admin data, admin id: " + id);
			}
		} else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}

		return result;
	}

}
