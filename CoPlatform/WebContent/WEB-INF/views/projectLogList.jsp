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
		<h3>项目日志</h3>
		<hr />
		<div class="container-fluid">
		
			<c:forEach items="${model.projectLogs}" var="projectLog">
				<c:set value="${projectLog.key}" var="project" />
				<h4>${project.name}</h4>
				<table
					class="table table-striped table-bordered table-hover table-responsive">
					<tr>
						<th>标题</th>
						<th>描述</th>
						<th>项目Id</th>
						<th>记录时间</th>
					</tr>
					<c:forEach items="${projectLog.value}" var="log">
						<tr>
							<td>${log.title}</td>
							<td>${log.description}</td>
							<td>${log.project.id}</td>
							<td>${log.createdTime}</td>
						</tr>
					</c:forEach>
				</table>
			</c:forEach>
		</div>
	</div>


	
<script type="text/javascript">

</script>
</body>
</html>

<jsp:include page="footer.jsp"></jsp:include>