<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String reportName = request.getParameter("reportName");
	String op = request.getParameter("op");
	String bypagesize = request.getParameter("bypagesize");
	if(op.equals("true")){
		op = "&op=write";
	}else{
		op = "";
	}
	if(bypagesize.equals("true")){
		bypagesize = "&__bypagesize__=false";
	}else{
		bypagesize="";
	}
	String url = "ReportServer?reportlet="+reportName+op+bypagesize;
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script language="javascript">
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
				//var addr = cjkEncode("<%=url%>&ypUsrName="+cjkEncode("${user.fullname}"));
				var addr = cjkEncode("<%=url%>&ypUsrName="+"${user.fullname}");
				document.getElementById("ifrWeboffice").src = addr;
			})
			</script>
	</head>
	<body>
<%--	${user.fullname}--%>
		<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="1000" src=""></iframe>
	</body>
</html>
