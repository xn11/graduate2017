<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="memberEditForm">
	<table class="table table-responsive">
		<c:set value="${model.member}" var="member" />
		<tr>
			<c:if test="${member.id != null && member.id != 0}">
				<td><input name="memberId" type="hidden" value="${member.id}" /></td>
			</c:if>

			<td><input name="companyId" type="hidden"
				value="${member.companyId}" /></td>
		</tr>
		<tr>
			<td><label>所属公司</label></td>
			<td><label>${member.companyId}</td>
		</tr>
		<tr>
			<td><label>用户名</label></td>
			<td><input id="name" name="name" type="text" value="${member.name}" /></td>
		</tr>
		<tr>
			<td><label>工号</label></td>
			<td><input id="workId" name="workId" type="text" value="${member.workId}" /></td>
		</tr>
		<tr>
			<td><label>qq号</label></td>
			<td><input id="qqNumber" name="qqNumber" type="text"
				value="${member.qqNumber}" /></td>
		</tr>
		<tr>
			<td><label>微信号</label></td>
			<td><input id="wxNumber" name="wxNumber" type="text"
				value="${member.wxNumber}" /></td>
		</tr>
		<tr>
			<td><label>手机</label></td>
			<td><input id="phone" name="phone" type="text" value="${member.phone}" /></td>
		</tr>
		<tr>
			<td><label>密码</label></td>
			<td><input id="password" name="password" type="password" value="${member.password}" /></td>
		</tr>
	</table>
</form>
