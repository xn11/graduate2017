package cebbank.gage.factory;

import cebbank.gage.dao.*;
import cebbank.gage.dao.impl.*;

/**
 * singleton
 * 
 * @author xn
 *
 */
public enum DaoFactory {
	INSTANCE;
	
	private UserDao userDao;

	public UserDao getUserDao() {
		if(null == userDao) {
			userDao = new UserDaoImpl();
		}
		return userDao;
	} 


}
