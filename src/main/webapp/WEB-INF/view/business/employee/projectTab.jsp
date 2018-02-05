<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

	//加载子列表
	ns.project.loadSubList = function(){
		$("#projectSubListDiv").loadUrl('<%=basePath %>/pages/business/employee/project/sublist.action?employeeId='+$("#domainId").val());
	}
	//加载编辑区
	ns.project.loadSubEdit = function(domainId){
		if(domainId){
			$("#projectSubEditDiv").loadUrl('<%=basePath %>/pages/business/employee/project/edit.action?projectId='+domainId);
		}else{
			$("#projectSubEditDiv").loadUrl('<%=basePath %>/pages/business/employee/projectEdit.jsp');
		}
	}
	
	$(function(){
		ns.project.loadSubEdit();
		ns.project.loadSubList();
		ns.common.mouseForButton();
	});
</script>
</head>
<body>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" align="left" class="Input_Table" >
	<tbody>
	<tr>
		<td>
			<div id="projectSubEditDiv">
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div id="projectSubListDiv">
			</div>
		</td>
	</tr>
	</tbody>
	</table>
</body>
</html>