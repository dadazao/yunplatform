<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<script type="text/javascript">
ns.attachment.loadTree = function() {
	var setting = {
			view: {
				fontCss: ns.attachment.getFont,
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
				onClick: ns.attachment.onClickCallback
			}
		};
	
	var url = '<%=basePath%>/pages/system/privilege/privilegeshowCatalogTree.action';
	$.ajax({
		url : url,
		async : false,
		data : params,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#attachmentShowTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#attachmentShowTree').html("树加载失败,请检查参数设置!");
		}
	});
}


ns.attachment.onClickCallback = function(event, treeId, treeNode){
	if(treeNode.id != '1') {
		$("#listId").loadUrl("<%=basePath%>/pages/resource/attachment/attachmentattachmentList.action?module="+treeNode.id);	
	}
}

ns.attachment.getFont = function(treeId, node) {
	return node.font ? {'color':'red','font-weight': 'bold'} : {} && (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

$(function(){
	$("#attachmentShowTree").height($("#desktop").height()-10);
	ns.attachment.loadTree();
});
</script>
<style>
	#attachmentShowTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<div>
	<ul id="attachmentShowTree" class="ztree"></ul>
</div>

