package edu.nju.software.pojo;

import java.util.Date;

public class Task {
	private int id;
	private int projectId;
	private String name;
	private String description;
	private int parentId;
	private int depth;
	private int status;
	private Date startTime;
	private Date endTime;
	private String path;
	private boolean isLeaf;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	public Task() {}
	
	public Task(int id) {
		this.id = id;
	}
	
	public Task(int projectId, String name, String description, int parentId, int status, int depth, Date startTime, Date endTime, String path, boolean isLeaf) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.parentId = parentId;
		this.status = status;
		this.depth = depth;
		this.startTime = startTime;
		this.endTime = endTime;
		this.path = path;
		this.isLeaf = isLeaf;
	}
	
	public Task(int id, int projectId, String name, String description, int parentId, int status, int depth, Date startTime, Date endTime, String path, boolean isLeaf) {
		this.id = id;
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.parentId = parentId;
		this.status = status;
		this.depth = depth;
		this.startTime = startTime;
		this.endTime = endTime;
		this.path = path;
		this.isLeaf = isLeaf;
	}

	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		
		if(obj instanceof Task) {
			Task task = (Task) obj;
			return this.id == task.getId();
		}
		return false;
	}
}
