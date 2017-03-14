package cebbank.gage.dao;

import java.util.List;

import cebbank.gage.model.Member;


public interface MemberDao {
	public List<Member> getByCompany(int companyId);
	
	public List<Member> getByName(String name);
	
	public Member getById(int id);
	
	public Member getByOpenId(String openId);
	
	public Member getByPhoneAndPassword(String phone, String password);
	
	public int create(Member member);
	
	public void update(Member member);
	
	public void delete(int id);
}
