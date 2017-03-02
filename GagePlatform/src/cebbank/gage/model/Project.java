package cebbank.gage.model;

import java.util.Date;

public class Project {
	private int id;
	private int companyId;
	private String name;
	private String description;
	private Date startTime;
	private Date endTime;
	private double progress;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	public Project() {}
	
	public Project(int id) {
		this.id = id;
	}
	
	public Project(int companyId, String name, String description, Date startTime, Date endTime, double progress) {
		this.companyId = companyId;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.progress = progress;
	}
	
	public Project(int id, int companyId, String name, String description, Date startTime, Date endTime, double progress) {
		this.id = id;
		this.companyId = companyId;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.progress = progress;
	}

	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		
		if(obj instanceof Project) {
			Project project = (Project) obj;
			return this.id == project.getId();
		}
		return false;
	}
}
