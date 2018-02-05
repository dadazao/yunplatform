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
	ns.system.loadSubList = function(){
		$("#subListDiv").loadUrl('<%=basePath %>/pages/system/authCustomerHadware/list.action?authCustomerId='+$("#domainId").val());
	}
	//加载编辑区
	ns.system.loadSubEdit = function(domainId){
		if(domainId){
			$("#subEditDiv").loadUrl('<%=basePath %>/pages/system/authCustomerHadware/edit.action?authCustomerHadwareId='+domainId);
		}else{
			$("#subEditDiv").loadUrl('<%=basePath %>/pages/system/authCustomerHadware/edit.action');
		}
	}
	
	$(function(){
		ns.system.loadSubEdit();
		ns.system.loadSubList();
		ns.common.mouseForButton();
	});
</script>
</head>
<body>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" align="left" class="Input_Table" >
	<tbody>
	<tr>
		<td>
			<div id="subEditDiv">
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div id="subListDiv">
			</div>
		</td>
	</tr>
	</tbody>
	</table>
</body>
</html>