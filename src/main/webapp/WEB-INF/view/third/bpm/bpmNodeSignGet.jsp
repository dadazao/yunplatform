<%--
	time:2011-12-14 08:41:55
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>会签任务投票规则明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript">
		//放置脚本
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">会签任务投票规则详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" href="../bpmNodeSign/list.ht"><span></span>返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-detail">
				<form id="bpmNodeSignForm" method="post" action="add2.ht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">流程节点ID:</th>
							<td>${bpmNodeSign.nodeId}</td>
						</tr>
						<tr>
							<th width="20%">票数:</th>
							<td>${bpmNodeSign.voteAmount}</td>
						</tr>
						<tr>
							<th width="20%">决策方式:</th>
							<td>${bpmNodeSign.decideType}</td>
						</tr>
						<tr>
							<th width="20%">1=百分比:</th>
							<td>${bpmNodeSign.voteType}</td>
						</tr>
						<tr>
							<th width="20%">Act流程发布ID:</th>
							<td>${bpmNodeSign.actDeployId}</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
</div>

</body>
</html>
