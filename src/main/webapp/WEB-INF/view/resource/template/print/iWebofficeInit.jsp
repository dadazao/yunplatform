<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
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
		<script type="text/javascript">
			function qpxs(){
				alert('请选择一个模板!');
			}
			
			function WebOpenPrint(){
				alert('请选择一个模板!');
			}
		</script>
	</head>
	<body>
		<form name="webform" method="post">
			<table style="width:100%;background-color:#FFFFFF;" >
			  <tr>
			    <td height="600px">
				    <script src="<%=path %>/js/iweboffice/iWebOffice2009.js"></script>
			    </td>
			  </tr>
			</table>
		</form>
	</body>
</html>
