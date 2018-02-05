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
		xjUrl="<%=basePath%>/pages/resource/${simpleModel}compexadd.action?op=new&formId=${formId}";
		plscUrl="<%=basePath%>/pages/resource/${simpleModel}compexdelete.action?model=${model}&formId=${formId}";
		ljscUrl="<%=basePath%>/pages/resource/${simpleModel}compexlogicDelete.action?model=${model}&formId=${formId}";
		bcUrl = "<%=basePath%>/pages/resource/${simpleModel}compexsave.action";
		mrUrl = "<%=basePath%>/pages/resource/${simpleModel}compexisDefault.action?mainTable=${model}&colName=tbl_isdefault&";
		ns.common.mouseForButton();
	});
	
	function eventCompexZDY(){
		var urlString = "<%=basePath%>/pages/deployment/dbDriverenabled.action";
		var items = $("input[type='checkbox']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "?params=" + param;
			alertMsg.confirm("确定要启用吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
</script>
</head>
<c:set var="listurl" value="/pages/deployment/dbDriverlist.action"></c:set>
<c:set var="viewurl" value="/pages/resource/compexview.action"></c:set>
<%@include file="/pages/core/commonList.jsp"  %>
</html>