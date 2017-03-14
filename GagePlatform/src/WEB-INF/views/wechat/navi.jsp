<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set value="${sessionScope.admin}" var="currentAdmin" />

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">

		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">任务协同平台</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false">员工管理
				</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="MemberList?companyId=${currentAdmin.company.id}">公司职员</a></li>
						<li><a
							href="OutEmployeeList?companyId=${currentAdmin.company.id}">外聘人员</a></li>
					</ul></li>
				<li><a href="WorkList?companyId=${currentAdmin.company.id}">工作管理</a></li>
				<!-- <li><a href="TaskList?projectId=1">任务管理</a></li> -->
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false">日志管理</a>
					<ul class="dropdown-menu" role="menu">
						<li><a
							href="ProjectLogList?companyId=${currentAdmin.company.id}">项目日志</a></li>
						<li><a href="TaskLogList?taskId=1">任务日志</a></li>
					</ul></li>
			</ul>

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

  var adminName = "${sessionScope.admin.name}";
  console.log("adminName: " + adminName);
</script>