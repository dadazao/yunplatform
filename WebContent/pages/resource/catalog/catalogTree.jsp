<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
function loadCatalog() {
	$.ajaxSetup({async: false});
	var urlString = "<%=basePath %>/pages/system/treelookup.action?expand=true&expandLevel=1&parentId=parentId&parentName=parentName&model=sys_catalog&root=1&idColumn=id&nameColumn=tbl_name&parentIdColumn=tbl_parentId&pathColumn=&orderColumn=tbl_orderNum";
	$('#catalogTree').load(urlString);	
	$.ajaxSetup({async: true});
}

$(function(){
	loadCatalog();
});
//-->
</script>
<div id="catalogTree" style="width:293px;height:563px;OVERFLOW-Y:auto;OVERFLOW-X:hidden;"></div>