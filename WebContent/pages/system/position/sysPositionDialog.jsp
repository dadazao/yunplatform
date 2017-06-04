<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript">
<!--
	ns.position.selectedPositionIds = '';
	ns.position.selectedPositionNames = '';
	
	ns.position.loadPositionTree = function() {
		var setting = {view: {fontCss: ns.position.getPositionFontCss,showTitle: false},
				data : {simpleData : {enable : true,idKey: "id",pIdKey: "pId"}},
				callback: {
					onCheck: ns.position.onCheckPositionCallback
				},
				check: {autoCheckTrigger: true,chkStyle: "checkbox",chkboxType: { "Y": "s", "N": "s" },	enable: true,}
			};
		var url = '<%=basePath %>/pages/system/position/positionshowTree.action';
		$.ajax({
			url : url,
			async : false,
			dataType : "json",
			success : function(zNodes) {
				$.fn.zTree.init($("#positionTree"), setting, zNodes);
				var treeObj = $.fn.zTree.getZTreeObj("positionTree");
				treeObj.expandAll(true);
			},
			error : function(msg) {
				$('#positionTree').html("树加载失败,请检查参数设置!");
			}
		});
	}
	ns.position.getPositionFontCss = function(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	
	ns.position.onCheckPositionCallback = function(event, treeId, treeNode){
		var treeObj = $.fn.zTree.getZTreeObj("positionTree");
		var nodes = treeObj.getCheckedNodes(true);
		var _html = '';
		ns.position.selectedPositionIds = '';
		ns.position.selectedPositionNames = '';
		for(var i = 0;i<nodes.length;i++){
			if(nodes[i].isParent){
				continue;
			}
			ns.position.selectedPositionIds+=nodes[i].id+',';
			ns.position.selectedPositionNames+=nodes[i].name+',';
			_html+='<tr><td align="center" width="80%">'+nodes[i].name+'</td></tr>';//<td align="center" width="20%"><a onclick="ns.position.removePosition(this,'+nodes[i].id+')">删除</a></td></tr>';
		}
		ns.position.selectedPositionIds = ns.position.selectedPositionIds.substring(0,ns.position.selectedPositionIds.length-1);
		ns.position.selectedPositionNames = ns.position.selectedPositionNames.substring(0,ns.position.selectedPositionNames.length-1);
		$('#selectedPositionListTbody').html(_html);
	}
	
	$(function(){
		ns.position.loadPositionTree();
	});
	ns.position.selectPositionDialogReturnValue = function(){
		var currentDialog = $.pdialog.getCurrent();
		currentDialog.data('param',{positionIds:ns.position.selectedPositionIds,positionNames:ns.position.selectedPositionNames});
		$.pdialog.close("selectPositionDialog");
	}
	ns.position.closeSelectPositionDialog = function(){
		$.pdialog.close('selectPositionDialog');
	}
//-->
</script>
<style>
	#roleTree.ztree {overflow-y: auto;overflow-x: auto;	background-color:#FFFFFF;}
</style>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td width="30%">
			<div class="panel" defH="390">
				<h1>所有岗位</h1>
			   <div><ul id="positionTree" class="ztree"></ul></div>
			</div>
		</td>
		<td width="70%">
			<div class="panel" defH="390">
				<h1>已选岗位</h1>
			    <div>
			    	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="table" style=" border-left:1px #ededed solid;">
			    	<thead><tr><th class="thClass">岗位名称</th></tr></thead>
					<tbody id="selectedPositionListTbody"></tbody>
					</table>
			    </div>
			</div>
		</td>
	</tr>
</table>
<div class="buttonPanel" align="center">
	<button type="button" class="listbutton" onclick="ns.position.selectPositionDialogReturnValue()">确定</button>
	<button type="button" class="listbutton" onclick="ns.position.closeSelectPositionDialog();">取消</button>
</div>