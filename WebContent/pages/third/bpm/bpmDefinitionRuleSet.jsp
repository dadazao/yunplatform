<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程节点跳转规则设置</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRuleWindow.js"></script>
<script type="text/javascript">
	var actDefId = "${bpmDefinition.actDefId}";
	var deployId = "${bpmDefinition.actDeployId}";
	function setNodeRule(nodeId, nodeName) {
		var url = __ctx + "/platform/bpm/bpmNodeRule/edit.ht?deployId=" + deployId + "&actDefId=" + actDefId + "&nodeId=" + nodeId + "&nodeName=" + encodeURIComponent(nodeName);
		var winArgs = "dialogWidth=800px;dialogHeight=650px;help=0;status=0;scroll=1;center=0;resizable=1;";
		url = url.getNewUrl();
		var rtn = window.showModalDialog(url, "", winArgs);
		//window.location.reload(true);
	}
</script>
</head>
<body>
	<c:if test="${empty nodeId}">
		<jsp:include page="incDefinitionHead.jsp">
			<jsp:param value="节点人员设置" name="title" />
		</jsp:include>
		<f:tab curTab="跳转规则设置" tabName="flow" />
	</c:if>
	<div class="panel">
		
		<div class="panel-body">
			
				<form action="" method="post" id="">
					<input type="hidden" name="defId" value="${defId}" /> <input type="hidden" name="nodeId" value="${nodeId}" />
					<table class="table-grid" style="width: 100%">
						<thead>
							<tr>
								<th width="30" nowrap="nowrap">序号</th>
								<th >节点名称</th>
								<th >是否定义规则</th>
								<th >管理</th>
							</tr>
						</thead>
						<c:forEach items="${nodeSets}" var="nodeSet" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<td>${nodeSet.nodeName}(${nodeSet.nodeId})</td>
								<td>
								<c:choose>
										<c:when test="${fn:length(nodeSetNodeRulesMap[nodeSet.setId])==0}">
											<span class="red">无</span>
										</c:when>
										<c:otherwise>
											<span class="green">已定义</span>
	
										</c:otherwise>
									</c:choose> </td>
								<td>
									<a href="#" class="link edit" onclick="setNodeRule('${nodeSet.nodeId}','${nodeSet.nodeName}')">编辑</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div style="height: 40px"></div>
				</form>
			
		</div>
	</div>
</body>
</html>
