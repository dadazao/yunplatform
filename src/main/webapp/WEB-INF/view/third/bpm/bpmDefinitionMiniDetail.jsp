<%--
	time:2011-11-28 22:02:01
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>流程定义扩展明细</title>
	<%@include file="/commons/include/getById.jsp" %>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程定义扩展详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="back bar-button" href="list.ht"><span></span>返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<div class="panel-detail">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">分类</th>
							<td>${globalType.typeName}</td>
						</tr>
						<tr>
							<th width="20%">流程标题:</th>
							<td>${bpmDefinition.subject}</td>
						</tr>
						<tr>
							<th width="20%">流程定义Key:</th>
							<td>${bpmDefinition.defKey}</td>
						</tr>
						<tr>
							<th width="20%">任务标题生成规则:</th>
							<td>${bpmDefinition.taskNameRule}</td>
						</tr>
						<tr>
							<th width="20%">流程描述:</th>
							<td>${bpmDefinition.descp}</td>
						</tr>
						<tr>
							<th width="20%">创建时间:</th>
							<td><fmt:formatDate value="${bpmDefinition.createtime}" pattern="yyyy-MM-dd HH:mm"/> </td>
						</tr>
						<tr>
							<th width="20%">流程状态:</th>
							<td>
								<c:choose>
									<c:when test="${bpmDefinition.status==1}">
										<font color='green'>可用</font>
									</c:when>
									<c:otherwise>禁用</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th width="20%">版本号:</th>
							<td>${bpmDefinition.versionNo}</td>
						</tr>
						<tr>
							<th width="20%">BPMN XML:</th>
							<td><c:out value="${defXml}" escapeXml="true"/></td>
						</tr>
						<tr>
							<th width="20%">流程定义XML(设计器):</th>
							<td><c:out value="${bpmDefinition.defXml}" escapeXml="true"/></td>
						</tr>
						<tr>
							<th width="20%">activiti流程定义ID:</th>
							<td>${bpmDefinition.actDefId}</td>
						</tr>
						<tr>
							<th width="20%">act流程定义Key:</th>
							<td>${bpmDefinition.actDefKey}</td>
						</tr>
						<tr>
							<th>流程图</th>
							<td>
								<img src="${ctx}/bpmImage?deployId=${bpmDefinition.actDeployId}"/>
							</td>
						</tr>
					</table>
				</div>
		</div>
</div>

</body>
</html>
