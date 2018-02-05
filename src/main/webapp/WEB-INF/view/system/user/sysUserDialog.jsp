<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path;
%>
<script type="text/javascript">
<!--
	ns.user.loadAllDialogUserList = function(){
		$("#add_all_user").loadUrl("<%=basePath %>/pages/system/user/dialogUserList.action?relationId=1");
	}
	
	ns.user.closeSelectUserDialog = function(){
		$.pdialog.close("selectUserDialog");
	}
	
	ns.user.selectUserDialogReturnValue = function(){
		var ids = '';
		var fullNames = '';
		$.each($("div[belong='userIds']"),function (entryIndex,entry){
			ids += $(entry).html()+",";
		});
		ids = ids.substring(0,ids.length-1);
		$.each($("div[belong='userNames']"),function (entryIndex,entry){
			fullNames += $(entry).html()+",";
		});
		fullNames = fullNames.substring(0,fullNames.length-1);
		
		var currentDialog = $.pdialog.getCurrent();
		currentDialog.data('param','{userIds:"'+ids+'",fullNames:"'+fullNames+'"}');
		$.pdialog.close("selectUserDialog");
	}
	
	ns.user.loadOrgTree = function() {
		var setting = {
				view: {
					fontCss: ns.user.getOrgTreeFontCss,
					showTitle: false
				},
				data : {
					simpleData : {
						enable : true,
						idKey: "id",
						pIdKey: "pId"					
					}
				},
				callback: {
					onClick: ns.user.onClickOrgTreeCallback
				}
			};
		
		var url = '<%=basePath %>/pages/system/org/orgshowTree.action';
		$.ajax({
			url : url,
			async : false,
			dataType : "json",
			success : function(zNodes) {
				$.fn.zTree.init($("#orgTree"), setting, zNodes);
			},
			error : function(msg) {
				$('#orgTree').html("树加载失败,请检查参数设置!");
			}
		});
	}
	
	ns.user.onClickOrgTreeCallback = function(event, treeId, treeNode){
		$("#add_all_user").loadUrl("<%=basePath %>/pages/system/user/dialogUserList.action?relationId="+treeNode.id+"&queryType=org");
	}
	
	ns.user.getOrgTreeFontCss = function(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	
	$(function(){
		ns.user.loadOrgTree();
		ns.user.loadAllDialogUserList();		
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
		<td width="25%">
			<div class="accordion">
				<div class="accordionHeader">
					<h2>
						<span>icon</span>按组织查找
					</h2>
				</div>
				<div class="accordionContent" style="height:350px">
					<ul id="orgTree"  class="ztree"></ul>
				</div>
				<div class="accordionHeader">
					<h2>
						<span>icon</span>按岗位查找
					</h2>
				</div>
				<div class="accordionContent">
					
				</div>
				<div class="accordionHeader">
					<h2>
						<span>icon</span>按角色查找
					</h2>
				</div>
				<div class="accordionContent">
					
				</div>
			</div>
		</td>
		<td width="55%">
			<div class="panel" defH="390">
				<h1>
					用户列表
				</h1>
				<div id="add_all_user">
				</div>
			</div>
		</td>
		<td width="20%">
			<div class="panel" defH="390">
				<h1>
					选择用户
				</h1>
				<div>
					<table border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" width="100%">
						<thead>
							<tr>
								<th height="14px" align="center" style="font-weight: bold;">用户姓名</th>
								<th height="14px" align="center" style="font-weight: bold;">操作</th>
							</tr>
						</thead>
						<tbody id="selectUserList">
						</tbody>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
<div class="buttonPanel" align="center">
	<button type="button" class="listbutton" onclick="ns.user.selectUserDialogReturnValue()">
		确定
	</button>
	<button type="button" class="listbutton" onclick="ns.user.closeSelectUserDialog();">
		取消
	</button>
</div>