<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>任务变量管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx }/js/hotent/platform/bpm/TaskVarsWindow.js"></script> 
<script type="text/javascript">
  var taskId='${taskId}';
function updateVars(id,varsValue){
	TaskVarsWindow({id:id,varsValue:varsValue,taskId:taskId});
}
</script>
</head>
<body > 
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务明细--${processRun.subject}--${task.name}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link close" href="javascript:window.close();"><span></span>关闭</a></div>
				</div>
			</div>
		</div>
		<f:tab curTab="taskVars" tabName="task"/>
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务变量管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht?taskId=${taskId }">
					<ul class="row">
						<li><span class="label">变量名称:</span><input type="text" name="Q_name_SL"  class="inputText"  value="${param['Q_name_SL']}"/></li>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
			<display:table name="taskVarsList" id="taskVarItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
			<display:column property="name" title="变量名称" ></display:column>
			<display:column property="type" title="变量类型" ></display:column>
			<display:column property="textValue" title="变量值" ></display:column>
			<display:column title="管理" media="html">
				<a href="#" onclick="updateVars(${taskVarItem.id},'${taskVarItem.textValue}')" class="link flowDesign">修改</a>
		     </display:column>
			</display:table>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


