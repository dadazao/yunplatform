
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务会签数据管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
			<div class="hide-panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">任务会签数据管理列表</span>
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
												<li><span class="label">流程实例ID:</span><input type="text" name="Q_actInstId_SL"  class="inputText" value="${param['Q_actInstId_SL']}"/></li>
											
												<li><span class="label">流程节点名称:</span><input type="text" name="Q_nodeName_SL"  class="inputText" value="${param['Q_nodeName_SL']}"/></li>
											
												<li><span class="label">nodeId:</span><input type="text" name="Q_nodeId_SL"  class="inputText" value="${param['Q_nodeId_SL']}"/></li>
											
												<li><span class="label">会签任务Id:</span><input type="text" name="Q_taskId_SL"  class="inputText" value="${param['Q_taskId_SL']}"/></li>
											
												<li><span class="label">投票人ID:</span><input type="text" name="Q_voteUserId_SL"  class="inputText" value="${param['Q_voteUserId_SL']}"/></li>
											
												<li><span class="label">投票人名:</span><input type="text" name="Q_voteUserName_SL"  class="inputText" value="${param['Q_voteUserName_SL']}"/><li>
											
												<li><span class="label">投票时间 从:</span> <input  name="Q_beginvoteTime_DL"  class="inputText date" value="${param['Q_beginvoteTime_DL']}"/>
												<span class="label">至: </span><input  name="Q_endvoteTime_GL" class="inputText date" value="${param['Q_endvoteTime_GL']}"/></li>
											
												<li><span class="label">是否同意:</span><input type="text" name="Q_isAgree_SN"  class="inputText" value="${param['Q_isAgree_SN']}"/></li>
											
												<li><span class="label">投票意见内容:</span><input type="text" name="Q_content_SL"  class="inputText" value="${param['Q_content_SL']}"/></li>
											
												<li><span class="label">signNums:</span><input type="text" name="Q_signNums_S"  class="inputText" value="${param['Q_signNums_S']}"/></li>
											
												<li><span class="label">是否完成:</span><input type="text" name="Q_isCompleted_SN"  class="inputText" value="${param['Q_isCompleted_SN']}"/></li>
											
									</ul>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="taskSignDataList" id="taskSignDataItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="dataId" value="${taskSignDataItem.dataId}">
							</display:column>
							<display:column property="actInstId" title="流程实例ID" sortable="true" sortName="actInstId"></display:column>
							<display:column property="nodeName" title="流程节点名称" sortable="true" sortName="nodeName"></display:column>
							<display:column property="nodeId" title="nodeId" sortable="true" sortName="nodeId"></display:column>
							<display:column property="taskId" title="会签任务Id" sortable="true" sortName="taskId"></display:column>
							<display:column property="voteUserId" title="投票人ID" sortable="true" sortName="voteUserId"></display:column>
							<display:column property="voteUserName" title="投票人名" sortable="true" sortName="voteUserName"></display:column>
							<display:column  title="投票时间" sortable="true" sortName="voteTime">
								<fmt:formatDate value="${taskSignDataItem.voteTime}" pattern="yyyy-MM-dd"/>
							</display:column>
							<display:column property="isAgree" title="是否同意" sortable="true" sortName="isAgree"></display:column>
							<display:column property="content" title="投票意见内容" sortable="true" sortName="content"></display:column>
							<display:column property="signNums" title="signNums" sortable="true" sortName="signNums"></display:column>
							<display:column property="isCompleted" title="是否完成" sortable="true" sortName="isCompleted"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.ht?dataId=${taskSignDataItem.dataId}" class="link del">删除</a>
								<a href="edit.ht?dataId=${taskSignDataItem.dataId}" class="link edit">编辑</a>
								<a href="get.ht?dataId=${taskSignDataItem.dataId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<hotent:paging tableId="taskSignDataItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


