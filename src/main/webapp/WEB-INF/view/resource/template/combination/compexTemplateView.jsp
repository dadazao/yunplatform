<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path1;
%>
<%@include file="/WEB-INF/view/core/compexDomainView.jsp"  %>	
<script type="text/javascript">
<!--
	$(function(){
		xgUrl = "<%=basePath %>/pages/resource/template/editComb.action?formId=${formId}&params=${params}" + "&op=edit";
		addPartitionPage();
		ns.common.mouseForButton();
	});
//-->
</script>