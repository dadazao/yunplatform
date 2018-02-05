<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<%@include file="/commons/include/get.jsp" %>
	<title>${f:removeHTMLTag(bpmTaskExeList[0].subject)}--任务交办历史</title>
</head>
<body>
	<div class="l-layout-header">流程实例-【<i>${bpmTaskExeList[0].subject}</i>】任务交办历史。</div>
	<div class="panel">
		<div class="panel-body" style="height:100%;overflow:auto;">
	
  		 <display:table name="bpmTaskExeList" id="bpmTaskExeItem" requestURI="list.ht" sort="external" cellpadding="0" cellspacing="0" class="table-grid">
			<display:column title="序号" style="width:30px;">
			  ${bpmTaskExeItem_rowNum}
			</display:column>
			<display:column title="任务名称" property="taskName"></display:column>
			<display:column title="创建时间">
				<fmt:formatDate value="${bpmTaskExeItem.cratetime}" pattern="yyyy-MM-dd HH:mm"/>
			</display:column>
			<display:column  title="被代理人">
				<a href="${ctx}/platform/system/sysUser/get.ht?userId=${bpmTaskExeItem.ownerId}&canReturn=2&hasClose=1" target="_blank">${bpmTaskExeItem.ownerName}</a>
			</display:column>
			<display:column  title="承接人">
				<a href="${ctx}/platform/system/sysUser/get.ht?userId=${bpmTaskExeItem.assigneeId}&canReturn=2&hasClose=1" target="_blank">${bpmTaskExeItem.assigneeName}</a>
			</display:column>
			<display:column  titleKey="原因备注" >
				 ${f:parseText(bpmTaskExeItem.memo)}
			</display:column>
			<display:column title="状态">
				<c:choose>
					<c:when test="${bpmTaskExeItem.status==0}">
						<span class="green">转办</span>
					</c:when>
					<c:when test="${bpmTaskExeItem.status==1}">
						<span class="green">完成任务</span>
					</c:when>
					<c:when test="${bpmTaskExeItem.status==2}">
						<span class="red">取消转办</span>
					</c:when>
				</c:choose>
			</display:column>
		</display:table>
		</div>
  </div>
</body>
</html>
