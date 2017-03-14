<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- <jsp:include page="header.jsp" flush="true" /> --%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link rel="stylesheet"
	href="<c:url value="/resources/css/wechat_news.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.timepicker.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/ui_lightness/jquery-ui.min.css" />" />


<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/datepicker-zh-CN.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.validate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.timepicker.js" />"></script>

<title>任务协同平台</title>
</head>

<body>


	<div id="borderLogo">
		<div class="logoImg"></div>
	</div>

	<div id="content" class="main fontSize2">
		<p class="title" align="left">${model.wxnews.title}</p>
		<span class="src">发布日期：${model.wxnews.createdTime}</span>

		


		<p class="text"></p>
		
		<p></p>
		<p class="text"></p>
		<div class="preLoad" style="width: 100%; min-height: 129px">
			<div class="img">
				<img src="http://njucowork-pic.stor.sinaapp.com/news${model.newspic}.jpg"
					preview-src="http://inews.gtimg.com/newsapp_match/0/44857511/1000"
					style="width: 100%; display: block;">
			</div>
			<div class="tip">Co-Work</div>
		</div>
		<p class="text">${model.wxnews.content}</p>
		<p></p>
		</div>
</body>
</html>