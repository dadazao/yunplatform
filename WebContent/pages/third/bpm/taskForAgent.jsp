<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>我的待办任务</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript">
		//执行任务
		function executeTask(taskId){
			 //加上代理参数
			 var url="${ctx}/platform/bpm/task/toStart.ht?taskId="+taskId +"&agentTask=true";
			 jQuery.openFullWindow(url);
		}
	</script>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">代理任务列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="forAgent.ht">
					<ul class="row">
						<li><span class="label" style="display: none;">id:</span><input type="hidden" name="userId"  class="inputText" value="${userId}" readonly="readonly"/></li>
						<li><span class="label">流程定义名称:</span><input type="text" name="Q_processName_SL"  class="inputText"  value="${param['Q_processName_SL']}"/></li>
						<li><span class="label">事项名称:</span><input type="text" name="Q_subject_SL"  class="inputText"  value="${param['Q_subject_SL']}"/></li>
						<li><span class="label">任务名称:</span><input type="text" name="Q_name_SL"  class="inputText"  value="${param['Q_name_SL']}"/></li>
					</ul>
				</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="taskList" id="taskItem" requestURI="forAgent.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${taskItem.id}">
					</display:column>
					<display:column property="subject" title="事项名称" ></display:column>
					<display:column property="processName" title="流程定义名称" ></display:column>
					<display:column property="name" title="任务名称" sortable="true" sortName="name_"></display:column>
					<display:column title="执行人" sortable="true" sortName="assignee_">
						<f:userName userId="${taskItem.assignee}"/>
					</display:column>
					<display:column title="创建时间" sortable="true" sortName="create_time_">
						<fmt:formatDate value="${taskItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="管理" media="html" style="width:220px;">								
						<c:if test="${empty taskItem.assignee}">
						<a href="claim.ht?taskId=${taskItem.id}&isAgent=1" class="link lock" title="申请">锁定</a>
						</c:if>
						<a href="detail.ht?taskId=${taskItem.id}" class="link detail" title="明细">明细</a>
						<c:if test="${not empty taskItem.assignee}">
						<a href="#" onclick="javascript:executeTask(${taskItem.id},'${taskItem.name}')" class="link run" title="执行">执行</a>
						</c:if>
					</display:column>
				</display:table>
				<hotent:paging tableId="taskItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


