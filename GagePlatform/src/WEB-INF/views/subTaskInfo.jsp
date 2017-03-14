<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="subTaskEditForm">
	<table class="table table-responsive">
		<c:set value="${model.task}" var="task" />
		<tr>
			<td><c:if test="${task.id != null && task.id != 0}">
					<input name="taskId" type="hidden" value="${task.id}" />

				</c:if>
				<c:if test="task.path != null">
					<input name="path" type="hidden" value="${task.path}" />
				</c:if>
				<c:if test="task.depth != null && task.id != 0">
					<input name="depth" type="hidden" value="${task.depth}" />
				</c:if>			
				</td>
			<td><input name="projectId" type="hidden"
				value="${task.projectId}" /></td>
		<c:if test="${task.parentId != null && task.parentId != 0}">
		<tr>
			<input name="parentId" type="hidden"
				value="${task.parentId}" />
		</tr>
		</c:if>

		</tr>
		<tr>
			<td><label>任务名称</label></td>
			<td><input id="subTaskName" name="name" type="text" value="${task.name}" /></td>
		</tr>
		<tr>
			<td><label>任务描述</label></td>
			<td><input id="subTaskDescription" name="description" type="text"
				value="${task.description}" /></td>
		</tr>
		<tr>
			<td><label>所属项目</label></td>
			<td><label>${task.projectId}</label></td>
		</tr>
		<tr>
			<td><label>父任务</label></td>
			<td><label>${task.parentId}</label></td>
		</tr>
		<tr>
			<td><label>开始时间</label></td>
			<td><input id="subTaskStartDate" name="startDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${task.startTime}' pattern='yyyy-MM-dd' />" />
				<input id="subTaskStartTime" name="startTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${task.startTime}' pattern='HH:mm:ss' />" />
			</td>
		<tr>
			<td><label>结束时间</label></td>
			<td><input id="subTaskEndDate" name="endDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${task.endTime}' pattern='yyyy-MM-dd' />" />
				<input id="subTaskEndTime" name="endTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${task.endTime}' pattern='HH:mm:ss' />" />
			</td>
		</tr>
		<tr>
			<td><label>当前状态</label></td>
			<td>
				<select id="subTaskStatus" name="status">
					<option value="1"
						<c:if test="${task.status == 1}">slected="selected"</c:if>
					>未开始</option>
					<option value="2"
						<c:if test="${task.status == 2}">slected="selected"</c:if>>进行中</option>
					<option value="3"
						<c:if test="${task.status == 3}">slected="selected"</c:if>>已完成</option>
					<option value="4"
						<c:if test="${task.status == 4}">slected="selected"</c:if>>已失效</option>
				</select>
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	var $subTaskStartDatePicker = $("#subTaskStartDate");
	var $subTaskStartTimePicker = $("#subTaskStartTime");
	var $subTaskEndDatePicker = $("#subTaskEndDate");
	var $subTaskEndTimePicker = $("#subTaskEndTime");

	$subTaskStartDatePicker.datepicker();
	$subTaskStartTimePicker.timepicker({ "timeFormat": "H:i:s" });
	$subTaskEndDatePicker.datepicker();
	$subTaskEndTimePicker.timepicker({ "timeFormat": "H:i:s" });	

</script>