<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript">
<!--
	ns.org.selectedOrgIds = '';
	ns.org.selectedOrgNames = '';
	
	ns.org.loadOrgTree = function() {
		var setting = {view: {fontCss: ns.org.getOrgFontCss,showTitle: false},
				data : {simpleData : {enable : true,idKey: "id",pIdKey: "pId"}},
				callback: {
					onCheck: ns.org.onCheckOrgCallback
				},
				check: {autoCheckTrigger: true,chkStyle: "checkbox",chkboxType: { "Y": "s", "N": "s" },	enable: true,}
			};
		var url = '<%=basePath %>/pages/system/org/orgshowTree.action';
		$.ajax({
			url : url,
			async : false,
			dataType : "json",
			success : function(zNodes) {
				$.fn.zTree.init($("#orgTree"), setting, zNodes);
				var treeObj = $.fn.zTree.getZTreeObj("orgTree");
				treeObj.expandAll(true);
			},
			error : function(msg) {
				$('#orgTree').html("树加载失败,请检查参数设置!");
			}
		});
	}
	ns.org.getOrgFontCss = function(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	
	ns.org.onCheckOrgCallback = function(event, treeId, treeNode){
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = treeObj.getCheckedNodes(true);
		var _html = '';
		ns.org.selectedOrgIds = '';
		ns.org.selectedOrgNames = '';
		for(var i = 0;i<nodes.length;i++){
			if(nodes[i].isParent){
				continue;
			}
			ns.org.selectedOrgIds+=nodes[i].id+',';
			ns.org.selectedOrgNames+=nodes[i].name+',';
			_html+='<tr><td align="center" width="80%">'+nodes[i].name+'</td></tr>';//<td align="center" width="20%"><a onclick="ns.org.removeOrg(this,'+nodes[i].id+')">删除</a></td></tr>';
		}
		ns.org.selectedOrgIds = ns.org.selectedOrgIds.substring(0,ns.org.selectedOrgIds.length-1);
		ns.org.selectedOrgNames = ns.org.selectedOrgNames.substring(0,ns.org.selectedOrgNames.length-1);
		$('#selectedOrgListTbody').html(_html);
	}
	
	$(function(){
		ns.org.loadOrgTree();
	});
	ns.org.selectOrgDialogReturnValue = function(){
		var currentDialog = $.pdialog.getCurrent();
		currentDialog.data('param',{orgIds:ns.org.selectedOrgIds,orgNames:ns.org.selectedOrgNames});
		$.pdialog.close("selectOrgDialog");
	}
	ns.org.closeSelectOrgDialog = function(){
		$.pdialog.close('selectOrgDialog');
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
				<h1>所有机构</h1>
			   <div><ul id="orgTree" class="ztree"></ul></div>
			</div>
		</td>
		<td width="70%">
			<div class="panel" defH="390">
				<h1>已选机构</h1>
			    <div>
			    	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="table" style=" border-left:1px #ededed solid;">
			    	<thead><tr><th class="thClass">机构名称</th></tr></thead>
					<tbody id="selectedOrgListTbody"></tbody>
					</table>
			    </div>
			</div>
		</td>
	</tr>
</table>
<div class="buttonPanel" align="center">
	<button type="button" class="listbutton" onclick="ns.org.selectOrgDialogReturnValue()">确定</button>
	<button type="button" class="listbutton" onclick="ns.org.closeSelectOrgDialog();">取消</button>
</div>