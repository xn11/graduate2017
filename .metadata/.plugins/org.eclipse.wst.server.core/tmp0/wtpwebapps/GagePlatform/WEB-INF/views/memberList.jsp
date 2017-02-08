<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
		<h3>职员列表<a id="memberCreate" href="#" class="btn btn-primary create-button">创建员工</a></h3>
		<hr />
		<table
			class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>用户名</th>
				<th>工号</th>
				<th>微信号</th>
				<th>qq号</th>
				<th>手机</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${model.members}" var="member">
				<tr>
					<td class="memberNameTd"><a class="memberInfo" href="#"
						memberId="${member.id}">${member.name}</a> <span><a
							class="memberDelete" href="#"><img
								src="<c:url value='/resources/images/delete.png' />"></a></span></td>
					<td>${member.workId}</td>
					<td>${member.wxNumber}</td>
					<td>${member.qqNumber}</td>
					<td>${member.phone}</td>
					<td><a class="memberTask" href="#" memberId="${member.id}">查看任务</a></td>
				</tr>
			</c:forEach>
		</table>

	</div>

	<div id="memberEditModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">职员信息</h4>
				</div>
				<div id="memberEditContent" class="modal-body"></div>
				<div class="modal-footer">
					<button id="memberEditSubmit" type="button" class="btn btn-primary"
						data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>

	<div id="memberCreateModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">职员信息</h4>
				</div>
				<div id="memberCreateContent" class="modal-body"></div>
				<div class="modal-footer">

					<p id="warningMessage" class="text-danger"></p>
					<button id="memberCreateSubmit" type="button"
						class="btn btn-primary">创建</button>
				</div>
			</div>
		</div>
	</div>

	<div id="memberTaskModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">任务信息</h4>
				</div>
				<div id="memberTaskContent" class="modal-body"></div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	/*constants*/
	var SAVE_TYPE_CREATE = 0;
	var SAVE_TYPE_UPDATE = 1;

	/*components*/
	var $memberInfoLink = $(".memberInfo");
	var $memberEditModal = $("#memberEditModal");
	var $memberEditContent = $("#memberEditContent");
	var $memberEditSubmit = $("#memberEditSubmit");
	var $memberDeleteLink = $(".memberDelete");
	var $memberNameTd = $(".memberNameTd");

	var $memberTaskLink = $(".memberTask");
	var $memberTaskModal = $("#memberTaskModal");
	var $memberTaskContent = $("#memberTaskContent");

	var $memberCreateModal = $("#memberCreateModal");
	var $memberCreateContent = $("#memberCreateContent");
	var $memberCreateSubmit = $("#memberCreateSubmit");
	var $memberCreateBtn = $("#memberCreate");


	var $warningMessage = $("#warningMessage");


	$memberInfoLink.click(function(e) {
		var memberId = $(this).attr("memberId");
		console.log("get member info, memberId: " + memberId);
		$memberEditContent.load("GetMemberInfo?memberId=" + memberId, function(response, status, xhr) {
			if(status == "error") {
				$memberEditContent.load("Error");
			}
		});
		$memberEditModal.modal();
	});

	$memberEditSubmit.click(function(e) {
		saveMember($("#memberEditForm"), SAVE_TYPE_UPDATE);
	});

	$memberCreateBtn.click(function(e) {
		var companyId = "${model.admin.companyId}";
		$memberCreateContent.load("CreateMember?companyId=" + companyId, function(response, status, xhr) {
			if(status == "error") {
				$memberEditContent.load("Error");
			}
		});
		$memberCreateModal.modal();
	});

	$memberCreateSubmit.click(function(e) {
		saveMember($("#memberEditForm"), SAVE_TYPE_CREATE);
	});

	$memberNameTd.on("mouseover", function(e) {
		$(this).find("a.memberDelete").show();
	});

	$memberNameTd.on("mouseout", function(e) {
		$(this).find("a.memberDelete").hide();
	});

	$memberDeleteLink.hide();

	$memberDeleteLink.click(function(e) {
		console.log("delete click");
		var memberId = $(this).parent().prev().attr("memberId");
		console.log("memberId: " + memberId);
		deleteMember(memberId);
	});

	$memberTaskLink.click(function(e) {
		var memberId = $(this).attr("memberId");
		console.log("memberTask click, memberId: " + memberId);
		$memberTaskContent.load("GetMemberTasks?memberId=" + memberId, function(response, status, xhr) {
			if(status == "error") {
				$memberTaskContent.load("Error");
			}
		});
		$memberTaskModal.modal();
	});


	function saveMember($form, saveType) {
		var url;
		if(saveType == SAVE_TYPE_CREATE) {
			url = "CreateMember";
		}else {
			url = "UpdateMember";
		}

		var name = $("#name").val();
		var workId = $("#workId").val();
		var qqNumber = $("#qqNumber").val();
		var wxNumber = $("#wxNumber").val();
		var phone = $("#phone").val();
		var password = $("#password").val();

		if(checkMemberParameters(name, workId, qqNumber, wxNumber, phone, password)) {
			console.log("save member")
			var formData = $form.serialize();
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

	function deleteMember(memberId) {
		$.ajax({
			url: "DeleteMember?memberId=" + memberId,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("delete member success");
					location.reload();
				}else {
					console.log("delete member error");
					alert("删除失败, 请重试");
				}
			}
		});
	}

	function checkMemberParameters(name, workId, qqNumber, wxNumber, phone, password) {
		if(name == undefined || name.trim() == "") {
			alert("用户名不能为空");
			return false;
		}

		if(workId == undefined || workId.trim() == "") {
			alert("工号不能为空");
			return false;
		} 

		if((qqNumber == undefined || qqNumber.trim() == "")
			&& (wxNumber == undefined || wxNumber.trim(0 == ""))) {
			alert("qq号与微信号不能同时为空");
			return false;
		}

		if(phone == undefined || phone.trim() == "") {
			alert("手机号不能为空");
			return false;
		}

		if(!isNumber(phone) || !checkLength(phone, 11, 11)) {
			alert("手机号必须为11位数字");
			return false;
		}

		console.log("password: " + password);	
		if(password == undefined || password.trim() == "") {
			alert("密码不能为空");
			return false;
		}

		return true;
	}

	function showWarningMessage(message) {
		$warningMessage.text(message);
	}

	function clearWarningMessage() {
		$warningMessage.text("");
	}
</script>
</body>
</html>

<jsp:include page="footer.jsp"></jsp:include>