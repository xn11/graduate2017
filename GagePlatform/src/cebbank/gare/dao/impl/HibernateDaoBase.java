package cebbank.gare.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDaoBase extends HibernateDaoSupport {
	@Resource(name="sessionFactory")
	public final void setSessionFactoryOverride(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
}
