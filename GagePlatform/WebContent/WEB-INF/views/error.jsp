<%@ page contentType="text/html; charset=gb2312" language="java"
	import="java.io.*" errorPage="error.jsp"%>
<html>
<head>
<title>自定义的错误页</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
</head>
<body marginwidth="0" leftmargin="0" bgcolor="ffffff">
	<table width="90%">
		<tbody>
			<tr>
				<td width="98%">这是自定义的错误页
					<hr>
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>
					<%   
      int status_code = -1;  
      String exception_info = null;  
      Exception theException = null;  
      status_code = ((Integer) request.getAttribute("javax.servlet.error.status_code"));  
      exception_info = (String) request.getAttribute("javax.servlet.error.message");  
      theException = (Exception) request.getAttribute("javax.servlet.error.exception_type");  
              
      out.println("<br><b>StatusCode:</b> " +  status_code);  
      out.println("<br><b>Exception:</b>" + exception_info);  
%>
				</TD>
			</TR>
			<TD>
				<HR>
			</TD>
			</TR>
		</TBODY>
	</TABLE>
</body>
</html>
