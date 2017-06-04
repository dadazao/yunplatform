<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;

	String recordId = request.getParameter("recordId");
	String fileType = request.getParameter("fileType");
	String editType = request.getParameter("editType");
	String op = request.getParameter("op");
	
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
		</script>
		<script type="text/javascript">
			var path = "<%=basePath%>";
			var _curPrintHighTr = null;
			
			var path = "<%=basePath%>";
			var recordId = "<%=recordId%>";
			var fileType = "<%=fileType%>";
			var editType = "<%=editType%>";
			var _curPrintHighTr = null;
			var op = "<%=op%>";
			var pageType = 'newOrEdit';
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
	<body onload="initObject()" onUnload="UnLoad()">
		<form name="webform" method="post">
			<input type="hidden" name="recordId" value="<%=recordId %>">
			<table width="100%" style="width:100%;background-color:#FFFFFF;" >
				<tr id="iWebOfficeTip" style="display: none;font-size: 12px;">
			    	<td>&nbsp;&nbsp;&nbsp;&nbsp;如果不能正常自动安装iWebOffice2009插件，请<strong><a href="<%=basePath%>/js/iweboffice/InstallClient.zip">下载</a></strong>本地安装程序，如果插件异常请运行KMWUninstall.exe卸载程序</td>
			  	</tr>
			  	<tr>
			   		<td width="100%" height="500">
				    	<script src="<%=basePath%>/js/iweboffice/iWebOffice2009.js"></script>
			    	</td>
			  </tr>
			</table>
		</form>
	</body>
</html>
