<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<div class="container-body">
		<h3>公司列表</h3>
		<hr />
		<table
			class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>名称</th>
				<th>简介</th>
				<th>联系电话</th>
				<th>地址</th>
			</tr>
			<c:forEach items="${model.companys}" var="company">
				<tr>
					<td>${company.name}</td>
					<td>${company.description}</td>
					<td>${company.phone}</td>
					<td>${company.address}</td>
				</tr>
			</c:forEach>
		</table>

	</div>


</body>
</html>