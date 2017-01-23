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
	<c:forEach items="${model.projects}" var="project">
		<div class="bottom-line project-item">
			<div class="row">
				<div class="col-xs-6">
					<a class="projectInfo btn" projectId="${project.id}">${project.name}</a>
				</div>
				<div style="float:right">
				<div class="col-xs-4">
					<a class="projectDelete btn btn-default btn-sm" projectId="${project.id}">删除项目</a> 
				</div>	
				<div class="col-xs-2">
					<a 
					class="btn btn-info btn-sm taskListBtn" data-toggle="collapse"
					href="#projectTaskArea${project.id}" aria-expanded="false"
					aria-controls="collapseExample" projectId="${project.id}" style="margin-left:10px;">查看任务</a>
				</div>
				</div>					
			</div>
		 	<div id="projectTaskArea${project.id}" class="collapse taskList">
						
			</div>
		</div>
	</c:forEach>
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

<script type="text/javascript">
/*constants*/
var SAVE_TYPE_CREATE = 0;
var SAVE_TYPE_UPDATE = 1;

var $taskListBtn = $(".taskListBtn");

var $taskInfoLink = $(".taskInfo");
var $taskEditModal = $("#taskEditModal");
var $taskEditContent = $("#taskEditContent");
var $taskEditSubmit = $("#taskEditSubmit");

var $projectInfoLink = $(".projectInfo");
var $projectEditModal = $("#projectEditModal");
var $projectEditContent = $("#projectEditContent");
var $projectEditSubmit = $("#projectEditSubmit");
var $projectDeleteBtn = $(".projectDelete");

var $projectCreateBtn = $("#projectCreate");
var $projectCreateModal = $("#projectCreateModal");
var $projectCreateContent = $("#projectCreateContent");
var $projectCreateSubmit = $("#projectCreateSubmit");

var projectTaskMap = new Map();

$taskListBtn.click(function(e) {
	var projectId = $(this).attr("projectId");
	console.log("see project tasks, projectId:" + projectId);
	if(!projectTaskMap.get(projectId)) {
		loadData(projectId);
	}

	if($(this).text() == "查看任务") {
		$(this).text("隐藏任务");
	}else {
		$(this).text("查看任务");
	}
});


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

function loadData(projectId) {
	$.ajax({
		url: "GetTaskTree?projectId=" + projectId,
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("load data success");
				var taskList = result.data;
				console.log(taskList);
				var taskTree = buildTree(taskList);
				projectTaskMap.set(projectId, taskTree);
				var viewData = convertToNodes(taskTree, taskList);
				console.log(JSON.stringify(viewData));
				$("#projectTaskArea" + projectId).treeview({
					data: viewData,
					enableLinks: true,
					color: "#428bca",
				    icon: "glyphicon glyphicon-time",
				    state: {
				    	expanded: false
				    },
				    // expandIcon: 'glyphicon glyphicon-chevron-right',
				    // collapseIcon: 'glyphicon glyphicon-chevron-down',
					// nodeIcon: 'glyphicon glyphicon-bookmark',
					levels: 5});
			}else {
				console.log("error message: " + result.message);
				console.log("load data fail");
			}
		}
	});
}

function buildTree(taskList) {
	var taskTree = new Map();

	taskList.forEach(function(task) {
		if(task.parentId == 0) {
			taskTree.set(task.id, new Map());
		}else {
			console.log("parent task: " + task.parentId);
			console.log("child task: " + task.id + ", name: " + task.name);
			var parentTask = findNode(taskTree, task.parentId);
			console.log(parentTask == null);
			if(parentTask != undefined) {
				parentTask.set(task.id, new Map());
			}
		}
	});
	console.log(taskTree);
	return taskTree;
}

function findNode(taskTree, id) {
	console.log(taskTree);
	if(taskTree == null || taskTree == undefined || !id) {
		return null;
	}
	
	console.log(taskTree.get(id));

	// bug: cannot break in forEach, need to be fixed
	var result;
	if(taskTree.get(id) != undefined) {
		result = taskTree.get(id);
	}else {
		var subResult;
		taskTree.forEach(function(value, key) {
			subResult = findNode(value, id);
			if(subResult != undefined) {
				result = subResult;
				return false;
			} 
		});
	}
	return result;
}


function getTask(taskList, id) {
	var result;
	taskList.forEach(function(task) {
		if(task.id == id) {
			result = task;
			return;
		}
	});
	return result;
}

function convertToNodes(taskTree, taskList) {
	if(taskTree == null || taskTree == undefined || taskTree.size == 0) {
		console.log("taskTree null");
		return null;
	}
	var data = [];
	console.log(taskTree.size);
	taskTree.forEach(function(value, key) {
		var taskId = key;
		var task = getTask(taskList, taskId);
		var taskNode = {};
		
		taskNode["text"] = task.name;
		taskNode["href"] = "TaskDetail?taskId=" + taskId;
		// taskNode["icon"] = "glyphicon glyphicon-time";
		if(value) {
			var subData = convertToNodes(value, taskList);
			if(subData) {
				taskNode["nodes"] = subData;
			}
		}else {
			console.log("value null");
		}
		data.push(taskNode);
	});
	console.log(data);
	return data;
}

</script>

</body>