
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义权限明细管理</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
	function save(){
		document.getElementById('dataForm').submit();
	}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">${bpmDefinition.subject}-权限设置</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link add" onclick="save()"><span></span>保存</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-data">
				<form action="save.ht" method="post" id="dataForm">
					<display:table name="bpmDefRtdlList" id="bpmDefRtdlItem"
						requestURI="list.ht" sort="external" cellpadding="1"
						cellspacing="1" export="true" class="table-grid">
						<display:column property="rightId" title="权限定义ID" sortable="true"
							sortName="rightId"></display:column>
						<display:column property="defId" title="定义ID" sortable="true"
							sortName="defId"></display:column>
						<display:column property="typeId" title="分类ID" sortable="true"
							sortName="typeId"></display:column>
						<display:column property="roleId" title="角色ID" sortable="true"
							sortName="roleId"></display:column>
						<display:column property="userId" title="用户ID" sortable="true"
							sortName="userId"></display:column>
						<display:column property="orgId" title="组织ID" sortable="true"
							sortName="orgId"></display:column>
						<display:column title="管理" media="html" style="width:180px">
							<a href="del.ht?detailId=${bpmDefRtdlItem.detailId}"
								class="link del">...</a>
							<a href="edit.ht?detailId=${bpmDefRtdlItem.detailId}"
								class="link edit">清空</a>
						</display:column>
					</display:table>
				</form>
			</div>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


