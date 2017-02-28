package edu.nju.software.service;

import java.util.List;

import cebbank.gage.pojo.News;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface NewsService {
	public GeneralResult<List<News>> getLatestNews(int companyId);
	
	public GeneralResult<List<News>> getNotPublishedNews(int companyId);
	
	public GeneralResult<Integer> create(News news);

	public NoDataResult update(News news);
	
	public NoDataResult delete(int id);
	
	public GeneralResult<News> getById(int id);
	
}
