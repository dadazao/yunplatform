<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
ns.position.loadDialogPositionTree = function() {
	var setting = {
			view: {
				fontCss: ns.position.getDialogPositionFont,
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
			$.fn.zTree.init($("#dialogPositionTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#dialogPositionTree').html("树加载失败,请检查参数设置!");
		}
	});
}

ns.position.getDialogPositionFont = function(treeId, node) {
	return node.font ? {'color':'red','font-weight': 'bold'} : {} && (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

ns.position.selectPositionDialogReturnValue = function(){
	var ids = '';
	var fullNames = '';
	
	var treeObj = $.fn.zTree.getZTreeObj("dialogPositionTree");
	var nodes = treeObj.getCheckedNodes(true);
	for(var i = 0;i<nodes.length;i++){
		if(i==0){
			ids+=nodes[i].id;
			fullNames+=nodes[i].name;
		}else{
			ids+=","+nodes[i].id;
			fullNames+=","+nodes[i].name;
		}
	}
	
	var currentDialog = $.pdialog.getCurrent();
	currentDialog.data('param','{positionIds:"'+ids+'",positionNames:"'+fullNames+'"}');
	$.pdialog.close("selectPositionDialog");
}

ns.position.closeSelectPositionDialog = function(){
	$.pdialog.close("selectPositionDialog");
}

$(function(){
	$("#dialogPositionTree").height(425);
	ns.position.loadDialogPositionTree();
});
</script>
<style>
	#dialogPositionTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<div>
	<ul id="dialogPositionTree" class="ztree"></ul>
	<p align="center">
	<input type="button" class="listbutton" onclick="ns.position.selectPositionDialogReturnValue()" value="确定"/>
	<input type="button" class="listbutton" onclick="ns.position.closeSelectPositionDialog()" value="取消"/>
	</p>
</div>