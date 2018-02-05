<%--
	time:2011-12-14 15:41:53
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>流程节点规则明细</title>
	<%@include file="/commons/include/getById.jsp" %>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程节点规则详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" href="../bpmNodeRule/list.ht"><span></span>返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="bpmNodeRuleForm" method="post" action="add2.ht">
					<div class="panel-detail">
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th width="20%">节点设置ID:</th>
								<td>${bpmNodeRule.setId}</td>
							</tr>
							<tr>
								<th width="20%">规则名称:</th>
								<td>${bpmNodeRule.ruleName}</td>
							</tr>
							<tr>
								<th width="20%">规则表达式:</th>
								<td>${bpmNodeRule.conditionCode}</td>
							</tr>
							<tr>
								<th width="20%">Act流程发布ID:</th>
								<td>${bpmNodeRule.actDefId}</td>
							</tr>
							<tr>
								<th width="20%">优先级别:</th>
								<td>${bpmNodeRule.priority}</td>
							</tr>
							<tr>
								<th width="20%">跳转节点:</th>
								<td>${bpmNodeRule.targetNode}</td>
							</tr>
							<tr>
								<th width="20%">跳转节点名称:</th>
								<td>${bpmNodeRule.targetNodeName}</td>
							</tr>
							<tr>
								<th width="20%">备注:</th>
								<td>${bpmNodeRule.memo}</td>
							</tr>
						</table>
					</div>
				</form>
		</div>
</div>

</body>
</html>
