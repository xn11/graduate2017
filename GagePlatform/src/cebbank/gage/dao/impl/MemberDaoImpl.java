package cebbank.gage.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cebbank.gage.dao.MemberDao;
import cebbank.gage.model.Member;

@Repository
public class MemberDaoImpl extends HibernateDaoBase implements MemberDao {

	private static Logger logger = LoggerFactory.getLogger(MemberDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getByCompany(int companyId) throws DataAccessException {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("from Member where companyId = " + companyId + " order by id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public int create(Member member) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(member);
	}

	@Override
	public void update(Member member) throws DataAccessException {
		getHibernateTemplate().update(member);

	}

	@Override
	public void delete(int id) throws DataAccessException {
		getHibernateTemplate().delete(new Member(id));
	}

	@Override
	public Member getById(int id) {
		return getHibernateTemplate().get(Member.class, id);
	}

	@Override
	public Member getByOpenId(String openId) {
		@SuppressWarnings("unchecked")
		List<Member> memberList = getHibernateTemplate().find(
				"from Member where openId = ?", openId);
		if(null != memberList && !memberList.isEmpty()) {
			return memberList.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public Member getByPhoneAndPassword(String phone, String password) {
		Session session = super.getSession(true);
		try {
			Query query = getSession().createQuery("from Member where phone = " + phone + " and password = " + password);
			@SuppressWarnings("unchecked")
			List<Member> memberList = query.list();
			if(null != memberList && !memberList.isEmpty()) {
				return memberList.get(0);
			}else {
				return null;
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Member> getByName(String name) {
		Query query = getSession().createQuery("from Member where name like '%" + name + "%'" );
		@SuppressWarnings("unchecked")
		List<Member> memberList = query.list();
		if(null != memberList && !memberList.isEmpty()) {
			return memberList;
		}else {
			return null;
		}
	}

}
