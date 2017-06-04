<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	$(function(){
		fSelfWidth = '${form.width}';
		fSelfHeight = '${form.height}';
		selfXjTitle = '${form.xjTitle}';
		selfWhTitle = '${form.whTitle}';
		formId = '${formId}';
		listId = '${listId}';
		xjUrl="<%=basePath %>/pages/resource/${simpleModel}compexadd.action?op=new&formId=${formId}";
		plscUrl="<%=basePath %>/pages/resource/${simpleModel}compexdelete.action?model=${model}&formId=${formId}";
		ljscUrl="<%=basePath %>/pages/resource/${simpleModel}compexlogicDelete.action?model=${model}&formId=${formId}";
		bcUrl = "<%=basePath %>/pages/resource/${simpleModel}compexsave.action";
		bzUrl = "<%=basePath %>/pages/resource/${simpleModel}compexshowListHelp.action?listId=${listId}";
		mrUrl = "<%=basePath %>/pages/resource/${simpleModel}compexisDefault.action?mainTable=${model}&colName=tbl_isdefault&";
		ns.common.mouseForButton();
		showQuery("${queryType}");
		
	});
</script>
</head>
<c:set var="listurl" value="/pages/resource/${simpleModel}compexlist.action"></c:set>
<c:set var="viewurl" value="/pages/resource/${simpleModel}compexview.action"></c:set>

<%@include file="/pages/core/commonList.jsp"  %>

</html>