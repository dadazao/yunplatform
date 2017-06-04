
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程任务评论管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>

	<display:table name="taskCommentList" id="taskCommentItem" requestURI="list.ht" 
		sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
		<display:column property="author" title="评论人" sortable="true" sortName="author"></display:column>
		<display:column  title="评论时间" sortable="true" sortName="commentTime">
			<fmt:formatDate value="${taskCommentItem.commentTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column property="content" title="评论内容" sortable="false" 
			sortName="content" maxLength="80"></display:column>
	</display:table>
	<hotent:paging tableId="taskCommentItem"/>

</body>
</html>


