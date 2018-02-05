<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
ns.user.loadPositionTree = function() {
	var setting = {
			view: {
				fontCss: ns.user.getPositionFont,
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
				onClick: ns.user.onClickPositionCallback
			},
			check: {
				autoCheckTrigger: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "s" },
				enable: true,
			}
		};
	
	var url = '<%=basePath %>/pages/system/position/positionshowTree.action';
	$.ajax({
		url : url,
		async : false,
		data : params,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#positionTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#positionTree').html("树加载失败,请检查参数设置!");
		}
	});
}


ns.user.onClickPositionCallback = function(event, treeId, treeNode){
	
}

ns.user.getPositionFont = function(treeId, node) {
	return node.font ? {'color':'red','font-weight': 'bold'} : {} && (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

$(function(){
	$("#positionTree").height($("#desktop").height()-10);
	ns.user.loadPositionTree();
});
</script>
<style>
	#positionTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<div>
	<ul id="positionTree" class="ztree"></ul>
</div>

