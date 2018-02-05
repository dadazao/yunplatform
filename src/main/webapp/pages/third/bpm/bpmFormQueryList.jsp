<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>通用表单查询管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript">
	function preview(alias){
		CommonQuery(alias);
	}
</script>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">通用表单查询管理列表</span>
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
						<li><span class="label">名称:</span><input type="text" name="Q_name_SL"  class="inputText" value="${param['Q_name_SL']}"/></li>
						<li><span class="label">别名:</span><input type="text" name="Q_alias_SL"  class="inputText" value="${param['Q_alias_SL']}"/></li>
					</ul>
				</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bpmFormQueryList" id="bpmFormQueryItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${bpmFormQueryItem.id}">
				</display:column>
				<display:column property="name" title="名称" sortable="true" sortName="name"></display:column>
				<display:column property="alias" title="别名" sortable="true" sortName="alias"></display:column>
				<display:column property="objName" title="对象名称" sortable="true" sortName="objName"></display:column>
				<display:column property="dsalias" title="数据源名称" sortable="true" sortName="dsalias"></display:column>
				<display:column title="管理" media="html" style="width:50px;text-align:center" class="rowOps">
					<a href="del.ht?id=${bpmFormQueryItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${bpmFormQueryItem.id}" class="link edit">编辑</a>
					<a href="javascript:preview('${bpmFormQueryItem.alias}')" class="link detail">预览</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="bpmFormQueryItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	
	<div id="divJsonData" style="display: none;">
		<div>查询返回的JSON格式数据。</div>
		<ul>
			<li>为JSON数组,字段为return字段。</li>
		</ul>
		<textarea id="txtJsonData" rows="10" cols="80" style="height: 180px;width:480px"></textarea>
	</div>
</body>
</html>


