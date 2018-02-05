<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;

	String recordId = request.getParameter("recordId");
	String editType = request.getParameter("editType");
	String fileType = request.getParameter("fileType");
	
	if(editType == null){
		editType = "1,1";
	}
%>
<html>
	<head>
		<script src="<%=basePath%>/js/jquery/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/cloudstong/yun.iweboffice.js" type="text/javascript"></script>
		<script type="text/javascript">
		var path = "<%=basePath%>";
		var recordId = "<%=recordId%>";
		var editType = "<%=editType%>";
		var fileType = "<%=fileType%>";
		var pageType = 'view';
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
			    <td width="100%" height="500px">
				    <script src="<%=basePath%>/js/iweboffice/iWebOffice2009.js"></script>
			    </td>
			  </tr>
			</table>
		</form>
	</body>
</html>
