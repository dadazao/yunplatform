<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子表权限管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">子表权限管理列表</span>
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
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<ul class="row">
						<li><span class="label">流程定义ID:</span><input type="text" name="Q_defid_S"  class="inputText" value="${param['Q_defid_S']}"/></li>
						<li><span class="label">节点ID:</span><input type="text" name="Q_nodeid_S"  class="inputText" value="${param['Q_nodeid_S']}"/></li>
						<li><span class="label">子表表ID:</span><input type="text" name="Q_tableid_S"  class="inputText" value="${param['Q_tableid_S']}"/></li>
						<li><span class="label">权限类型(1,简单配置,2,脚本):</span><input type="text" name="Q_permissiontype_S"  class="inputText" value="${param['Q_permissiontype_S']}"/></li>
						<li><span class="label">权限配置:</span><input type="text" name="Q_permissionseting_S"  class="inputText" value="${param['Q_permissionseting_S']}"/></li>
					</ul>
				</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="BpmSubtableRightsList" id="BpmSubtableRightsItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${BpmSubtableRightsItem.id}">
				</display:column>
				<display:column property="defid" title="流程定义ID" sortable="true" sortName="defid"></display:column>
				<display:column property="nodeid" title="节点ID" sortable="true" sortName="nodeid"></display:column>
				<display:column property="tableid" title="子表表ID" sortable="true" sortName="tableid"></display:column>
				<display:column property="permissiontype" title="权限类型(1,简单配置,2,脚本)" sortable="true" sortName="permissiontype"></display:column>
				<display:column property="permissionseting" title="权限配置" sortable="true" sortName="permissionseting" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${BpmSubtableRightsItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${BpmSubtableRightsItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${BpmSubtableRightsItem.id}" class="link detail">明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="BpmSubtableRightsItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


