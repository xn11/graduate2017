<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />

<link rel="stylesheet"
	href="<c:url value="/resources/css/co_platform.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/co_platform_wx.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap-theme.min.css" />" />


<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.validate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.timepicker.js" />"></script>

<title>南大任务协同平台-登录</title>
</head>
<body class="login-body">
	<div class="container center container-body-mobile">
<!-- 		<h2>任务协同平台</h2>
		<h4>登录</h4> -->
		<div class="row">
			<img class="login-logo-mobile" src="<c:url value="/resources/images/login_logo.png" />" alt="任务协同平台" />
		</div>
		<div class="row col-xs-4"></div>
		<div class="row col-xs-4">
			<form id="loginForm" action="wechat" method="post">
				<input type="hidden" name="openId" value="${model.openId}" />
				<div class="input-group input-item-mobile">
					<span class="input-group-addon">手机</span> <input type="text"
						class="form-control" id="phone" name="phone" placeholder="手机"
						aria-describedby="basic-addon2" />
				</div>
				<div class="input-group input-item-mobile">
					<span class="input-group-addon">密码</span> <input type="password"
						class="form-control" id="password" name="password"
						placeholder="密码" aria-describedby="basic-addon2" />
				</div>
				<div class="input-item-mobile">
					<a id="login" class="btn btn-success login" href="#"> 登
						录 </a>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">

	var $phoneInput;
	var $passwordInput;
	var $loginBtn;
	var $loginForm;
	
	$(function() {
		$phoneInput = $("#phone");
		$passwordInput = $("#password");
		$loginLink = $("#login");
		$loginForm = $("#loginForm");
		
		$loginLink.click(function(e) {
			console.log("log");
			var phone = $phoneInput.val();
			var password = $passwordInput.val();
			console.log("phone: " + phone);
			console.log("password: " + password);
			$loginForm.submit();
		}); 

		$passwordInput.keyup(function(e){
			if(e.keyCode == 13) {
				$loginLink.trigger("click");
			}
		});
	});

</script>
</body>
</html>