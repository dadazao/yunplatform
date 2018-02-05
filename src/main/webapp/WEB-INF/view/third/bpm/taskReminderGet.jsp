<%--
	time:2012-02-17 13:56:54
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>任务节点催办时间设置明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript">
		//放置脚本
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务节点催办时间设置详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-detail">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th width="20%">流程定义ID:</th>
						<td>${taskReminder.actDefId}</td>
					</tr>
					<tr>
						<th width="20%">流程节点ID:</th>
						<td>${taskReminder.nodeId}</td>
					</tr>
					<tr>
						<th width="20%">催办开始时间(相对于任务创建时间，多少工作日):</th>
						<td>${taskReminder.reminderStart}</td>
					</tr>
					<tr>
						<th width="20%">催办结束时间(相对于催办开始时间):</th>
						<td>${taskReminder.reminderEnd}</td>
					</tr>
					<tr>
						<th width="20%">催办次数:</th>
						<td>${taskReminder.times}</td>
					</tr>
					<tr>
						<th width="20%">催办提醒短信内容:</th>
						<td>${taskReminder.mailContent}</td>
					</tr>
					<tr>
						<th width="20%">催办邮件模板内容:</th>
						<td>${taskReminder.msgContent}</td>
					</tr>
					<tr>
						<th width="20%">催办邮件模板内容:</th>
						<td>${taskReminder.smsContent}</td>
					</tr>
					<tr>
						<th width="20%">任务到期处理动作:</th>
						<td>${taskReminder.action}</td>
					</tr>
					<tr>
						<th width="20%">script:</th>
						<td>${taskReminder.script}</td>
					</tr>
					<tr>
						<th width="20%">completeTime:</th>
						<td>${taskReminder.completeTime}</td>
					</tr>
				</table>
			</div>
		</div>
</div>

</body>
</html>
