<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="projectEditForm">
	<table class="table table-responsive">
		<c:set value="${model.project}" var="project" />
		<tr>
			<td><c:if test="${project.id != null && projet.id != 0}">
					<input name="projectId" type="hidden" value="${project.id}" />
				</c:if></td>
			<td><input name="companyId" type="hidden"
				value="${project.companyId}" /></td>
		</tr>
		<tr>
			<td><label>项目名称</label></td>
			<td><input id="projectName" name="name" type="text" value="${project.name}" /></td>
		</tr>
		<tr>
			<td><label>项目描述</label></td>
			<td><input id="projectDescription" name="description" type="text"
				value="${project.description}" /></td>
		</tr>
		<tr>
			<td><label>所属公司</label></td>
			<td><label>${project.companyId}</label></td>
		</tr>
		<tr>
			<td><label>开始时间</label></td>
			<td><input id="projectStartDate" name="startDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${project.startTime}' pattern='yyyy-MM-dd' />" />
				<input id="projectStartTime" name="startTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${project.startTime}' pattern='HH:mm:ss' />" />
			</td>
		<tr>
			<td><label>结束时间</label></td>
			<td><input id="projectEndDate" name="endDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${project.endTime}' pattern='yyyy-MM-dd' />" />
				<input id="projectEndTime" name="endTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${project.endTime}' pattern='HH:mm:ss' />" />
			</td>
		</tr>
		<tr>
			<td><label>当前进度</label></td>
			<td><input id="projectProgress" name="progress" type="text"
				value="${project.progress}" /></td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	$projectStartDatePicker = $("#projectStartDate");
	$projectStartTimePicker = $("#projectStartTime");
	$projectEndDatePicker = $("#projectEndDate");
	$projectEndTimePicker = $("#projectEndTime");
	
	$projectStartDatePicker.datepicker();
	$projectStartTimePicker.timepicker({ "timeFormat": "H:i:s" });
	$projectEndDatePicker.datepicker();
	$projectEndTimePicker.timepicker({ "timeFormat": "H:i:s" });
</script>