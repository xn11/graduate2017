package edu.nju.software.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.model.User;
import cebbank.gare.dao.UserDao;
import edu.nju.software.service.AdminService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class AdminServiceImpl implements AdminService {
	private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	private static final String ALL_ADMIN_CACHE_KEY = "all_admin_cache";
	
	@Autowired
	private UserDao adminDao;
	
	public AdminServiceImpl() {}

	@Override
	public GeneralResult<List<User>> getAll() {
		GeneralResult<List<User>> result = new GeneralResult<List<User>>();
		@SuppressWarnings("unchecked")
		List<User> adminList = (List<User>) CoCacheManager.get(ALL_ADMIN_CACHE_KEY);
		if(null != adminList && !adminList.isEmpty()) {
			result.setData(adminList);
		}else {
			try {
				adminList = adminDao.getAll();
				if(null != adminList && !adminList.isEmpty()) {
					result.setData(adminList);
					CoCacheManager.put(ALL_ADMIN_CACHE_KEY, adminList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setMessage(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			}
		}
		return result;
	}

	@Override
	public GeneralResult<User> getById(int id) {
		GeneralResult<User> result = new GeneralResult<User>();
		GeneralResult<List<User>> allResult = getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(User admin : allResult.getData()) {
				if(admin.getId() == id) {
					result.setData(admin);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no admin data, admin id: " + id);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		
		return result;
	}

	@Override
	public GeneralResult<User> getByMailAndPassword(String mail,
			String password) {
		GeneralResult<User> result = new GeneralResult<User>();
		if(StringUtils.isBlank(mail) || StringUtils.isBlank(password)) {
			result.setResultCode(ResultCode.E_INVALID_PARAMETER);
			return result;
		}
		
		GeneralResult<List<User>> allResult = getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(User admin : allResult.getData()) {
				if(admin.getMail().equals(mail) && admin.getPassword().equals(password)) {
					result.setData(admin);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no admin data, admin mail: " + mail);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		
		return result;
	}

	@Override
	public GeneralResult<Integer> create(User admin) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = adminDao.create(admin);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult update(User admin) {
		NoDataResult result = new NoDataResult();
		try{
			adminDao.update(admin);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult delete(int id) {
		NoDataResult result = new NoDataResult();
		try{
			adminDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}


}
