<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<html>
	<head>
		<link href="<%=basePath %>/themes/red/style.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="<%=basePath %>/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
		<link href="<%=basePath %>/themes/css/table.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="<%=basePath %>/themes/css/jquery-ui.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="<%=basePath %>/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/js/uploadify/uploadify.css">
		
		<script src="<%=basePath %>/js/jquery/jquery-1.7.1.js" type="text/javascript"></script>
		<script type="text/javascript">
		var _curPrintHighTr = null;
		
		//初始化对象
		function initObject() {
			
		}
		
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
			<input type="hidden" name="recordId" value="">
			<input name="formList" type="hidden" id="formList" value="" onpropertychange="formListChange(this)"/>
			<table width="100%" style="width:100%;background-color:#FFFFFF;" >
			  <tr>
			    <td width="18%"  align="left" valign="top" id="webOfficeColumn"></td>
			    <td width="82%" height="500px">
				    
			    </td>
			  </tr>
			</table>
		</form>
	</body>
</html>
