<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Date sd = new Date(System.currentTimeMillis());
	Date ed = new Date(System.currentTimeMillis()+24*3600*1000);
	String starttime = format.format(sd);
	String endtime = format.format(ed);
%>
<html>
	<head>
		<title>FineReport Demo</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script language="javascript">
			//cjkEncode方法的实现代码，放在网页head中或者用户自己的js文件中  
			function cjkEncode(text) {
				if (text == null) {
					return "";
				}
				var newText = "";
				for ( var i = 0; i < text.length; i++) {
					var code = text.charCodeAt(i);
					if (code >= 128 || code == 91 || code == 93) { //91 is "[", 93 is "]".         
						newText += "[" + code.toString(16) + "]";
					} else {
						newText += text.charAt(i);
					}
				}
				return newText;
			}
			
			$(function(){
				var date = new Date();
				var addr = cjkEncode("ReportServer?reportlet=log.cpt&starttime=<%=starttime%>&endtime=<%=endtime%>");
				document.getElementById("ifrWeboffice").src = addr;
			})
		</script>
	</head>
	<body>
		<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="100%"></iframe>
	</body>
</html>
