<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<script type="text/javascript">alert(1);
			var settings = {
				check : {
					enable : true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data : {
					simpleData : {
						enable : true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: 0						
					}
				},
				callback: {
					onCheck: onCheck
				}
			};
		
			$(document).ready(function() {
				$('#yunDialog').attr('style','height: 100%;OVERFLOW-x:hidden;OVERFLOW-Y:auto;');
				$('ul[class="ztree"]').css({'height':(fDefaultHeight-100)+'px'});
				$.ajax({
					url : "<%=basePath %>/pages/resource/personChoise/depmUserTree.action",
					dataType : "json",
					data: 'treeId=${treeId}',
					success : function(zNodes) {
						$.fn.zTree.init($("#privilegeTree"), settings, zNodes);
					},
					error : function(msg) {
						alertMsg.warn("数据返回异常");
					}
				});
			})
			
			function onCheck(){
				getCheckedNodes();
			}
			
			function getCheckedNodes(){
				var treeObj = $.fn.zTree.getZTreeObj("privilegeTree");
				var nodes = treeObj.getCheckedNodes();
				if(nodes.length>0){
					var _html = '';
					for (var i=0,j=1; i<nodes.length; i++) {
						if(nodes[i].isParent == false){
							_html += '<tr><td class="tdClass" style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">'+j+'</td><td class="tdClass" style=" border-bottom:1px #ededed solid; border-right:1px #ededed solid;">'+nodes[i].name+'</td></tr>';
							j++;
						}
					}
					$('#persionList').html(_html);
				}else{
					$('#persionList').html('');
				}
			}
			
			function setCheckedNodes(){
				var treeObj = $.fn.zTree.getZTreeObj("privilegeTree");
				var nodes = treeObj.getCheckedNodes();
				var _userId = '';
				var _personName = '';
				for (var i=0; i<nodes.length; i++) {
					if(nodes[i].isParent == false){
						if(nodes[i].id != ''){
							_userId += nodes[i].id+';';
						}
						_personName +=nodes[i].name+';';
					}
				}
				_userId = _userId.substring(0, _userId.length-1);
				$("#"+'${choiseName}').val(_personName);
				$("#"+'${choiseId}').val(_userId);
				closeCurPage();
			}
			
			function closeCurPage(){
				$.pdialog.close("selectDialog");
			}
		</script>
		<style>
			ul.ztree {
				border: 1px solid #617775;
				overflow-y: scroll;
				overflow-x: auto;
			}
		</style>
	</head>

	<body>
		<div id="yunDialog">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				
			  <tr>
			    <td width="30%" align="left" valign="top">
			    	<ul id="privilegeTree" class="ztree" ></ul>
			    </td>
			    <td width="70%" align="center" valign="top">
				    <ul class="ztree">
					    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
						    <tbody id="persionList">
						    	<thead>
							    <tr>
							    	<th class="thClass" style="width:5%; border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">序号</th>
							    	<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">用户姓名</th>
							    </tr>
							    </thead>
						    </tbody>
					    </table>
				    </ul>
			    </td>
			  </tr>
			  <tr>
			    <td colspan="2">
			    	<button style="width:60px;heith:21px;float:right;" onclick="closeCurPage();" class="listbutton" name="b1">取消</button>
			    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button style="width:60px;heith:21px;float:right;" onclick="setCheckedNodes();" class="listbutton" name="b1">确定</button>
			    </td>
			  </tr>
			</table>
		</div>
	</body>
</html>
