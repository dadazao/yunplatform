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
			callback: {
				onCheck: ns.privilege.onCheckCallback
			},
			check: {
				autoCheckTrigger: false,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "s" },
				enable: true,
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
	//$("#catalog_all_auth").loadUrl("<%=basePath %>/pages/system/role/rolecatalogAuthList.action?module="+treeNode.id);
}

ns.privilege.onCheckCallback = function(event, treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj("privilegeShowTree");
	var nodes = treeObj.getCheckedNodes(true);
	var catalogIds = "";
	for(var i = 0;i<nodes.length;i++){
		if(i==0){
			catalogIds+=nodes[i].id;
		}else{
			catalogIds+=";"+nodes[i].id;
		}
	}
	$("#catalog_all_auth").loadUrl("<%=basePath %>/pages/system/role/rolecatalogAuthList.action?modules="+catalogIds+"&roleId="+"${param.roleId}");
}

ns.privilege.getFont = function(treeId, node) {
	return node.font ? {'color':'red','font-weight': 'bold'} : {} && (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

$(function(){
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
<div>
	<ul id="privilegeShowTree" class="ztree" style="height:500px;"></ul>
</div>

