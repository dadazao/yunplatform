<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript">
<!--
	ns.role.loadAllAuth = function(){
		$("#catalog_all_auth").loadUrl("<%=basePath %>/pages/system/role/rolecatalogAuthList.action?module=1");
	}
	
	ns.role.closeAssignAuth = function(){
		$.pdialog.close("assignAuthDialog");
	}
	
	ns.role.saveAssignAuth = function(){
		var ids = '';
		$.each($("div[belong='hasauthId']"),function (entryIndex,entry){
			ids += $(entry).html()+";";
		});
		$.ajax({
			url : "<%=basePath %>/pages/system/role/roleaddAuth.action",
			type: 'post',
			data: 'authIds='+ids+'&roleId=${param.roleId}',
			dataType : "json",
			success : function(data) {
				alertMsg.info("操作成功!");
				$.pdialog.close("assignAuthDialog");
			}
		});
	}
	
	ns.role.loadHasAuth = function(){
		$.ajax({
			url : "<%=basePath %>/pages/system/role/roleloadHasAuth.action",
			type: 'post',
			data: 'roleId=${param.roleId}',
			dataType : "json",
			success : function(data) {
				var _list = data;
				for(var i=0;i<_list.length;i++){
					var auth = _list[i];
					$("#selectAuthList").append("<tr id='"+auth.id+"'><td height='14px' align='center'>"+auth.privilegeName+"<div style='display:none' belong='hasauthId'>"+auth.id+"</div></td><td height='14px' align='center'><div title='删除' onclick='ns.role.deleteTr(this)' style='cursor: pointer;width:12px;height:12px; background-image: url(\"images/jquery/ui-icons_cd0a0a_256x240.png\");background-repeat: no-repeat;background-position: -99px -131px;'></div></td></tr>");
				}
			}
		});
	}
	
	$(function(){
		$("#role_catalogtreeId").load("<%=basePath %>/pages/system/role/catalogTree.action?roleId="+"${param.roleId}");
		ns.role.loadAllAuth();
		ns.role.loadHasAuth();
	});
//-->
</script>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td width="25%">
			<div class="panel" defH="530">
				<h1>
					模块
				</h1>
				<div>
					<div id="role_catalogtreeId"/>
				</div>
			</div>
		</td>
		<td width="40%">
			<div class="panel" defH="530">
				<h1>
					选择权限
				</h1>
				<div id="catalog_all_auth">
					
				</div>
			</div>
		</td>
		<td width="35%">
			<div class="panel" defH="530">
				<h1>
					已有权限
				</h1>
				<div>
					<table border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" width="100%">
						<thead>
							<tr>
								<th height="14px" align="center" style="font-weight: bold;">权限名称</th>
								<th height="14px" align="center" style="font-weight: bold;">操作</th>
							</tr>
						</thead>
						<tbody id="selectAuthList">
						</tbody>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
<div class="buttonPanel" align="center">
	<button type="button" class="listbutton" onclick="ns.role.saveAssignAuth()">
		保存
	</button>
	<button type="button" class="listbutton" onclick="ns.role.closeAssignAuth()">
		取消
	</button>
</div>