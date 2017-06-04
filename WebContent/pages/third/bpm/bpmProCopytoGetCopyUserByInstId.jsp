<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>抄送人</title>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="l-layout-header">流程实例-【<i>${processRun.subject}</i>】抄送人。</div>
	<div class="panel">
		<c:if test="${param.tab eq 1 }">
			<f:tab curTab="copyUser" tabName="process"/>
		</c:if>
		
		<div class="panel-body">
	    	<display:table name="bpmProCopytoList" id="bpmProCopytoItem" requestURI="getCopyUserByInstId.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column property="ccUname" title="接收人">
					<c:out value="${bpmProCopytoItem.ccUname}"></c:out>
				</display:column>
				<display:column property="posName" title="岗位">
					<c:out value="${bpmProCopytoItem.posName}"></c:out>
				</display:column>
				<display:column property="orgName" title="组织">
					<c:out value="${bpmProCopytoItem.orgName}"></c:out>
				</display:column>
			
				<display:column title="是否已读" style="width:60px;" sortable="true" sortName="IS_READED">
					<c:choose>
						<c:when test="${bpmProCopytoItem.isReaded eq 0}"><span class="red close-message">未读</span></c:when>
						<c:when test="${bpmProCopytoItem.isReaded eq 1}"><span class="green open-message">已读</c:when>
					</c:choose>
				</display:column>
				<display:column  title="抄送时间 ">
					<fmt:formatDate value="${bpmProCopytoItem.ccTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column title="类型" style="width:45px;" sortable="true" sortName="CP_TYPE">
					<c:choose>
						<c:when test="${bpmProCopytoItem.cpType eq 1}"><span class="green">抄送</span></c:when>
						<c:when test="${bpmProCopytoItem.cpType eq 2}"><span class="brown">转发</span></c:when>
					</c:choose>
				</display:column>
			</display:table>
			<hotent:paging tableId="bpmProCopytoItem"></hotent:paging>
		</div>
	</div>
</body>
</html>


