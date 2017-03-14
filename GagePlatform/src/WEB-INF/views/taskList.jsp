<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> -->

<c:if test="${model.tasks != null}">
	<table
		class="table table-striped table-bordered table-hover table-responsive">
		<tr>
			<th>任务名称</th>
			<th>任务描述</th>
			<th>所属项目</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>当前状态</th>
		</tr>
		<c:forEach items="${model.tasks}" var="task">
			<tr>
				<td>${task.name}</td>
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
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${model.tasks == null}">
	<h3>暂无任务</h3>
</c:if>