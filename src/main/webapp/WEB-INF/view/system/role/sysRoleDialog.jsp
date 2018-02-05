<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path;
%>
<script type="text/javascript">
<!--
	ns.role.loadAllDialogRoleList = function(){
		$("#add_all_role").loadUrl("<%=basePath %>/pages/system/role/dialogRoleList.action");
	}
	
	ns.role.closeSelectRoleDialog = function(){
		$.pdialog.close("selectRoleDialog");
	}
	
	ns.role.selectRoleDialogReturnValue = function(){
		var ids = '';
		var fullNames = '';
		$.each($("div[belong='roleIds']"),function (entryIndex,entry){
			ids += $(entry).html()+",";
		});
		ids = ids.substring(0,ids.length-1);
		$.each($("div[belong='roleNames']"),function (entryIndex,entry){
			fullNames += $(entry).html()+",";
		});
		fullNames = fullNames.substring(0,fullNames.length-1);
		
		var currentDialog = $.pdialog.getCurrent();
		currentDialog.data('param','{roleIds:"'+ids+'",roleNames:"'+fullNames+'"}');
		$.pdialog.close("selectRoleDialog");
	}
	
	$(function(){
		ns.role.loadAllDialogRoleList();		
	});
//-->
</script>
<style>
	#orgTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td width="75%">
			<div class="panel" defH="390">
				<h1>
					角色列表
				</h1>
				<div id="add_all_role">
				</div>
			</div>
		</td>
		<td width="30%">
			<div class="panel" defH="390">
				<h1>
					选择角色
				</h1>
				<div>
					<table border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" width="100%">
						<thead>
							<tr>
								<th height="14px" align="center" style="font-weight: bold;">角色</th>
								<th height="14px" align="center" style="font-weight: bold;">操作</th>
							</tr>
						</thead>
						<tbody id="selectRoleList">
						</tbody>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
<div class="buttonPanel" align="center">
	<button type="button" class="listbutton" onclick="ns.role.selectRoleDialogReturnValue()">
		确定
	</button>
	<button type="button" class="listbutton" onclick="ns.role.closeSelectRoleDialog();">
		取消
	</button>
</div>