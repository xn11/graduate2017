<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> -->

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
		<h3>任务日志</h3>
		<hr />
		<c:forEach items="${model.taskLogs}" var="taskLog">
			<c:set value="${taskLog.key}" var="task" />
			<h4>${task.name}</h4>
			<table
				class="table table-striped table-bordered table-hover table-responsive">
				<tr>
					<th>标题</th>
					<th>描述</th>
					<th>任务Id</th>
					<th>原状态</th>
					<th>当前状态</th>
					<th>记录时间</th>
				</tr>
				<c:forEach items="${taskLog.value}" var="log">
					<tr>
						<td>${log.title}</td>
						<td>${log.description}</td>
						<td>${log.task.id}</td>
						<td>${log.originStatus}</td>
						<td>${log.currentStatus}</td>
						<td>${log.createdTime}</td>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
	</div>


	<script type="text/javascript">

</script>
</body>
</html>

<jsp:include page="footer.jsp"></jsp:include>