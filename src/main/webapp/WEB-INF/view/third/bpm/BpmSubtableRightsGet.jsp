
<%--
	time:2013-01-16 10:13:31
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>子表权限明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">子表权限详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">流程定义ID:</th>
				<td>${BpmSubtableRights.defid}</td>
			</tr>
 
			<tr>
				<th width="20%">节点ID:</th>
				<td>${BpmSubtableRights.nodeid}</td>
			</tr>
 
			<tr>
				<th width="20%">子表表ID:</th>
				<td>${BpmSubtableRights.tableid}</td>
			</tr>
 
			<tr>
				<th width="20%">权限类型(1,简单配置,2,脚本):</th>
				<td>${BpmSubtableRights.permissiontype}</td>
			</tr>
 
			<tr>
				<th width="20%">权限配置:</th>
				<td>${BpmSubtableRights.permissionseting}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

