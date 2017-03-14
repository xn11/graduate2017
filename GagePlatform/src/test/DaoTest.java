package test;

import java.io.File;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cebbank.gage.common.RoleEnum;
import cebbank.gage.dao.UserDao;
import cebbank.gage.dao.impl.HibernateDaoBase;
import cebbank.gage.factory.DaoFactory;
import cebbank.gage.model.User;

public class DaoTest {

	public static void main(String[] args) {

		// File f = new File("WebContent/WEB-INF/applicationContext.xml");
		// System.out.println(f.isFile());

		// ClassPathXmlApplicationContext 是读取 src 目录下的配置文件
		// ApplicationContext app = new
		// ClassPathXmlApplicationContext("applicationContext.xml");

		// FileSystemXmlApplicationContext 即系统文件路径，文件的目录。
//		 ApplicationContext context = new
//		 FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext.xml");

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
		context.start();

		
//		 HibernateDaoBase hdb =  new HibernateDaoBase();
//		 hdb.setSessionFactoryOverride((SessionFactory) context.getBean("sessionFactory"));
//		 HibernateTemplate h =hdb.getHibernateTemplate();
//		 System.out.println(h);
		 
//		 hdb.getHibernateTemplate().save(new User("test", RoleEnum.ADMIN, "test",
//				 "12345678901", 1));

		 UserDao userDao = DaoFactory.INSTANCE.getUserDao();
		 int res = userDao.create(new User("test", RoleEnum.ADMIN, "test",
		 "12345678901", 1));
//		 System.out.println(res);
		// List<User> userList = userDao.getAll();
		// System.out.println(userList.size());
	}

}
