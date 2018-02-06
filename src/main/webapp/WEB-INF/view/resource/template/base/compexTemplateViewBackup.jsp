<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<%@include file="/pages/resource/compexDomainView.jsp"  %>	
<script type="text/javascript">
<!--
	$(function(){
		$('#templateViewTd').html($('#templateView').parent().html());
		$('#templateView').parent().parent().parent().remove();
		xgUrl="<%=basePath %>/pages/resource/template/editBase.action?formId=${formId}&params=${params}" + "&op=edit";
		ns.common.mouseForButton();
	});
//-->
</script>
<div id="templateViewTd"></div>