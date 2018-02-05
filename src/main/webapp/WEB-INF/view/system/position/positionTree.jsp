<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
ns.position.loadTree = function() {
	var setting = {
			view: {
				fontCss: ns.position.getFont,
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
				onClick: ns.position.onClickCallback
			}
		};
	
	var url = '<%=basePath %>/pages/system/position/positionshowTree.action';
	$.ajax({
		url : url,
		async : false,
		data : params,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#positionShowTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#positionShowTree').html("树加载失败,请检查参数设置!");
		}
	});
}


ns.position.onClickCallback = function(event, treeId, treeNode){
	if(treeNode.id == '1') {
		$("#listId").loadUrl("<%=basePath %>/pages/system/position/positionlist.action?position.parentId="+treeNode.id);	
	}else{
		$("#listId").loadUrl("<%=basePath %>/pages/system/position/positionlist.action?position.parentId="+treeNode.id);	
	}
	
}

ns.position.getFont = function(treeId, node) {
	return node.font ? {'color':'red','font-weight': 'bold'} : {} && (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

$(function(){
	$("#positionShowTreeDiv").height(getContentHeight());
	$("#positionShowTree").height($("#desktop").height()-10);
	ns.position.loadTree();
});
</script>
<style>
	#positionShowTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<div id="positionShowTreeDiv" style="border:1px solid #CECCCD;border-top:0px;border-bottom:0px;">
	<div class="panelBar" style="padding-left: 10px;line-height: 32px;">岗位树</div>
	<div>
		<ul id="positionShowTree" class="ztree"></ul>
	</div>
</div>

