<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
		
	<h3>编辑内容</h3>
	<hr />
	<div class="row">
	<!-- 加载编辑器的容器 -->
	<form id="newsForm" action="CreateNews" method="post">
	<input name="companyId" type="hidden"
				value="${model.admin.companyId}" />
	<div>
		<label for="title">标题：</label>
		<input type="text" name="title" />
	</div>
	    <script id="container" name="content" type="text/plain">这里写你的初始化内容</script>
	    <!-- 配置文件 -->
	    <script type="text/javascript" src="<c:url value="/resources/ueditor/ueditor.config.js" />"></script>
	    <!-- 编辑器源码文件 -->
	    <script type="text/javascript" src="<c:url value="/resources/ueditor/ueditor.all.js" />"></script>
	    <!-- 实例化编辑器 -->
	    <script type="text/javascript">
	        var ue = UE.getEditor("container", {
	        	toolbars: [
			    ['fullscreen', 'source', 'undo', 'redo'],
			    ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']],
			    autoHeightEnabled: true,
			    autoFloatEnabled: true,
			    wordCount: false,
			    elementPathEnabled: false
				});
	    </script>
		</form>
		    <a id="submitBtn" href="#" class="btn btn-success btn-large submit-button">提交</a>
	    </div>
	</div>

	<script type="text/javascript">
		var $newsForm = $("#newsForm");
		var $submitBtn = $("#submitBtn");

		$submitBtn.click(function(e) {
			console.log("submit button click");
			var data = $newsForm.serialize();
			$.ajax({
				url: "CreateNews",
				data: data,
				method: "post",
				success: function(result) {
					if(result.resultCode == 0) {
						alert("创建成功");
					}else {
						alert("创建失败");
					}
					location.href = "NewsList?companyId=${model.admin.companyId}";
				}
				
			});
		});
	</script>
	</body>
</html>
