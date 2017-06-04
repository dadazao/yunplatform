
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务节点催办时间设置管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
			<div class="hide-panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">任务节点催办时间设置管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
						</div>	
					</div>
				</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.ht">
									<ul class="row">
												<li><span min-width:150px;>流程定义ID:</span><input type="text" name="Q_actDefId_SL"  class="inputText" value="${param['Q_actDefId_SL']}"/></li>
											
												<li><span min-width:150px;>流程节点ID:</span><input type="text" name="Q_nodeId_SL"  class="inputText" value="${param['Q_nodeId_SL']}"/></li>
											
												<li><span min-width:150px; >催办开始时间(相对于任务创建时间，多少工作日):</span><input type="text" name="Q_reminderStart_SL"  class="inputText" value="${param['Q_reminderStart_SL']}"/></li>
											
												<li><span min-width:150px;>催办结束时间(相对于催办开始时间):</span><input type="text" name="Q_reminderEnd_SL"  class="inputText" value="${param['Q_reminderEnd_SL']}"/></li>
											
												<li><span min-width:150px;>催办次数:</span><input type="text" name="Q_times_SL"  class="inputText" value="${param['Q_times_SL']}"/></li>
											
												<li><span min-width:150px;>催办提醒短信内容:</span><input type="text" name="Q_mailContent_SL"  class="inputText" value="${param['Q_mailContent_SL']}"/></li>
											
												<li><span min-width:150px;>催办邮件模板内容:</span><input type="text" name="Q_msgContent_SL"  class="inputText" value="${param['Q_msgContent_SL']}"/></li>
											
												<li><span min-width:150px;>催办邮件模板内容:</span><input type="text" name="Q_smsContent_SL"  class="inputText" value="${param['Q_smsContent_SL']}"/></li>
											
												<li><span min-width:150px;>任务到期处理动作:</span><input type="text" name="Q_action_SL"  class="inputText" value="${param['Q_action_SL']}"/></li>
											
												<li><span min-width:150px;>script:</span><input type="text" name="Q_script_SL"  class="inputText" value="${param['Q_script_SL']}"/></li>
											
												<li><span min-width:150px;>completeTime:</span><input type="text" name="Q_completeTime_SL"  class="inputText" value="${param['Q_completeTime_SL']}"/></li>
											
									</ul>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="taskReminderList" id="taskReminderItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="taskDueId" value="${taskReminderItem.taskDueId}">
							</display:column>
							<display:column property="actDefId" title="流程定义ID" sortable="true" sortName="actDefId"></display:column>
							<display:column property="nodeId" title="流程节点ID" sortable="true" sortName="nodeId"></display:column>
							<display:column property="reminderStart" title="催办开始时间(相对于任务创建时间，多少工作日)" sortable="true" sortName="reminderStart"></display:column>
							<display:column property="reminderEnd" title="催办结束时间(相对于催办开始时间)" sortable="true" sortName="reminderEnd"></display:column>
							<display:column property="times" title="催办次数" sortable="true" sortName="times"></display:column>
								<display:column property="mailContent" title="催办提醒短信内容" sortable="true" sortName="mailContent" maxLength="80"></display:column>
								<display:column property="msgContent" title="催办邮件模板内容" sortable="true" sortName="msgContent" maxLength="80"></display:column>
								<display:column property="smsContent" title="催办邮件模板内容" sortable="true" sortName="smsContent" maxLength="80"></display:column>
							<display:column property="action" title="任务到期处理动作" sortable="true" sortName="action"></display:column>
								<display:column property="script" title="script" sortable="true" sortName="script" maxLength="80"></display:column>
							<display:column property="completeTime" title="completeTime" sortable="true" sortName="completeTime"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.ht?taskDueId=${taskReminderItem.taskDueId}" class="link del">删除</a>
								<a href="edit.ht?taskDueId=${taskReminderItem.taskDueId}" class="link edit">编辑</a>
								<a href="get.ht?taskDueId=${taskReminderItem.taskDueId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<hotent:paging tableId="taskReminderItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


