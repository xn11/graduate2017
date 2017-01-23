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
	href="<c:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap-theme.min.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/co_platform.css" />" />


<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.validate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.timepicker.js" />"></script>

<title>南大任务协同平台-系统管理员登录</title>
</head>
<body class="login-body">
	<div class="container center">
		<!-- <h2>任务协同平台</h2>
		<h4>登录</h4> -->
		<div class="row">
		<img src="<c:url value="/resources/images/login_logo.png" />" alt="任务协同平台" />
		</div>
		<div class="row col-xs-4"></div>
		<div class="row col-xs-4">
			<form id="loginForm" action="Login" method="post">
				<div class="input-group input-item">
					<span class="input-group-addon">用户名</span> <input type="text"
						class="form-control" id="userName" name="userName" placeholder="用户名"
						aria-describedby="basic-addon2" />
				</div>
				<div class="input-group input-item">
					<span class="input-group-addon">密码</span> <input type="password"
						class="form-control" id="password" name="password"
						placeholder="密码" aria-describedby="basic-addon2" />
				</div>
				<div class="input-item">
					<a id="login" class="btn btn-lg login btn-success" href="#"> 登
						录 </a>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">

	var $userNameInput;
	var $passwordInput;
	var $loginBtn;
	var $loginForm;

	$(function() {
		$userNameInput = $("#userName");
		$passwordInput = $("#password");
		$loginLink = $("#login");
		$loginForm = $("#loginForm");
		
		$loginLink.click(function(e) {
			console.log("log");
			var userName = $userNameInput.val();
			var password = $passwordInput.val();
			console.log("userName: " + userName);
			console.log("password: " + password);
			if(checkParameters(userName, password)) {
				userName = userName.trim();
				password = password.trim();
				$loginForm.submit();	
			}
		}); 

		$passwordInput.keyup(function(e){
			if(e.keyCode == 13) {
				$loginLink.trigger("click");
			}
		});


	});

	function checkParameters(userName, password) {
		if(userName == undefined || userName.trim() == "") {
			alert("用户名不能为空");
			return false;
		}

		if(password == undefined || password == "") {
			alert("密码不能为空!");
			return false;
		}
		return true;
	}
</script>
</body>
</html>