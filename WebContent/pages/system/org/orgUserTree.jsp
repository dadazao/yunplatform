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
				fontCss: ns.org.getFontCss,
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
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#orgTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#orgTree').html("树加载失败,请检查参数设置!");
		}
	});
}


ns.org.onClickCallback = function(event, treeId, treeNode){
	$("#add_all_user").loadUrl("<%=basePath %>/pages/system/user/userpUserList.action?relationId="+treeNode.id+"&queryType=org");
}

$(function(){
	ns.org.loadTree();
});

ns.org.getFontCss = function(treeId, treeNode) {
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