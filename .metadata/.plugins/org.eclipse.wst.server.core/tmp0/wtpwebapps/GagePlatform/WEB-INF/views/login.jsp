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

<title>南大任务协同平台-用户登录</title>
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
					<span class="input-group-addon">邮箱</span> <input type="text"
						class="form-control" id="mail" name="mail" placeholder="邮箱"
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

	var $mailInput;
	var $passwordInput;
	var $loginBtn;
	var $loginForm;

	var MAIL_PATTERN = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	
	$(function() {
		$mailInput = $("#mail");
		$passwordInput = $("#password");
		$loginLink = $("#login");
		$loginForm = $("#loginForm");
		
		$loginLink.click(function(e) {
			console.log("log");
			var mail = $mailInput.val();
			var password = $passwordInput.val();
			console.log("mail: " + mail);
			console.log("password: " + password);
			if(checkParameters(mail, password)) {
				mail = mail.trim();
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

	function checkParameters(mail, password) {
		if(mail == undefined || mail.trim() == "") {
			alert("邮箱不能为空");
			return false;
		}

		if(!MAIL_PATTERN.test(mail)) {
			alert("邮箱格式不正确");
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