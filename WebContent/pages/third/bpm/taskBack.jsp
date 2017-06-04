<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>任务回退</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js" ></script>
<script type="text/javascript">
 function save(){
	 $("#backTaskForm").submit();
	 window.close();
 }

 function selBackUser(obj)
 {
 	var assigneeIds=$(obj).siblings("input[name='assigneeIds']");
 	var assigneeNames=$(obj).siblings("input[name='assigneeNames']");
 	
    UserDialog({
 		callback:function(uIds,unames){
 			assigneeIds.val(uIds);
 			assigneeNames.val(unames);
 		}
 	});
 }
</script>
</head>
<body>
   <div>
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">任务回退</span>
		</div>
	</div>
	<form action="${ctx}/platform/bpm/task/jumpBack.ht" method="post" id="backTaskForm">
		<table class="table-grid" style="width:100%">
			<tr>
				<th>回退节点</th>
				<th>执行</th>
			</tr>
			<tr>
				<td>${parentStack.nodeName}</td>
				<td>
					<input type="hidden" name="taskId" value="${taskId}"/> <!-- required -->
					<input type="hidden" name="assigneeIds" value="${parentStack.assignees}"/> <!-- allow null -->
					<input type="hidden" name="back" value="true"/>  <!-- required -->
					<input type="hidden" name="stackId" value="${parentStack.stackId}"/><!-- allow null -->
					<input type="text" class="inputText" name="assigneeNames" value="${assigneeNames}">
					<input type="button" value="..." onclick="selBackUser(this);"/>
				</td>
			</tr>
		</table>
		<div position="bottom"  class="bottom" style='margin-top:10px;'>
			<a href='#' class='button'  onclick="save()" ><span >确定</span></a>
			<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span >取消</span></a>
		</div>
	</form>
</div>
</body>
</html>