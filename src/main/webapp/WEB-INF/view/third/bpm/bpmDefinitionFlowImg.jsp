<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
	<script type="text/javascript">
	var _height=${shapeMeta.height};
	function setIframeHeight(){
		var mainIFrame = window.parent.document.getElementById("flowchart");
		if(!mainIFrame)return;
		mainIFrame.style.height=(parseInt(_height)+100)+'px';
	};
	
	$(function(){
		if(self!=top){
			setIframeHeight();
		}
	});
	</script>
	<title>流程示意图</title>
</head>
<body >
	<div style="padding-top:40px;background-color: white;">
		<div style="position: relative;background:url('<%=basePath%>/bpmImage?definitionId=${actDefId}') no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
			 
		</div>
	</div>
</body>
</html>