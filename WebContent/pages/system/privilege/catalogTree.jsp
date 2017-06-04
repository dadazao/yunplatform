<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
ns.privilege.loadTree = function() {
	var setting = {
			view: {
				fontCss: ns.privilege.getFont,
				showTitle: false
			},
			data : {
				simpleData : {
					enable : true,
					idKey: "id",
					pIdKey: "pId"					
				}
			},
			check: {
				autoCheckTrigger: false,
				chkStyle: "radio",
				enable: true,
				radioType : "all"
			},
			callback: {
				onClick: ns.privilege.onClickCallback,
				onCheck: ns.privilege.onCheckCallback
			}
		};
	
	var url = '<%=basePath %>/pages/system/privilege/privilegeshowCatalogTree.action';
	$.ajax({
		url : url,
		async : false,
		data : params,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#privilegeShowTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#privilegeShowTree').html("树加载失败,请检查参数设置!");
		}
	});
}


ns.privilege.onClickCallback = function(event, treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj("privilegeShowTree");
	if(treeNode.id != '1') {
		$("#listId").loadUrl("<%=basePath %>/pages/system/privilege/privilegeauthList.action?module="+treeNode.id);	
		treeObj.checkNode(treeNode, true, true);
	}
}

ns.privilege.onCheckCallback = function(event, treeId, treeNode){
	if(treeNode.id != '1') {
		$("#listId").loadUrl("<%=basePath %>/pages/system/privilege/privilegeauthList.action?module="+treeNode.id);	
	}
}

ns.privilege.getFont = function(treeId, node) {
	return node.font ? {'color':'red','font-weight': 'bold'} : {} && (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

$(function(){
	$("#privilegeShowTree").height($("#desktop").height()-10);
	ns.privilege.loadTree();
});
</script>
<style>
	#privilegeShowTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<div class="panelBar" style="padding-left: 10px;line-height: 32px;">模块树</div>
<div>
	<ul id="privilegeShowTree" class="ztree"></ul>
</div>

