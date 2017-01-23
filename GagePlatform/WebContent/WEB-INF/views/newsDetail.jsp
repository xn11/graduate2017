<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
	<div class="news_title">
		<p>${model.news.title}</p>
		<p class="time">${model.news.createdTime}</p>
	</div>
	<div class="news_content">
		${model.news.content}
	</div>
	</div>

</body>
</html>

<jsp:include page="footer.jsp"></jsp:include>