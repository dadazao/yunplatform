<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
ns.org.loadTree = function() {
	var setting = {
			view: {
				fontCss: ns.org.getFont,
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
				onClick: ns.org.onClickCallback
			}
		};
	
	var url = '<%=basePath %>/pages/system/org/orgshowTree.action';
	$.ajax({
		url : url,
		async : false,
		data : params,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#orgShowTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#orgShowTree').html("树加载失败,请检查参数设置!");
		}
	});
}


ns.org.onClickCallback = function(event, treeId, treeNode){
	if(treeNode.id == '1') {
		$("#listId").loadUrl("<%=basePath %>/pages/system/org/orglist.action?sysOrg.parentId="+treeNode.id);	
	}else{
		$("#listId").loadUrl("<%=basePath %>/pages/system/org/orglist.action?sysOrg.parentId="+treeNode.id);	
	}
	
}

ns.org.getFont = function(treeId, node) {
	return node.font ? {'color':'red','font-weight': 'bold'} : {} && (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

$(function(){
	$("#orgShowTreeDiv").height(getContentHeight());
	$("#orgShowTree").height(getContentHeight()-10);
	ns.org.loadTree();
});
</script>
<style>
	#orgShowTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<div id="orgShowTreeDiv" style="border:1px solid #CECCCD;border-top:0px;border-bottom:0px;">
	<div class="panelBar" style="padding-left: 10px;line-height: 32px;">机构树</div>
	<div>
		<ul id="orgShowTree" class="ztree"></ul>
	</div>
</div>


