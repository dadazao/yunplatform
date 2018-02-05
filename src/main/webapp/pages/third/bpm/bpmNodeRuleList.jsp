
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程节点规则管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
			<div class="hide-panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">流程节点规则管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add " href="edit.ht"><span></span>添加</a></div>
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
												<li><span class="label">节点设置ID:</span><input type="text" name="Q_setId_S"  class="inputText" value="${param['Q_setId_S']}"/></li>
											
												<li><span class="label">规则名称:</span><input type="text" name="Q_ruleName_S"  class="inputText" value="${param['Q_ruleName_S']}"/></li>
											
												<li><span class="label">规则表达式:</span><input type="text" name="Q_conditionCode_S"  class="inputText" value="${param['Q_conditionCode_S']}"/></li>
											
												<li><span class="label">Act流程发布ID:</span><input type="text" name="Q_actDefId_S"  class="inputText" value="${param['Q_actDefId_S']}"/></li>
											
												<li><span class="label">优先级别:</span><input type="text" name="Q_priority_S"  class="inputText" value="${param['Q_priority_S']}"/></li>
											
												<li><span class="label">跳转节点:</span><input type="text" name="Q_targetNode_S"  class="inputText" value="${param['Q_targetNode_S']}"/></li>
											
												<li><span class="label">跳转节点名称:</span><input type="text" name="Q_targetNodeName_S"  class="inputText" value="${param['Q_targetNodeName_S']}"/></li>
											
												<li><span class="label">备注:</span><input type="text" name="Q_memo_S"  class="inputText" value="${param['Q_memo_S']}"/></li>
											
									</ul>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmNodeRuleList" id="bpmNodeRuleItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="ruleId" value="${bpmNodeRuleItem.ruleId}">
							</display:column>
							<display:column property="setId" title="节点设置ID" sortable="true" sortName="setId"></display:column>
							<display:column property="ruleName" title="规则名称" sortable="true" sortName="ruleName"></display:column>
								<display:column property="conditionCode" title="规则表达式" sortable="true" sortName="conditionCode" maxLength="80"></display:column>
							<display:column property="actDefId" title="Act流程发布ID" sortable="true" sortName="actDefId"></display:column>
							<display:column property="priority" title="优先级别" sortable="true" sortName="priority"></display:column>
							<display:column property="targetNode" title="跳转节点" sortable="true" sortName="targetNode"></display:column>
							<display:column property="targetNodeName" title="跳转节点名称" sortable="true" sortName="targetNodeName"></display:column>
							<display:column property="memo" title="备注" sortable="true" sortName="memo"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.ht?ruleId=${bpmNodeRuleItem.ruleId}" class="link del">删除</a>
								<a href="edit.ht?ruleId=${bpmNodeRuleItem.ruleId}" class="link edit">编辑</a>
								<a href="get.ht?ruleId=${bpmNodeRuleItem.ruleId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<hotent:paging tableId="bpmNodeRuleItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


