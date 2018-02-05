<%--
	time:2012-08-06 13:56:42
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>流程运行日志明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript">
		//放置脚本
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程运行日志详细信息</span>
			</div>
			<c:if test="${canReturn==0}">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" onclick="history.back()"><span></span>返回</a></div>
				</div>
			</div>
			</c:if>
		</div>
		<div class="panel-body">
			
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th width="20%">流程运行ID:</th>
						<td>${bpmRunLog.runid}</td>
					</tr>
					<tr>
						<th width="20%">流程标题:</th>
						<td>${bpmRunLog.processSubject}</td>
					</tr>
					<tr>
						<th width="20%">用户名称:</th>
						<td>${bpmRunLog.username}</td>
					</tr>
					<tr>
						<th width="20%">操作时间:</th>
						<td><fmt:formatDate value="${bpmRunLog.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th width="20%">操作类型:</th>
						<td>
							<c:choose>
								<c:when test="${bpmRunLog.operatortype eq 0}"><span class="green">启动流程</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 1}"><span class="green">交办</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 2}"><span class="red">追回</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 3}"><span class="red">删除流程实例</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 4}"><span class="green">同意(投票)</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 5}"><span class="red">反对(投票)</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 6}"><span class="green">弃权(投票)</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 7}"><span class="green">补签</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 8}"><span class="red">驳回</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 9}"><span class="red">驳回到发起人</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 10}"><span class="red">删除任务</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 11}"><span class="green">代理任务</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 13}"><span class="green">锁定任务</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 14}"><span class="green">任务解锁</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 15}"><span class="green">添加意见</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 16}"><span class="green">任务指派</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 17}"><span class="green">设定所有人</span></c:when>
								<c:when test="${bpmRunLog.operatortype eq 18}"><span class="green">结束任务</span></c:when>
								<c:otherwise><span class="red">其他</span></c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th width="20%">备注:</th>
						<td>${bpmRunLog.memo}</td>
					</tr>
				</table>
				
		</div>
</div>

</body>
</html>
