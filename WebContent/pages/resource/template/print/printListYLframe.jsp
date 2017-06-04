<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@page import="com.cloudstong.UUIDGeneratorUtil"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;

	String recordId = request.getParameter("recordId");
	String editType = request.getParameter("editType");
	
	if(editType == null){
		editType = "1,1";
	}
%>
<html>
	<head>
		<link media="screen" type="text/css" rel="stylesheet" href="<%=basePath %>/themes/red/style.css">
		<link media="print" type="text/css" rel="stylesheet" href="<%=basePath %>/themes/css/print.css">
		<link media="screen" type="text/css" rel="stylesheet" href="<%=basePath %>/themes/css/jquery-ui.css">
		<link media="screen" type="text/css" rel="stylesheet" href="<%=basePath %>/themes/css/core.css">
		<link media="screen" type="text/css" rel="stylesheet" href="<%=basePath %>/js/uploadify/uploadify.css">
		
		<script src="<%=basePath%>/js/jquery/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/cloudstong/yun.iweboffice.js" type="text/javascript"></script>
		
		<script language=javascript for=WebOffice event=OnToolsClick(vIndex,vCaption)>
		  //响应工具栏事件
		  if (vIndex==2){
		    SaveDocument(); 
		  }
		  if (vIndex==1){
		    WebSaveLocal(); 
		  }
		  if (vIndex==13){
		    WebOpenPrint(); 
		  }
		</script>
		
		<script type="text/javascript">
		var path = "<%=basePath%>";	
		var recordId = "<%=recordId%>";
		var editType = "<%=editType%>";
		var pageType = 'lbdy';
		$(function(){
			initObject();
			setListBookmarksStatus();
		})
		</script>
		<style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		-->
		</style>
	</head>
	<body onUnload="UnLoad()">
		<form name="webform" method="post">
			<input name="formId" id="formId" value="0" type="hidden"/>
			<textarea name="params" id="params" style="display:none;">0</textarea>
			<table style="width:100%;background-color:#FFFFFF;" >
			  <tr>
			    <td height="600px">
				    <script src="<%=basePath%>/js/iweboffice/iWebOffice2009.js"></script>
			    </td>
			  </tr>
			</table>
		</form>
	</body>
</html>
