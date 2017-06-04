<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>用户表管理</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript"
	src="${ctx }/js/lg/plugins/ligerWindow.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#ligerTab-div").ligerTab();
	});
</script>
</head>
<body>

	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">用户设置预览</span>
			</div>
		</div>
		<div class="panel-body">
			<div id="ligerTab-div">
				<div title="参与流程用户">
					<display:table name="attendTaskExecutors"
						id="attendTaskExecutorItem" cellpadding="1" cellspacing="1"
						sort="page" class="table-grid">
						<display:column title="执行人的类型" style="text-align:left">
							<c:choose>
								<c:when test="${attendTaskExecutorItem.type=='user'}">
									<span class="black">用户</span>
								</c:when>
								<c:when test="${attendTaskExecutorItem.type=='org'}">
									<span class="black">组织</span>
								</c:when>
								<c:when test="${attendTaskExecutorItem.type=='role'}">
									<span class="black">角色</span>
								</c:when>
								<c:when test="${attendTaskExecutorItem.type=='pos'}">
									<span class="black">岗位</span>
								</c:when>
							</c:choose>
						</display:column>
						<display:column property="executeId" title="执行人ID"
							style="text-align:left"></display:column>
						<display:column property="executor" title="执行人">
						</display:column>
					</display:table>
				</div>
				<div title="接收通知用户">
					<display:table name="informTaskExecutors"
						id="informTaskExecutorItem" cellpadding="1" cellspacing="1"
						sort="page" class="table-grid">
						<display:column title="执行人的类型" style="text-align:left">
							<c:choose>
								<c:when test="${informTaskExecutorItem.type=='user'}">
									<span class="black">用户</span>
								</c:when>
								<c:when test="${informTaskExecutorItem.type=='org'}">
									<span class="black">组织</span>
								</c:when>
								<c:when test="${informTaskExecutorItem.type=='role'}">
									<span class="black">角色</span>
								</c:when>
								<c:when test="${informTaskExecutorItem.type=='pos'}">
									<span class="black">岗位</span>
								</c:when>
							</c:choose>
						</display:column>
						<display:column property="executeId" title="执行人ID"
							style="text-align:left"></display:column>
						<display:column property="executor" title="执行人">
						</display:column>
					</display:table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>


