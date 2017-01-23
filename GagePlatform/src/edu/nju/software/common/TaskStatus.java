package edu.nju.software.common;

public class TaskStatus {
	
	/**
	 * 未开始
	 */
	public static final int TASK_STATUS_NOT_STARTED = 1;
	
	/**
	 * 进行中
	 */
	public static final int TASK_STATUS_STARTED = 2;
	
	/**
	 * 已完成 
	 */
	public static final int TASK_STATUS_COMPLETED = 3;
	
	/**
	 * 已失效
	 */
	public static final int TASK_STATUS_INVALID = 4;

	/**
	 * 根据任务状态返回任务描述
	 * @param status 任务状态
	 * @return 任务状态描述
	 */
	public static String getStatusStr(int status){
		String statusStr = null;
		
		switch (status) {
		case 1:
			statusStr = "未开始";
			break;
		case 2:
			statusStr = "进行中";
			break;
		case 3:
			statusStr = "已完成";
			break;
		case 4:
			statusStr = "已失效";
			break;
		default:
			statusStr = "未开始";
			break;
		}
		
		return statusStr;
	}
}
