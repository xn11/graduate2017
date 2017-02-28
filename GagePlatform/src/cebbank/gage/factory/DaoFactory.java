package cebbank.gage.factory;

import cebbank.gare.dao.*;
import cebbank.gare.dao.impl.*;

/**
 * singleton
 * 
 * @author xn
 *
 */
public enum DaoFactory {
	INSTANCE;
	
	private AdminDao adminDao;

	public AdminDao getAdminDao() {
		if(null == adminDao) {
			adminDao = new AdminDaoImpl();
		}
		return adminDao;
	} 


}
