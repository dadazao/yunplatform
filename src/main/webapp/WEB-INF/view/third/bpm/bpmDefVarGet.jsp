<%--
	time:2011-12-01 16:50:08
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>流程变量定义明细</title>
	<%@include file="/commons/include/getById.jsp" %>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程变量定义详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back"  href="../bpmDefVar/list.ht?defId=${bpmDefVar.defId}&actDeployId=${bpmDefVar.actDeployId}&actDefId=${actDefId}"><span></span>返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-detail">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">流程定义ID:</th>
							<td>${bpmDefVar.defId}</td>
						</tr>
						<tr>
							<th width="20%">变量名称:</th>
							<td>${bpmDefVar.varName}</td>
						</tr>
						<tr>
							<th width="20%">变量Key:</th>
							<td>${bpmDefVar.varKey}</td>
						</tr>
						<tr>
							<th width="20%">变量数据类型:</th>
							<td>
							<c:if test="${bpmDefVar.varDataType eq 'string'}">字符串</c:if>
							<c:if test="${bpmDefVar.varDataType eq 'number'}">数字</c:if>
							<c:if test="${bpmDefVar.varDataType eq 'date'}">日期</c:if>
							</td>
						</tr>
						<tr>
							<th width="20%">默认值:</th>
							<td>${bpmDefVar.defValue}</td>
						</tr>
						<tr>
							<th width="20%">actDeployId:</th>
							<td>${bpmDefVar.actDeployId}</td>
						</tr>
						<tr>
							<th width="20%">节点名称:</th>
							<td>${bpmDefVar.nodeName}</td>
						</tr>
						<tr>
							<th width="20%">节点ID:</th>
							<td>${bpmDefVar.nodeId}</td>
						</tr>
					</table>
			</div>
		</div>
</div>

</body>
</html>
