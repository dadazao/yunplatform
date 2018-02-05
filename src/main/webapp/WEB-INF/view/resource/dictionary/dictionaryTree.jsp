<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
function loadDictionaryTree() {
	$.ajaxSetup({async: false});
	var urlString = '<%=basePath %>/pages/system/tree<%=request.getParameter("method")%>.action?model=sys_dictionarys&root=1&idColumn=id&nameColumn=tbl_name&parentIdColumn=tbl_parentId&orderColumn=tbl_dicOrder&expand=true&expandLevel=1';
	$('#dictionaryTree').load(urlString);	
	$.ajaxSetup({async: true});
}

$(function(){
	loadDictionaryTree();
});
//-->
</script>
<div id="dictionaryTree" style="width:293px;height:563px;OVERFLOW-Y:auto;OVERFLOW-X:hidden;"></div>