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
				<span class="tbar-label">流程定义明细--${bpmDefinition.subject}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<c:choose>
							<c:when test="${not empty param['returnFromDetail']}">
								<a class="link back" href="myList.ht"><span></span>返回</a>
							</c:when>
							<c:otherwise>
								<a class="link back" href="list.ht"><span></span>返回</a>		
							</c:otherwise>
						
						</c:choose>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="20%" nowrap="nowrap">分类</th>
					<td>${globalType.typeName}</td>
				</tr>
				<tr>
					<th width="20%" nowrap="nowrap">流程标题:</th>
					<td>${bpmDefinition.subject}</td>
				</tr>
				<tr>
					<th width="20%" nowrap="nowrap">流程定义Key:</th>
					<td>${bpmDefinition.defKey}</td>
				</tr>
				<tr>
					<th width="20%" nowrap="nowrap">任务标题生成规则:</th>
					<td>${bpmDefinition.taskNameRule}</td>
				</tr>
				<tr>
					<th width="20%" nowrap="nowrap">流程描述:</th>
					<td>${bpmDefinition.descp}</td>
				</tr>
				<tr>
					<th width="20%" nowrap="nowrap">创建时间:</th>
					<td><fmt:formatDate value="${bpmDefinition.createtime}" pattern="yyyy-MM-dd HH:mm"/> </td>
				</tr>
				<tr>
					<th width="20%" nowrap="nowrap">流程状态:</th>
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
					<th>流程图</th>
					<td>
						<img src="${ctx}/bpmImage?deployId=${bpmDefinition.actDeployId}"/>
					</td>
				</tr>
			</table>
		</div>
</div>

</body>
</html>
