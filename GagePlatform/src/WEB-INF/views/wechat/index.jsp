<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<table>
		<tr>
			<th>Id</th>
			<th>邮箱</th>
			<th>用户名</th>
			<th>密码</th>
		</tr>
		<c:forEach items="${model.admins}" var="admin">
			<tr>
				<td>${admin.id}</td>
				<td>${admin.mail}</td>
				<td>${admin.name}</td>
				<td>${admin.password}</td>
			</tr>
		</c:forEach>
	</table>
</body>
<<<<<<< HEAD
</html>
=======
</html>
>>>>>>> 086b6134dc66cefedadb25826ef0434c91670619
