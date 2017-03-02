package cebbank.gage.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class TaskAssign {
	private int id;
	private int taskId;
	private int memberId;
	private int outEmployeeId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getOutEmployeeId() {
		return outEmployeeId;
	}
	public void setOutEmployeeId(int outEmployeeId) {
		this.outEmployeeId = outEmployeeId;
	}
	public TaskAssign() {}
	
	public TaskAssign(int id) {
		this.id = id;
	}
	
	public TaskAssign(int taskId, int memberId, int outEmployeeId) {
		this.taskId = taskId;
		this.memberId = memberId;
		this.outEmployeeId = outEmployeeId;
	}
}
