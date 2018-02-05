<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<html>
<head>
	<title>${processRun.subject}--审批历史</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
  		 <display:table name="taskOpinionList" id="taskOpinionItem" requestURI="list.ht" sort="external" cellpadding="0" cellspacing="0" class="table-grid">
			<display:column title="序号" style="width:30px;">
			  ${taskOpinionItem_rowNum}
			</display:column>
			<display:column title="任务名称" property="taskName"></display:column>
			<display:column title="执行开始时间">
				<fmt:formatDate value="${taskOpinionItem.startTime}" pattern="yyyy-MM-dd HH:mm"/>
			</display:column>
			<display:column title="结束时间">
				<fmt:formatDate value="${taskOpinionItem.endTime}" pattern="yyyy-MM-dd HH:mm"/>
			</display:column>
			<display:column title="持续时间">
			  ${f:getTime(taskOpinionItem.durTime)}
			</display:column>
			<display:column property="exeFullname" title="执行人名"></display:column>
			<display:column property="opinion" title="审批意见" ></display:column>
			<display:column title="审批状态">
			<c:choose>
				<c:when test="${taskOpinionItem.checkStatus==1}"><span class="green">同意</span></c:when>
				<c:when test="${taskOpinionItem.checkStatus==2}"><span class="red">反对</span></c:when>
				<c:when test="${taskOpinionItem.checkStatus==3}"><span class="red">驳回</span></c:when>
				<c:when test="${taskOpinionItem.checkStatus==0}">弃权</c:when>
				<c:when test="${taskOpinionItem.checkStatus==4}">被追回</c:when>
				<c:when test="${taskOpinionItem.checkStatus==7}"><span class="green">知会意见</span></c:when>
				<c:otherwise>尚未处理</c:otherwise>
			</c:choose>
			</display:column>
		</display:table>
  </div>
</body>
</html>
