<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<display:table name="taskOpinionList" id="taskOpinionItem" requestURI="list.ht" sort="external" cellpadding="0" cellspacing="0" class="table-grid">
	<display:column title="序号" style="width:30px;">
	  ${taskOpinionItem_rowNum}
	</display:column>
	<display:column property="taskName" title="任务名称"></display:column>
	<display:column  title="执行开始时间">
		<fmt:formatDate value="${taskOpinionItem.startTime}" pattern="yyyy-MM-dd HH:mm"/>
	</display:column>
	<display:column  title="结束时间">
		<fmt:formatDate value="${taskOpinionItem.endTime}" pattern="yyyy-MM-dd HH:mm"/>
	</display:column>
	<display:column title="持续时间">
	  ${f:getTime(taskOpinionItem.durTime)}
	</display:column>
	<display:column property="exeFullname" title="执行人名"></display:column>
	<display:column property="opinion" title="审批意见" ></display:column>
	<display:column title="审批状态">
		<f:taskStatus status="${taskOpinionItem.checkStatus}" flag="0"></f:taskStatus>
</display:column>
</display:table>
