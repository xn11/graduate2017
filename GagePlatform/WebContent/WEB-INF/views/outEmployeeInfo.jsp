<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-responsive">
	<c:set value="${model.outEmployee}" var="outEmployee" />
	<tr>
		<td><label>姓名</label></td>
		<td><label>${outEmployee.name}</label></td>
	</tr>
	<tr>
		<td><label>qq号</label></td>
		<td><label>${outEmployee.qqNumber}</label></td>
	</tr>
	<tr>
		<td><label>微信号</label></td>
		<td><label>${outEmployee.wxNumber}</label></td>
	</tr>
	<tr>
		<td><label>手机</label></td>
		<td><label>${outEmployee.phone}</label></td>
	</tr>
</table>
