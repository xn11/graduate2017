<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
<div class="container-bodymobile">
<h4 class="modal-title">任务信息</h4>

<form id="taskEditForm">
	<table class="table table-responsive taskInfoTable">
		<c:set value="${model.wxtask}" var="task" />
			<c:if test="${task.id != null && task.id != 0}">
				<input name="taskId" type="hidden" value="${task.id}" />
			</c:if>
			<c:if test="task.depth != null && task.id != 0">
				<input name="depth" type="hidden" value="${task.depth}" />
			</c:if>			
			<c:if test="task.path != null">
				<input name="path" type="hidden" value="${task.path}" />
			</c:if>			
		<input name="projectId" type="hidden"
				value="${task.projectId}" />
		<tr>
			<td><label>任务名称</label></td>
			<td><p>${task.name}</p></td>
		</tr>
		<tr>
			<td><label>任务描述</label></td>
			<td><p>${task.description}</p></td>
		</tr>
		<tr>
			<td><label>所属项目</label></td>
			<td><p>${task.projectId}</p></td>
		</tr>
		<tr>
			<td><label>开始时间</label></td>
			<td>
			<p>
			<fmt:formatDate value="${task.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			</p>
			</td>
		<tr>
			<td><label>结束时间</label></td>
			<td>			
			<p>
			<fmt:formatDate value="${task.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			</p>
			</td>
		</tr>
		<tr>
			<td><label>当前状态</label></td>
			<td>
				<select id="taskStatus" name="status">
					<option value="1"
						<c:if test="${task.status == 1}">selected="selected"</c:if>
					>未开始</option>
					<option value="2"
						<c:if test="${task.status == 2}">selected="selected"</c:if>>进行中</option>
					<option value="3"
						<c:if test="${task.status == 3}">selected="selected"</c:if>>已完成</option>
					<option value="4"
						<c:if test="${task.status == 4}">selected="selected"</c:if>>已失效</option>
				</select>
			</td>
		</tr>
	</table>
</form>
<div class="row submitDiv">
<button id="taskEditSubmit" type="button" class="btn btn-success submit-button">保存</button>
</div>
</div>

<div id="messageModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">系统信息</h4>
			</div>
			<div class="modal-body">更新任务状态成功</div>
			<div class="modal-footer">
				<button id="confirm" type="button" class="btn btn-primary"
					data-dismiss="modal">确定</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$taskStartDatePicker = $("#taskStartDate");
	$taskStartTimePicker = $("#taskStartTime");
	$taskEndDatePicker = $("#taskEndDate");
	$taskEndTimePicker = $("#taskEndTime");

	$taskStartDatePicker.datepicker();
	$taskStartTimePicker.timepicker({ "timeFormat": "H:i:s" });
	$taskEndDatePicker.datepicker();
	$taskEndTimePicker.timepicker({ "timeFormat": "H:i:s" });	
	
	var $taskEditSubmit = $("#taskEditSubmit");
	
	$taskEditSubmit.click(function(e) {
		var $form = $("#taskEditForm");
		
		console.log("save task")
		var formData = $form.serialize();
		$.ajax({
			url: "UpdateTask",
			data: formData,
			method: "post",
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("success");
					$("#messageModal").modal();
					// location.reload();
				}else {
					console.log("save task info error, error code : " + result.resultCode + ";error message: " + result.message);
				}
			} 
		});
	});
	

	$("#confirm").click(function(e) {
		location.reload();
	});
</script>

</body>