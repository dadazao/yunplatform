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
		$("#allPositionTreeDiv").load("<%=basePath %>/pages/system/user/positionTree.jsp");
		$("#selectedPositionListDiv").loadUrl("<%=basePath %>/pages/system/user/userselectedPositionList.action?userId="+$("#userId").val());
	});
	
	ns.user.addUserPosition = function(){
		var treeObj = $.fn.zTree.getZTreeObj("positionTree");
		var nodes = treeObj.getCheckedNodes(true);
		var positionIds = "";
		for(var i = 0;i<nodes.length;i++){
			if(i==0){
				positionIds+=nodes[i].id;
			}else{
				positionIds+=";"+nodes[i].id;
			}
		}
		
		$.ajax({
			type:'post',
			url: "<%=basePath %>/pages/system/user/useraddUserPosition.action?positionIds="+positionIds+"&userId="+$("#userId").val(),
			success: function(){
				$("#selectedPositionListDiv").loadUrl("<%=basePath %>/pages/system/user/userselectedPositionList.action?userId="+$("#userId").val());
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
				<h1>岗位</h1>
			    <div id="allPositionTreeDiv"></div>
			</div>
		</td>
		<td width="5%"><button type="button" style="width:80px;" class="listbutton" onclick="ns.user.addUserPosition();">添加>></button></td>
		<td width="70%">
			<div class="panel" defH="485">
				<h1>已选岗位</h1>
			    <div id="selectedPositionListDiv"></div>
			</div>
		</td>
	</tr>
</table>