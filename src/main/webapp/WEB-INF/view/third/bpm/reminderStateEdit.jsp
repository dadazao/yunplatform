<%--
	time:2012-02-17 17:17:37
	desc:edit the 任务催办执行情况
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 任务催办执行情况</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=reminderState"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#reminderStateForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				  <c:choose>
						<c:when test="${reminderState.id==0}">
							添加任务催办执行情况
						</c:when>
						<c:otherwise>
							编辑任务催办执行情况
						</c:otherwise>
				   </c:choose> 			
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="reminderStateForm" method="post" action="save.ht">
					<div class="panel-detail">
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th width="20%">流程定义ID: </th>
								<td><input type="text" id="actDefId" name="actDefId" value="${reminderState.actDefId}"  class="inputText"/></td>
							</tr>
							<tr>
								<th width="20%">任务ID: </th>
								<td><input type="text" id="taskId" name="taskId" value="${reminderState.taskId}"  class="inputText"/></td>
							</tr>
							<tr>
								<th width="20%">催办时间: </th>
								<td><input type="text" id="reminderTime" name="reminderTime" value="<fmt:formatDate value='${reminderState.reminderTime}' pattern='yyyy-MM-dd'/>" class="inputText date"/></td>
							</tr>
						</table>
						<input type="hidden" name="id" value="${reminderState.id}" />
					</div>
				</form>
		</div>
</div>
</body>
</html>
