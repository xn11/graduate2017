package cebbank.gare.dao;

import java.util.List;

import cebbank.gage.pojo.Project;

public interface ProjectDao {
	public List<Project> getByCompany(int companyId);
	
	public Project getById(int id);
	
	public int create(Project project);
	
	public void update(Project project);
	
	public void delete(int id);
	
	public void deleteTaskAssign(int projectId);
}
