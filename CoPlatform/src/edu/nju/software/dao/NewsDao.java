package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.News;

public interface NewsDao {
	public List<News> getLatestNews(int companyId);
	
	public List<News> getNotPublishedNews(int companyId);
	
	public News getById(int id);
	
	public int create(News news);

	public void update(News news);
	
	public void delete(int id);
	
}
