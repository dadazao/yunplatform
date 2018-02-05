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
		$("#allOrgTreeDiv").load("<%=basePath %>/pages/system/user/orgTree.jsp");
		$("#selectedOrgListDiv").loadUrl("<%=basePath %>/pages/system/user/userselectedOrgList.action?userId="+$("#userId").val());
	});
	
	ns.user.addUserOrg = function(){
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = treeObj.getCheckedNodes(true);
		var orgIds = "";
		for(var i = 0;i<nodes.length;i++){
			if(i==0){
				orgIds+=nodes[i].id;
			}else{
				orgIds+=";"+nodes[i].id;
			}
		}
		
		$.ajax({
			type:'post',
			url: "<%=basePath %>/pages/system/user/useraddUserOrg.action?orgIds="+orgIds+"&userId="+$("#userId").val(),
			success: function(){
				$("#selectedOrgListDiv").loadUrl("<%=basePath %>/pages/system/user/userselectedOrgList.action?userId="+$("#userId").val());
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
				<h1>机构</h1>
			    <div id="allOrgTreeDiv"></div>
			</div>
		</td>
		<td width="5%"><button type="button" style="width:80px;" class="listbutton" onclick="ns.user.addUserOrg();">添加>></button></td>
		<td width="70%">
			<div class="panel" defH="485">
				<h1>已选机构</h1>
			    <div id="selectedOrgListDiv"></div>
			</div>
		</td>
	</tr>
</table>