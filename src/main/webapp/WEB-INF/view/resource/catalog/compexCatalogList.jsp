<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">	
	$(function(){
		fSelfWidth = '${form.width}';
		fSelfHeight = '${form.height}';
		selfXjTitle = '${form.xjTitle}';
		selfWhTitle = '${form.whTitle}';
		xjUrl="<%=basePath %>/pages/resource/catalog/add.action?op=new&formId=${formId}";
		plscUrl="<%=basePath %>/pages/resource/${simpleModel}compexdelete.action?model=${model}&formId=${formId}";
		ljscUrl="<%=basePath %>/pages/resource/compexlogicDelete.action?model=${model}&formId=${formId}";
		bcUrl = "<%=basePath %>/pages/resource/catalog/save.action";
		bzUrl = "<%=basePath %>/pages/resource/compexshowListHelp.action?listId=${listId}";
		mrUrl = "<%=basePath %>/pages/resource/compexisDefault.action?mainTable=${model}&colName=tbl_isdefault&";
		ns.common.mouseForButton();
	});
	
</script>
</head>
<c:set var="listurl" value="/pages/resource/catalog/list.action"></c:set>
<c:set var="viewurl" value="/pages/resource/catalog/view.action"></c:set>
<%@include file="/WEB-INF/view/core/commonList.jsp"  %>
</html>