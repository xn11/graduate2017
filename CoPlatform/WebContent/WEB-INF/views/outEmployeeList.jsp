<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
		<h3>外聘人员列表</h3>
		<hr />
		<table
			class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>姓名</th>
				<th>微信号</th>
				<th>qq号</th>
				<th>手机</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${model.outEmployees}" var="outEmployee">
				<tr>
					<td><a class="outEmployeeInfo" href="#"
						outEmployeeId="${outEmployee.id}">${outEmployee.name}</a></td>
					<td>${outEmployee.wxNumber}</td>
					<td>${outEmployee.qqNumber}</td>
					<td>${outEmployee.phone}</td>
					<td><a class="outEmployeeTask" href="#"
						outEmployeeId="${outEmployee.id}">查看任务</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div id="outEmployeeInfoModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">职员信息</h4>
				</div>
				<div id="outEmployeeInfoContent" class="modal-body"></div>
			</div>
		</div>
	</div>

	<div id="outEmployeeTaskModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">任务信息</h4>
				</div>
				<div id="outEmployeeTaskContent" class="modal-body"></div>
				<!--       <div class="modal-footer">
        <button id="memberEditSubmit" type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
      </div> -->
			</div>
		</div>
	</div>

	<script type="text/javascript">
	var $outEmployeeInfoLink = $(".outEmployeeInfo");
	var $outEmployeeInfoModal = $("#outEmployeeInfoModal");
	var $outEmployeeInfoContent = $("#outEmployeeInfoContent");

	var $outEmployeeTaskLink = $(".outEmployeeTask");
	var $outEmployeeTaskModal = $("#outEmployeeTaskModal");
	var $outEmployeeTaskContent = $("#outEmployeeTaskContent");

	$outEmployeeInfoLink.click(function(e) {
		var outEmployeeId = $(this).attr("outEmployeeId");
		console.log("get outEmployeeId info, outEmployeeId: " + outEmployeeId);
		$outEmployeeInfoContent.load("GetOutEmployeeInfo?outEmployeeId=" + outEmployeeId, function(response, status, xhr) {
			if(status == "error") {
				$outEmployeeInfoContent.load("Error");
			}
		});
		$outEmployeeInfoModal.modal();
	});

	$outEmployeeTaskLink.click(function(e) {
		var outEmployeeId = $(this).attr("outEmployeeId");
		var companyId = "${model.admin.companyId}";
		console.log("outEmployeeTask click, outEmployeeId: " + outEmployeeId);
		$outEmployeeTaskContent.load("GetOutEmployeeTasks?companyId=" + companyId + "&outEmployeeId=" + outEmployeeId, function(response, status, xhr) {
			if(status == "error") {
				$outEmployeeTaskContent.load("Error");
			}
		});
		$outEmployeeTaskModal.modal();
	});
</script>
</body>
</html>

<jsp:include page="footer.jsp"></jsp:include>