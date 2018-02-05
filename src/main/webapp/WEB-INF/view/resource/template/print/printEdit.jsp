<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;

	String recordId = request.getParameter("recordId");
	String editType = request.getParameter("editType");
	String op = request.getParameter("op");
	
	if(editType == null){
		editType = "1,1";
	}
%>
<html>
	<head>
		<link media="screen" type="text/css" rel="stylesheet" href="<%=path %>/themes/red/style.css">
		<link media="print" type="text/css" rel="stylesheet" href="<%=path %>/themes/css/print.css">
		<link media="screen" type="text/css" rel="stylesheet" href="<%=path %>/themes/css/jquery-ui.css">
		<link media="screen" type="text/css" rel="stylesheet" href="<%=path %>/themes/css/core.css">
		<link media="screen" type="text/css" rel="stylesheet" href="<%=path %>/js/uploadify/uploadify.css">
		
		<script src="<%=path%>/js/jquery/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=path%>/js/cloudstong/yun.iweboffice.js" type="text/javascript"></script>
		
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
			var path = "<%=path%>";
			var recordId = "<%=recordId%>";
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
			<input name="formList" type="hidden" id="formList" value="" onpropertychange="formListChange(this)"/>
			<table width="100%" style="width:100%;background-color:#FFFFFF;" >
			 <tr id="iWebOfficeTip" style="display: none;font-size: 12px;">
			    <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;如果不能正常自动安装iWebOffice2009插件，请<strong><a href="<%=path%>/js/iweboffice/InstallClient.zip">下载</a></strong>本地安装程序，如果插件异常请运行KMWUninstall.exe卸载程序</td>
			  </tr>
			  <tr>
			    <td width="18%"  align="left" valign="top" id="webOfficeColumn"></td>
			    <td width="82%" height="500px">
				    <script src="<%=path%>/js/iweboffice/iWebOffice2009.js"></script>
			    </td>
			  </tr>
			</table>
		</form>
	</body>
</html>
