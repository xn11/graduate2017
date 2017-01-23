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
		<h3>最新资讯<a id="newsManage" href="EditNews?companyId=${model.admin.companyId}" class="btn btn-primary create-button">创建</a></h3>
		<hr />
		<table
			class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>标题</th>
				<th>创建时间</th>
				<th>发布时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${model.newsList}" var="news">
				<tr>
					<td><a href="NewsDetail?newsId=${news.id}" target="_blank">${news.title}</a></td>
					<td><fmt:formatDate value="${news.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${news.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><button type="button" class="btn btn-danger btn-sm deleteButton" newsId="${news.id}">删除</button></td>
				</tr>
			</c:forEach>
		</table>

		<h3>未发布资讯</h3>
		<hr />
		<c:if test="${model.notPublishedNewsList != null}">
			<table class="table table-striped table-bordered table-hover table-responsive">
				<tr>
					<th>标题</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${model.notPublishedNewsList}" var="news">
					<tr>
						<td><a href="NewsDetail?newsId=${news.id}" target="_blank">${news.title}</a></td>
						<td><fmt:formatDate value="${news.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><button type="button" class="btn btn-success btn-sm publishButton" newsId="${news.id}">发布</button>
						<button type="button" class="btn btn-danger btn-sm deleteButton" newsId="${news.id}">删除</button></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>

<script type="text/javascript">
var $publishButton = $(".publishButton");
var $deleteButton = $(".deleteButton");

$publishButton.click(function(e) {
	var newsId = $(this).attr("newsId");
	console.log("click publish newsId: " + newsId);
	$.ajax({
		url: "SubscribeNews?newsId=" + newsId,
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("success");
				alert("发布资讯成功");
				location.reload();
			}else {
				console.log("publish news error, error code : " + result.resultCode + ";error message: " + result.message);
			}
		} 
	});
});

$deleteButton.click(function(e) {
	var newsId = $(this).attr("newsId");
	console.log("click delete newsId: " + newsId);
	$.ajax({
		url: "DeleteNews?newsId=" + newsId,
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("success");
				alert("删除资讯成功");
				location.reload();
			}else {
				console.log("delete news error, error code : " + result.resultCode + ";error message: " + result.message);
			}
		} 
	});
});
</script>
</body>
</html>

