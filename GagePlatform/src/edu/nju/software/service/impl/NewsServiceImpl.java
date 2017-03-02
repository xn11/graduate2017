package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cebbank.gage.model.News;
import cebbank.gare.dao.NewsDao;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class NewsServiceImpl implements NewsService{

	private static Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
	
	private static final String NEWS_CACHE_KEY = "news_%d";
	
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public GeneralResult<List<News>> getLatestNews(int companyId) {
		GeneralResult<List<News>> result = new GeneralResult<List<News>>();
		try {
			List<News> newsList = newsDao.getLatestNews(companyId);
			if(null != newsList && !newsList.isEmpty()) {
				result.setData(newsList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no news data in company, companyId = " + companyId);
			}
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<List<News>> getNotPublishedNews(int companyId) {
		GeneralResult<List<News>> result = new GeneralResult<List<News>>();
		try {
			List<News> newsList = newsDao.getNotPublishedNews(companyId);
			if(null != newsList && !newsList.isEmpty()) {
				result.setData(newsList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
				result.setMessage("no news data in company, companyId = " + companyId);
			}
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@Override
	public GeneralResult<Integer> create(News news) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = newsDao.create(news);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		
		return result;
	}

	@Override
	public NoDataResult update(News news) {
		NoDataResult result = new NoDataResult();
		try {
			newsDao.update(news);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(NEWS_CACHE_KEY, news.getId()));
		return result;
	}

	@Override
	public NoDataResult delete(int id) {
		NoDataResult result = new NoDataResult();
		try {
			newsDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(NEWS_CACHE_KEY, id));
		return result;
	}

	@Override
	public GeneralResult<News> getById(int id) {
		GeneralResult<News> result = new GeneralResult<News>();
		News news = (News) CoCacheManager.get(String.format(NEWS_CACHE_KEY, id));
		if(null != news) {
			result.setData(news);
		}else {
			try {
				news = newsDao.getById(id);
				result.setData(news);
				CoCacheManager.put(String.format(NEWS_CACHE_KEY, id), news);
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

}
