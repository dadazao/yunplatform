<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<html>
<head>
<script type="text/javascript">
	$(function() {
		fSelfWidth = '${form.width}';
		fSelfHeight = '${form.height}';
		selfXjTitle = '${form.xjTitle}';
		selfWhTitle = '${form.whTitle}';
		//新建ACTION URL
		xjUrl = "<%=basePath%>/pages/resource/textBox/add.action?formId=${formId}&model=${model}&op=new";
		//批量删除ACTION URL
		plscUrl = "<%=basePath%>/pages/resource/textBox/del.action?model=${model}&formId=${formId}";
		ljscUrl = "<%=basePath%>/pages/resource/textBox/logicDelete.action?model=${model}&formId=${formId}";
		bcUrl = "<%=basePath%>/pages/resource/${simpleModel}compexsave.action";
		bzUrl = "<%=basePath%>/pages/resource/${simpleModel}compexshowListHelp.action?listId=${listId}";
		mrUrl = "<%=basePath%>/pages/resource/${simpleModel}compexisDefault.action?mainTable=${model}&colName=tbl_isdefault&";
	});
		
</script>
</head>
<c:set var="listurl" value="/pages/resource/textBox/list.action"></c:set>
<c:set var="viewurl" value="/pages/resource/textBox/view.action"></c:set>
<%@include file="/WEB-INF/view/core/commonList.jsp"  %>
</html>
