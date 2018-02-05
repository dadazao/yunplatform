<%--
	time:2011-12-31 15:48:59
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>流程节点邮件明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript">
		//放置脚本
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程节点邮件详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" href="../bpmNodeMessage/list.ht"><span></span>返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="bpmNodeMessageForm" method="post" action="add2.ht">
					<div class="panel-detail">
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							
							<tr>
								<th width="20%">主题:</th>
								<td>${bpmNodeMessage.subject}</td>
							</tr>
							<tr>
								<th width="20%">收件人:</th>
								<td>${bpmNodeMessage.receiver}</td>
							</tr>
							<tr>
								<th width="20%">抄送:</th>
								<td>${bpmNodeMessage.copyTo}</td>
							</tr>
							<tr>
								<th width="20%">流程定义ID:</th>
								<td>${bpmNodeMessage.actDefId}</td>
							</tr>
							<tr>
								<th width="20%">流程节点ID:</th>
								<td>${bpmNodeMessage.nodeId}</td>
							</tr>
							<tr>
								<th width="20%">内容模版:</th>
								<td>${bpmNodeMessage.templateId}</td>
							</tr>
							<tr>
								<th width="20%">秘密抄送:</th>
								<td>${bpmNodeMessage.bcc}</td>
							</tr>
							<tr>
								<th width="20%">发件人:</th>
								<td>${bpmNodeMessage.fromUser}</td>
							</tr>
							<tr>
								<th width="20%">messageType:</th>
								<td>${bpmNodeMessage.messageType}</td>
							</tr>
						</table>
					</div>
				</form>
		</div>
</div>

</body>
</html>
