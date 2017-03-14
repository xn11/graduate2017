package cebbank.gage.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.dao.OutEmployeeDao;
import cebbank.gage.model.Company;
import cebbank.gage.model.OutEmployee;

@Repository
public class OutEmployeeDaoImpl extends HibernateDaoBase implements
		OutEmployeeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OutEmployee> getByCompany(int companyId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession()
					.createQuery(
							"select oe from OutEmployee oe, Company c, CompanyOutEmployee coe where coe.companyId = c.id and coe.outEmployeeId = oe.id and c.id = "
									+ companyId + " order by oe.id asc");
			return query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getRelatedCompanies(int outEmployeeId) {
		Session session = super.getSession(true);
		try {
			Query query = getSession()
					.createQuery(
							"select c from Company c, OutEmployee oe, CompanyOutEmployee coe where coe.companyId = c.id and coe.outEmployeeId = oe.id and eoe.id = "
									+ outEmployeeId + " order by c.id asc");
			return query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public OutEmployee getById(int outEmployeeId) {
		return getHibernateTemplate().get(OutEmployee.class, outEmployeeId);
	}

	@Override
	public OutEmployee getByOpenId(String openId) {
		@SuppressWarnings("unchecked")
		List<OutEmployee> outEmployeeList = getHibernateTemplate().find(
				"from OutEmployee where openId = ?", openId);
		if (null != outEmployeeList && !outEmployeeList.isEmpty()) {
			return outEmployeeList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public OutEmployee getByPhoneAndPassword(String phone, String password) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery(
					"from OutEmployee where phone = " + phone
							+ " and password = " + password);
			@SuppressWarnings("unchecked")
			List<OutEmployee> outEmployeeList = query.list();
			if (null != outEmployeeList && !outEmployeeList.isEmpty()) {
				return outEmployeeList.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public void update(OutEmployee outEmployee) throws DataAccessException{
		getHibernateTemplate().update(outEmployee);		
	}

}
