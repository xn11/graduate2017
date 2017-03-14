package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.dao.CompanyDao;
import cebbank.gage.model.Company;
import edu.nju.software.service.CompanyService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class CompanyServiceImpl implements CompanyService {
	private static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	private static final String ALL_COMPANY_CACHE_KEY = "all_company_cache";
	
	@Autowired
	private CompanyDao companyDao;
	
	public CompanyServiceImpl() {}

	@Override
	public GeneralResult<List<Company>> getAll() {
		GeneralResult<List<Company>> result = new GeneralResult<List<Company>>();
		@SuppressWarnings("unchecked")
		List<Company> companyList = (List<Company>) CoCacheManager.get(ALL_COMPANY_CACHE_KEY);
		if(null != companyList && !companyList.isEmpty()) {
			result.setData(companyList);
		}else {
			try {
				companyList = companyDao.getAll();
				if(null != companyList && !companyList.isEmpty()) {
					result.setData(companyList);
					CoCacheManager.put(ALL_COMPANY_CACHE_KEY, companyList);
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
	public GeneralResult<Company> getById(int id) {
		GeneralResult<Company> result = new GeneralResult<Company>();
		GeneralResult<List<Company>> allResult = getAll();
		if(allResult.getResultCode() == ResultCode.NORMAL) {
			for(Company company : allResult.getData()) {
				if(company.getId() == id) {
					result.setData(company);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(allResult.getResultCode());
			result.setMessage(allResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Integer> create(Company company) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = companyDao.create(company);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult update(Company company) {
		NoDataResult result = new NoDataResult();
		try {
			companyDao.update(company);
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
		try {
			companyDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

}
