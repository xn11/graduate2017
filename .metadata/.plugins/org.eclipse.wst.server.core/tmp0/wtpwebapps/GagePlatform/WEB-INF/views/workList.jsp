<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
		<h3>项目列表<a id="projectCreate" href="#" class="btn btn-primary create-button" style="margin-right:50px;">创建项目</a></h3>
		<hr />

		<div class="container-fluid">

			<c:forEach items="${model.works}" var="work">
				<c:set value="${work.key}" var="project" />
				<c:set value="${work.value}" var="tasks" />
				<div class="bottom-line project-item">
				<div class="row">
					<div class="col-xs-6">
						<a class="projectInfo btn" projectId="${project.id}">${project.name}</a>
					</div>
					<div style="float:right">
					<div >
					    <div class="col-xs-4">
					<a class="projectDelete btn btn-default btn-sm" projectId="${project.id}">删 除 项 目</a> 
					</div>	
					<div class="col-xs-2">
					<a 
						class="btn btn-info btn-sm" data-toggle="collapse"
						href="#projectTaskArea${project.id}" aria-expanded="false"
						aria-controls="collapseExample" style="margin-left:10px;">查 看 任 务</a>
					</div>
					</div>
					</div>					
				</div>
				<div id="projectTaskArea${project.id}" class="collapse taskList">
					<div class="row">
					<c:if test="${tasks != null}">
						<table
							class="table table-striped table-bordered table-hover table-responsive taskList-table">
							<tr >
								<th>任务名称</th>
								<th>任务列表</th>
								<th>所属项目</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>当前状态</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${tasks}" var="task">
								<tr>
									<td class="taskNameTd"><a class="taskInfo" href="#"
										taskId="${task.id}">${task.name}</a> <span><a
											class="taskDelete" href="#"><img
												src="<c:url value='/resources/images/delete.png' />"></a></span>
									</td>
									<td>${task.description}</td>
									<td>${task.projectId}</td>
									<td><fmt:formatDate value="${task.startTime}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td><fmt:formatDate value="${task.endTime}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>
										<c:if test="${task.status == 1}">未开始</c:if>
										<c:if test="${task.status == 2}">进行中</c:if>
										<c:if test="${task.status == 3}">已完成</c:if>
										<c:if test="${task.status == 4}">已失效</c:if>
									</td>
									<td><a class="taskAssign" href="#" taskId="${task.id}">分配任务</a></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${tasks == null}">
						<p style="color:gray">&nbsp;&nbsp;暂无任务</p>
						<br/>
					</c:if>
					
					<div class="col-xs-10"></div>
							<div class="col-xs-2" style="right:8px">
								<a class="taskCreate btn btn-primary btn-xs" href="#"
									projectId="${project.id}" style="float:right;margin-right:8px;">&nbsp;创 建  任 务&nbsp;</a>
							</div>
					</div>
						
					<br/>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>

	<div id="projectEditModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">项目信息</h4>
				</div>
				<div id="projectEditContent" class="modal-body"></div>
				<div class="modal-footer">
					<button id="projectEditSubmit" type="button"
						class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>

	<div id="taskEditModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">任务信息</h4>
				</div>
				<div id="taskEditContent" class="modal-body"></div>
				<div class="modal-footer">
					<button id="taskEditSubmit" type="button" class="btn btn-primary"
						data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>

	<div id="taskAssignModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">任务分配</h4>
				</div>
				<div id="taskAssignContent" class="modal-body">
					<label>类型</label> <select id="employeeTypeSelect">
						<option value="0">公司职员</option>
						<option value="1">外聘人员</option>
					</select> <label>人员</label> <select id="employeeList">
					</select>
				</div>
				<div class="modal-footer">
					<button id="taskAssignSubmit" type="button" class="btn btn-primary"
						>确定</button>
				</div>
			</div>
		</div>
	</div>

	<div id="projectCreateModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">创建项目</h4>
				</div>
				<div id="projectCreateContent" class="modal-body"></div>
				<div class="modal-footer">
					<button id="projectCreateSubmit" type="button"
						class="btn btn-primary">创建</button>
				</div>
			</div>
		</div>
	</div>

	<div id="taskCreateModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">创建任务</h4>
				</div>
				<div id="taskCreateContent" class="modal-body"></div>
				<div class="modal-footer">
					<button id="taskCreateSubmit" type="button" class="btn btn-primary">创建</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	/*constants*/
	var SAVE_TYPE_CREATE = 0;
	var SAVE_TYPE_UPDATE = 1;

	var EMPLOYEE_TYPE_MEMBER = 0;
	var EMPLOYEE_TYPE_OUT = 1;

	/*components*/
	var $projectInfoLink = $(".projectInfo");
	var $projectEditModal = $("#projectEditModal");
	var $projectEditContent = $("#projectEditContent");
	var $projectEditSubmit = $("#projectEditSubmit");
	var $projectDeleteBtn = $(".projectDelete");

	var $taskInfoLink = $(".taskInfo");
	var $taskEditModal = $("#taskEditModal");
	var $taskEditContent = $("#taskEditContent");
	var $taskEditSubmit = $("#taskEditSubmit");
	var $taskDeleteBtn = $(".taskDelete");
	var $taskNameTd = $(".taskNameTd");

	var $taskAssignBtn = $(".taskAssign");

	var $taskAssignModal = $("#taskAssignModal");
	var $employeeTypeSelect = $("#employeeTypeSelect");
	var $employeeListSelect = $("#employeeList");
	var $taskAssignSubmit = $("#taskAssignSubmit");

	var $projectCreateBtn = $("#projectCreate");
	var $projectCreateModal = $("#projectCreateModal");
	var $projectCreateContent = $("#projectCreateContent");
	var $projectCreateSubmit = $("#projectCreateSubmit");

	var $taskCreateBtn = $(".taskCreate");
	var $taskCreateModal = $("#taskCreateModal");
	var $taskCreateContent = $("#taskCreateContent");
	var $taskCreateSubmit = $("#taskCreateSubmit");

	/*variables*/
	var members;
	var outEmployees;
	var currentEmployeeType = EMPLOYEE_TYPE_MEMBER;
	var currentTaskId;


	$projectInfoLink.click(function(e) {
		var projectId = $(this).attr("projectId");
		console.log("get project info, projectId: " + projectId);
		$projectEditContent.load("GetProjectInfo?projectId=" + projectId + "&companyId=" + "${model.admin.companyId}", function(response, status, xhr) {
			if(status == "error") {
				$projectEditContent.load("Error");
			}
		});
		$projectEditModal.modal();
	});

	$projectDeleteBtn.click(function(e) {
		var projectId = $(this).attr("projectId");
		deleteProject(projectId);
	});

	$projectEditSubmit.click(function(e) {
		console.log("submit project info");
		saveProject($("#projectEditForm"), SAVE_TYPE_UPDATE);
	});


	$taskInfoLink.click(function(e) {
		var taskId = $(this).attr("taskId");
		// var projectId = $(this).;
		var projectId = 0;
		console.log("get task info, taskId: " + taskId + ", projectId: " + projectId);
		$taskEditContent.load("GetTaskInfo?taskId=" + taskId + "&projectId=" + projectId, function(response, status, xhr) {
			if(status == "error") {
				$taskEditContent.load("Error");
			}
		});
		$taskEditModal.modal();
	});

	$taskEditSubmit.click(function(e) {
		saveTask($("#taskEditForm"), SAVE_TYPE_UPDATE);
	});

	$taskDeleteBtn.click(function(e){
		console.log("delete click");
		var taskId = $(this).parent().prev().attr("taskId");
		deleteTask(taskId);
	});

	$taskNameTd.on("mouseover", function(e) {
		$(this).find("a.taskDelete").show();
	});

	$taskNameTd.on("mouseout", function(e) {
		$(this).find("a.taskDelete").hide();
	});

	$taskAssignBtn.click(function(e) {
		var taskId = $(this).attr("taskId");
		currentTaskId = taskId;
		if($employeeListSelect.children().length == 0) {
			appendEmployees(EMPLOYEE_TYPE_MEMBER);
		}
		$taskAssignModal.modal();
	});

	$taskAssignSubmit.click(function(e) {
		var employeeIndex = $employeeListSelect[0].selectedIndex;
		var employeeId = $employeeListSelect.children().eq(employeeIndex).val().trim();
		var companyId = "${model.admin.companyId}";
		assignTaskToEmployee(currentTaskId, employeeId, currentEmployeeType, companyId);
	});

	$projectCreateBtn.click(function(e) {
		console.log("click create project");
		var companyId = "${model.admin.companyId}";
		$projectCreateContent.load("CreateProject?companyId=" + companyId, function(response, status, xhr) {
			if(status == "error") {
				$projectEditContent.load("Error");
			}
		});
		$projectCreateModal.modal();
	});

	$projectCreateSubmit.click(function(e) {
		saveProject($("#projectEditForm"), SAVE_TYPE_CREATE);
	});

	$taskCreateBtn.click(function(e) {
		var projectId = $(this).attr("projectId");
		console.log("click task, projectId: " + projectId);
		$taskCreateContent.load("CreateTask?projectId=" + projectId, function(response, status, xhr) {
			if(status == "error") {
				$taskCreateContent.load("Error");
			}
		});
		$taskCreateModal.modal();
	});

	$taskCreateSubmit.click(function(e) {
		saveTask($("#taskEditForm"), SAVE_TYPE_CREATE);
	});

	$employeeTypeSelect.change(function(e) {
		var typeIndex = $employeeTypeSelect[0].selectedIndex;
		$employeeListSelect.empty();
		console.log("selectIndex: " + typeIndex);
		if(typeIndex == 0) {
			currentEmployeeType = EMPLOYEE_TYPE_MEMBER;
			appendEmployees(EMPLOYEE_TYPE_MEMBER);
		}else {
			currentEmployeeType = EMPLOYEE_TYPE_OUT;
			appendEmployees(EMPLOYEE_TYPE_OUT);
		}
	});

	/*functions*/
	function loadEmployees(employeeType) {
		var companyId = "${model.admin.companyId}";
		var url;
		if(employeeType == EMPLOYEE_TYPE_MEMBER) {
			url = "GetMemberList?companyId=" + companyId;
		}else {
			url = "GetOutEmployeeList?companyId=" + companyId;
		}

		$.ajax({
			url: url,
			success: function(result) {
				if(result.resultCode == 0) {	
					if(employeeType == EMPLOYEE_TYPE_MEMBER) {
						members = result.data;
					}else {
						outEmployees = result.data;
					}
				}else {
					console.log("get employee error");
				}
			}
		});		
	}

	function appendEmployees(employeeType) {
		if(employeeType == EMPLOYEE_TYPE_MEMBER) {
			members.forEach(function(member){
				$employeeListSelect.append($("<option value='" + member.id + "'>" + member.name + "</option>"));
			}); 
		}else {
			outEmployees.forEach(function(outEmployee){
				$employeeListSelect.append($("<option value='" + outEmployee.id + "'>" + outEmployee.name + "</option>"));
			}); 
		}
	}

	function assignTaskToEmployee(taskId, employeeId, employeeType, companyId) {
		var url;
		if(employeeType == EMPLOYEE_TYPE_MEMBER) {
			url = "MemberTaskAssign?taskId=" + taskId + "&memberId=" + employeeId;
		}else {
			url = "OutEmployeeTaskAssign?taskId=" + taskId + "&outEmployeeId=" + employeeId + "&companyId=" + companyId;
		}

		$.ajax({
			url: url,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("分配任务成功");
					$taskAssignModal.modal("hide");
				}else {
					alert("失败，任务不能重复分配！");
				}
			}
		});
	}


	function saveProject($form, saveType) {
		var formData = $form.serialize();
		var url;
		if(saveType == SAVE_TYPE_CREATE) {
			url = "CreateProject";
		}else {
			url = "UpdateProject";
		}

		var name = $("#projectName").val();
		var desc = $("#projectDescription").val();
		var startDate = $("#projectStartDate").val();
		var startTime = $("#projectStartTime").val();
		var endDate = $("#projectEndDate").val();
		var endTime = $("#projectEndTime").val();
		var progress = $("#projectProgress").val();
		if(checkProjectParameters(name, desc, startDate, startTime, endDate, endTime, progress)) {
			$.ajax({
				url: url,
				data: formData,
				method: "post",
				success: function(result) {
					if(result.resultCode == 0) {
						console.log("success");
						location.reload();
					}else {
						console.log("save task info error, error code : " + result.resultCode + ";error message: " + result.message);
					}
				} 
			});			
		}

	}

	function saveTask($form, saveType) {
		var formData = $form.serialize();
		var url;
		if(saveType == SAVE_TYPE_CREATE) {
			url = "CreateTask";
		}else {
			url = "UpdateTask";
		}

		var name = $("#taskName").val();
		var desc = $("#taskDescription").val();
		var startDate = $("#taskStartDate").val();
		var startTime = $("#taskStartTime").val();
		var endDate = $("#taskEndDate").val();
		var endTime = $("#taskEndTime").val();
		var statusIndex = $("#taskStatus")[0].selectedIndex;
		var status = $("#taskStatus").children().eq(statusIndex).val().trim();
		if(checkTaskParameters(name, desc, startDate, startTime, endDate, endTime, status)) {
			$.ajax({
				url: url,
				data: formData,
				method: "post",
				success: function(result) {
					if(result.resultCode == 0) {
						console.log("success");
						location.reload();
					}else {
						console.log("save task info error, error code : " + result.resultCode + ";error message: " + result.message);
					}
				} 
			});
		}

	}

	function deleteTask(taskId) {
		$.ajax({
			url: "DeleteTask?taskId=" + taskId,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("delete task success");
					location.reload();
				}else {
					console.log("delete task error");
					alert("删除失败, 请重试");
				}
			}
		});
	}

	function deleteProject(projectId) {
		$.ajax({
			url: "DeleteProject?projectId=" + projectId,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("delete project success");
					location.reload();
				}else {
					console.log("delete project fail");
				}
			}
		});
	}

	function checkProjectParameters(name, desc, startDate, startTime, endDate, endTime, progress) {
		if(name == undefined || name.trim() == "") {
			alert("项目名称不能为空");
			return false;
		}
		if(desc == undefined || desc.trim() == "") {
			alert("项目描述不能为空");
			return false;
		}
		if(startDate == undefined || startDate.trim() == "") {
			alert("开始日期不能为空");
			return false;
		}
		if(!validateDateFormat(startDate)) {
			alert("开始日期格式错误");
			return false;
		}
		if(startTime == undefined || startTime.trim() == "") {
			alert("开始时间不能为空");
			return false;
		}
		if(!validateTimeFormat(startTime)) {
			alert("开始时间格式错误");
			return false;
		}
		if(endDate == undefined || endDate.trim() == "") {
			alert("结束日期不能为空");
			return false;
		}
		if(!validateDateFormat(endDate)) {
			alert("结束日期格式错误");
			return false;
		}
		if(!compareDate(startDate, startTime, endDate, endTime)) {
			alert("结束时间不能早于开始时间");
			return false;
		}

		if(endTime == undefined || endTime.trim() == "") {
			alert("结束时间不能为空");
			return false;
		}
		if(!validateTimeFormat(endTime)) {
			alert("结束时间格式错误");
			return false;
		}
		if(progress == undefined || progress.trim() == "") {
			alert("进度不能为空");
			return false;
		}
		return true;
	}

	function checkTaskParameters(name, desc, startDate, startTime, endDate, endTime, status) {
		if(name == undefined || name.trim() == "") {
			alert("任务名称不能为空");
			return false;
		}
		if(desc == undefined || desc.trim() == "") {
			alert("任务描述不能为空");
			return false;
		}
		if(startDate == undefined || startDate.trim() == "") {
			alert("开始日期不能为空");
			return false;
		}
		if(!validateDateFormat(startDate)) {
			alert("开始日期格式错误");
			return false;
		}
		if(startTime == undefined || startTime.trim() == "") {
			alert("开始时间不能为空");
			return false;
		}
		if(!validateTimeFormat(startTime)) {
			alert("开始时间格式错误");
			return false;
		}
		if(endDate == undefined || endDate.trim() == "") {
			alert("结束日期不能为空");
			return false;
		}
		if(!validateDateFormat(endDate)) {
			alert("结束日期格式错误");
			return false;
		}
		if(!compareDate(startDate, startTime, endDate, endTime)) {
			alert("结束时间不能早于开始时间");
			return false;
		}

		if(endTime == undefined || endTime.trim() == "") {
			alert("结束时间不能为空");
			return false;
		}
		if(!validateTimeFormat(endTime)) {
			alert("结束时间格式错误");
			return false;
		}
		if(status == undefined || status.trim() == "") {
			alert("任务状态不能为空");
			return false;
		}
		return true;
	}

	$taskDeleteBtn.hide();

	loadEmployees(EMPLOYEE_TYPE_MEMBER);
	loadEmployees(EMPLOYEE_TYPE_OUT);

</script>
</body>
</html>

<jsp:include page="footer.jsp"></jsp:include>