package edu.nju.software.service;

import java.util.List;

import cebbank.gage.model.Member;
import cebbank.gage.model.Task;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface MemberService {
	public GeneralResult<List<Member>> getAllByCompany(int companyId);

	public GeneralResult<Member> getById(int id);
	
	public GeneralResult<Member> getByOpenId(String openId);

	public GeneralResult<Member> getByCompanyAndId(int companyId, int memberId);
	
	public GeneralResult<List<Member>> getByName(String name);

	public GeneralResult<Member> getByCompanyAndWorkId(int companyId, String workId);

	public GeneralResult<Integer> create(Member member);

	public NoDataResult update(Member member);

	public NoDataResult delete(int id);

	public GeneralResult<List<Task>> getTasks(int memberId);

	public GeneralResult<Member> getByPhoneAndPassword(String phone, String password);
}
