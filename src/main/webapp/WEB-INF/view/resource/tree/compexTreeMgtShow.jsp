<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">

function loadTable() {
	var setting = {
			view: {
				fontCss: getFontCss,
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
				onClick: onClickCallback
			}
		};
	
	var url = '<%=basePath %>/pages/resource/tree/showtreeByUrl.action';
	var params = 'belongTable=${belongTable}&rootId=${rootId}&nameColumn=${nameColumn}&parentColumn=${parentColumn}&orderColumn=${orderColumn}&expand=false&expandLevel=1&showRoot=${showRoot}';
	$.ajax({
		url : url,
		async : false,
		data : params,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#showTree"), setting, zNodes);
		},
		error : function(msg) {
			$('#showTree').html("树加载失败,请检查参数设置!");
		}
	});
}


function onClickCallback(event, treeId, treeNode){
	$('#${parentName}').val(treeNode.name);
	$('#${parentId}').val(treeNode.id);
	try{
		$.pdialog.close("selectDialog");
	}catch(e){}
	try{
		$.pdialog.close("personSelectDialog");
	}catch(e){}
	//下面为特殊代码！！！
	if('${belongTable}' == 'sys_dictionarys'){
		setTree(treeNode.id);
	}
}

$(function(){
	loadTable();
	ns.common.mouseForButton();
	
	$('#nodesByName').bind('keypress',function(event){
		if(event.keyCode == "13"){
			eventSearchNodes();
		}
	});
});

function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

var nodeList;
function eventSearchNodes() {
	var zTree = $.fn.zTree.getZTreeObj("showTree");
	if(nodeList != undefined){
		for( var i=0; i<nodeList.length;  i++) {
			nodeList[i].highlight = false;
			zTree.updateNode(nodeList[i], false);
			zTree.expandNode(nodeList[i], false, false, false);
			var parentNode = zTree.getNodeByParam("id", nodeList[i].pId, null);
			if(parentNode != null || parentNode!=undefined){
				zTree.expandNode(parentNode, false, false, false);
			}
		}
	}
	nodeList = zTree.getNodesByParamFuzzy("name", $('#nodesByName').val(), null);
	for( var i=0; i<nodeList.length;  i++) {
		nodeList[i].highlight = true;
		zTree.updateNode(nodeList[i], false);
		zTree.expandNode(nodeList[i], true, false, true);
		var parentNode = zTree.getNodeByParam("id", nodeList[i].pId, null);
		if(parentNode != null || parentNode!=undefined){
			zTree.expandNode(parentNode, true, false, true);
		}
	}
}
</script>
<style>
	#showTree.ztree {
		width: 280px;
		height: 510px;
		overflow-y: scroll;
		overflow-x: hidden;
		background-color:#FFFFFF;
	}
	input.empty {color:black;width:200px;}
</style>
<ul>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="80%"><input type="text" id="nodesByName" class="empty" /></td>
    <td width="20%"><button onclick="eventSearchNodes();" style="width:60px;height:24px;" class="listbutton" type="button">检索</button></td>
  </tr>
</table>
</ul><br/>
<ul id="showTree"  class="ztree"></ul>

