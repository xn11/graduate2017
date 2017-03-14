package cebbank.gage.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDaoBase extends HibernateDaoSupport {
	 @Resource(name="sessionFactory")
	 public final void setSessionFactoryOverride(SessionFactory
	 sessionFactory){
	 super.setSessionFactory(sessionFactory);
	 }

//	public final void setSessionFactoryOverride(SessionFactory sessionFactory) {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
//		super.setSessionFactory((SessionFactory) context.getBean("sessionFactory"));
//	}

}
