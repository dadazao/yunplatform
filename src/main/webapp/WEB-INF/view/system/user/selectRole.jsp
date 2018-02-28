<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript">
<!--
	$(function(){
		$("#allRoleTreeDiv").load("<%=basePath %>/pages/system/user/roleTree.action");
		$("#selectedRoleListDiv").loadUrl("<%=basePath %>/pages/system/user/userselectedRoleList.action?userId="+$("#userId").val());
	});
	
	ns.user.addUserRole = function(){
		var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		var nodes = treeObj.getCheckedNodes(true);
		var roleIds = "";
		for(var i = 0;i<nodes.length;i++){
			if(i==0){
				roleIds+=nodes[i].id;
			}else{
				roleIds+=";"+nodes[i].id;
			}
		}
		
		$.ajax({
			type:'post',
			url: "<%=basePath %>/pages/system/user/useraddUserRole.action?roleIds="+roleIds+"&userId="+$("#userId").val(),
			success: function(){
				$("#selectedRoleListDiv").loadUrl("<%=basePath %>/pages/system/user/userselectedRoleList.action?userId="+$("#userId").val());
				alertMsg.info("添加成功!");
			}
		
		});
	}
//-->
</script>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%">
			<div class="panel" defH="485">
				<h1>所有角色</h1>
			    <div id="allRoleTreeDiv"></div>
			</div>
		</td>
		<td width="5%"><button type="button" style="width:80px;" class="listbutton" onclick="ns.user.addUserRole();">添加>></button></td>
		<td width="70%">
			<div class="panel" defH="485">
				<h1>已选角色</h1>
			    <div id="selectedRoleListDiv"></div>
			</div>
		</td>
	</tr>
</table>