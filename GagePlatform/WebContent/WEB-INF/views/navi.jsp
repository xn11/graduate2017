<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<nav class="navbar navbar-default navbar-fixed-top" style="">
	<div class="container-fluid nav-container">
		<div class="row">
			<div class="col-xs-2">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="Login"> <!-- 任务协同平台 --> <img
						alt="任务协同平台" class="logo"
						src="<c:url value="/resources/images/logo.png" />">
					</a>
				</div>
			</div>
			<div class="col-xs-10">
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav" style="width: 100%">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">员工管理
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									href="MemberList?companyId=${model.admin.companyId}">公司职员</a></li>
								<li><a
									href="OutEmployeeList?companyId=${model.admin.companyId}">外聘人员</a></li>
							</ul></li>
						<li><a href="TaskTree?companyId=${model.admin.companyId}">工作管理</a></li>
						<!-- <li><a href="TaskList?projectId=1">任务管理</a></li> -->
						<li><a href="ProjectLogList?companyId=${model.admin.companyId}">日志管理</a></li>
						<!-- <li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false"></a>
							<ul class="dropdown-menu" role="menu">
								
								<li><a href="TaskLogList?taskId=1">任务日志</a></li>
							</ul></li> -->
						<li><a href="NewsList?companyId=${model.admin.companyId}">新闻资讯</a></li>
						<li style="float: right">

							<ul class="nav-rightblock nav navbar-nav">

								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown" role="button" aria-expanded="false">
										<span style="padding-right: 45px;"><img alt="用户" class="navi-user-pic"
											src="<c:url value="/resources/images/user_default.png" />"style="  margin-top: 5px;"></span><span>${model.admin.name}</span>
								</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="Logout">退出</a></li>
									</ul></li>

								<%-- <li class="nav-username">${model.admin.name}</li>
					<li class="nav-username">&nbsp;&nbsp;|</li>
					<li class="nav-logout"><a href="Logout">注销</a></li> --%>
							</ul>
						</li>
					</ul>
					<!-- 	<div style="margin-top: 4px;">
					<img alt="用户" class="navi-user-pic" src="/resources/images/user21.png">
				</div> -->

				</div>
			</div>
			<%-- <div class="col-xs-2 row">

				<div class="col-xs-2" style="margin-top: 4px;">
					<img alt="用户" class="navi-user-pic" src="/resources/images/user21.png">
				</div>
				
				<div class="col-xs-10">
					<ul class="nav-rightblock nav navbar-nav">
	
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">
								${model.admin.name} </a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="Logout">退出</a></li>
							</ul></li>

						<li class="nav-username">${model.admin.name}</li>
					<li class="nav-username">&nbsp;&nbsp;|</li>
					<li class="nav-logout"><a href="Logout">注销</a></li>
					</ul>
				</div>
			</div> --%>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>

<script type="text/javascript">
	var $nav;
	var $navItems;

	$(function() {
		$nav = $("ul.nav");
		$navItems = $("ul.nav li");

		$navItems.click(function(e) {
			var index = $(this).index();
			console.log(index);
			$navItems.removeClass("active");
			$nav.children().eq(index).addClass("active");
		});
	});

	function setCurrentNavItem(index) {
		$navItems.removeClass("active");
		$nav.children().eq(index).addClass("active");
	}

	var adminName = "${model.admin.name}";
	console.log("adminName: " + adminName);
</script>