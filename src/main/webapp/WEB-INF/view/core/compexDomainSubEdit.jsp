<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	$(function(){
		dataJsonsub("<%=basePath %>/pages/resource/${simpleModel}compexdataJsonsub.action?params=${params}&formId=${formId}&tabId=${tabId}&partitionId=${partitionId}&model=${model}&subDomainId=${subDomainId}","${partitionForm}","${op}");
	});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="compexSubEditId_${partitionForm}"></div>
</html>