package cebbank.gare.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cebbank.gage.pojo.News;
import cebbank.gare.dao.NewsDao;

@Repository
public class NewsDaoImpl extends HibernateDaoBase implements NewsDao{
	private static Logger logger = LoggerFactory.getLogger(NewsDaoImpl.class);
	
	private static final int MAX_PIECES = 10;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<News> getLatestNews(int companyId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("from News where companyId = " + companyId + " and publishTime is not null order by publishTime desc, id desc");
			query.setFirstResult(0);
			query.setMaxResults(MAX_PIECES);
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<News> getNotPublishedNews(int companyId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("from News where companyId = " + companyId + " and publishTime is null order by createdTime desc, id desc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public int create(News news) {
		return (Integer) getHibernateTemplate().save(news);
	}

	@Override
	public void update(News news) {
		getHibernateTemplate().update(news);
		
	}

	@Override
	public void delete(int id) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("delete from News where id = " + id);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public News getById(int id) {
		return getHibernateTemplate().get(News.class, id);
	}

}
