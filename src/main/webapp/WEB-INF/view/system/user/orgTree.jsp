<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">

ns.user.loadOrgTree = function() {
	var setting = {
			view: {
				fontCss: ns.user.getOrgFontCss,
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
				onClick: ns.user.onClickOrgCallback
			},
			check: {
				autoCheckTrigger: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "s" },
				enable: true,
			}
		};
	
	var url = '<%=basePath %>/pages/system/org/orgshowTree.action';
	$.ajax({
		url : url,
		async : false,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#orgTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#orgTree').html("树加载失败,请检查参数设置!");
		}
	});
}


ns.user.onClickOrgCallback = function(event, treeId, treeNode){
	
}

$(function(){
	ns.user.loadOrgTree();
});

ns.user.getOrgFontCss = function(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}
</script>
<style>
	#orgTree.ztree {
		overflow-y: auto;
		overflow-x: auto;
		background-color:#FFFFFF;
	}
</style>
<div>
	<ul id="orgTree"  class="ztree"></ul>
</div>