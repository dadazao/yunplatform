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
	ns.${mainTable.parentClassName?uncap_first}.loadSubList${mainTable.variables.class} = function(){
		$("#${mainTable.variables.class?uncap_first}SubListDiv").loadUrl('${mainTable.namespace}/${mainTable.variables.class?uncap_first}/sublist.action?${mainTable.parentClassName?uncap_first}Id='+$("#domainId").val());
	}
	//加载编辑区
	ns.${mainTable.parentClassName?uncap_first}.loadSubEdit${mainTable.variables.class} = function(domainId){
		if(domainId){
			$("#${mainTable.variables.class?uncap_first}SubEditDiv").loadUrl('${mainTable.namespace}/${mainTable.variables.class?uncap_first}/edit.action?${mainTable.variables.class?uncap_first}Id='+domainId);
		}else{
			$("#${mainTable.variables.class?uncap_first}SubEditDiv").loadUrl('${mainTable.namespace}/${mainTable.variables.class?uncap_first}Edit.jsp');
		}
	}
	
	$(function(){
		ns.${mainTable.parentClassName?uncap_first}.loadSubEdit${mainTable.variables.class}();
		ns.${mainTable.parentClassName?uncap_first}.loadSubList${mainTable.variables.class}();
		ns.common.mouseForButton();
	});
</script>
</head>
<body>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" align="left" class="Input_Table" >
	<tbody>
	<tr>
		<td>
			<div id="${mainTable.variables.class?uncap_first}SubEditDiv">
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div id="${mainTable.variables.class?uncap_first}SubListDiv">
			</div>
		</td>
	</tr>
	</tbody>
	</table>
</body>
</html>