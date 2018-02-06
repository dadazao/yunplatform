<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript">
		$(function(){
			$("#listId").width($("#desktop").width() - $("#treeId").width() - 11);
			$("#treeId").load("<%=basePath %>/pages/system/privilege/catalog.action");
			$("#listId").loadUrl("<%=basePath %>/pages/system/privilege/readme.action?height="+$("#container").height());	
		});
	</script>
</head>
<body>
	<div id="treeId" style="float:left;width:20%;"/>
	<div id="listId" style="float:left;width:80%;"/>
</body>
</html>