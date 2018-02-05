<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<table class="table-detail">
		<tr>
			<th width="100px">动态节点名称</th>
			<td width="35%">
				<input type="text" name="dynamicTask" class="inputText" value="${task.name}" validate="{required:true,maxlength:64}" /> 
			</td>
			<th width="100px">节点执行人</th>
			<td width="35%">
				<input id="lastDestTaskId" type="hidden" value="${task.taskDefinitionKey}" name="lastDestTaskId">
				<span id="jumpUserDiv"></span>
				<a id="jumpUserLink" class="link grant" onclick="selExeUsers(this,'${task.taskDefinitionKey}')">选择..</a>
			</td>
		</tr>
</table>