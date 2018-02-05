<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>流程定义导出</title>
<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript">
	window.name="win";
			
	$(function(){	
		$("#btnSave").click(function(){
			$("#exportForm").submit();
		});
	});
	
	
	function onChecked(me,obj){
/* 		if($(me).attr('checked')==undefined){
			$('#'+obj).attr('checked',false); 
		}*/
	}
	
	function onCheck(me,obj){
		if($(me).attr('checked')=='checked'){
			$('#'+obj).attr('checked',true);
		}
	}
	
	
	</script>
</head>
<base target="download"> 
<body>
	<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程定义导出</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="btnSave"><span></span>导出</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" onclick="javasrcipt:window.close()"><span></span>关闭</a></div>
				
				</div>	
			</div>
	</div>
	<div class="panel-body">
		<form id="exportForm" name="exportForm" method="post" target="download" action="exportXml.ht">
			<div class="row">
			 <input type="hidden" name="bpmDefIds" value="${bpmDefIds}" >
			 <table id="tableid" class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 	<tr>
			 		<th width="30%">流程定义：</th>
			 		<td width="20%"><input type="checkbox"  name="bpmDefinition" checked="checked" disabled="disabled" value="true"></td>
			 		<th width="30%">历史版本：</th>
			 		<td width="20%"><input type="checkbox"  name="bpmDefinitionHistory" checked="checked" value="true"></td>	
			 	</tr>	
			 	<tr>
			 		<th>流程节点设置：</th>
			 		<td><input type="checkbox"  name="bpmNodeSet" checked="checked" disabled="disabled" value="true"></td>
					<th>流程人员设置：</th>
			 		<td><input type="checkbox" id="bpmNodeUser"  name="bpmNodeUser" checked="checked"  value="true" ></td>		
			 	</tr>
			 	<tr>
			 		<th>流程定义权限：</th>
			 		<td><input type="checkbox"  name="bpmDefRights" checked="checked" value="true"></td>
			 		<th>常用语设置：</th>
			 		<td><input type="checkbox"  name="taskApprovalItems" checked="checked" value="true"></td> 		
			 	</tr>
			 	<tr>
			 		<th>流程跳转规则：</th>
			 		<td><input type="checkbox"  name="bpmNodeRule" checked="checked" value="true"></td>		
			 		<th>流程事件脚本：</th>
			 		<td><input type="checkbox"  name="bpmNodeScript" checked="checked" value="true"></td> 
			 	</tr>
			    <tr>
			 		<th>流程操作按钮设置：</th>
			 		<td><input type="checkbox"  name="bpmNodeButton" checked="checked" value="true"></td>
			 		<th>流程变量：</th>
			 		<td><input type="checkbox"  name="bpmDefVar" checked="checked" value="true"></td> 		
			 	</tr>
			 	<tr>
		 		 	<th>流程节点催办时间设置：</th>
			 		<td><input type="checkbox"  name="taskReminder" checked="checked" value="true"></td>
			 		<th>流程会签规则：</th>
			 		<td><input type="checkbox"  name="bpmNodeSign" checked="checked" value="true"></td> 		
			 	</tr>
				<tr>
					 <th>流程消息：</th>
					<td><input type="checkbox"  name="bpmNodeMessage" checked="checked" value="true"></td>
			 		<th>内（外）部子流程：</th>
			 		<td><input type="checkbox"  name="subBpmDefinition" checked="checked" value="true"></td>		
			 	</tr>
			 	<tr>
					<th>自定义表单和其对应的表：</th>
					<td><input type="checkbox" id="bpmFormDef" name="bpmFormDef" checked="checked" value="true" onclick="onChecked(this,'bpmFormTable');"></td>
					<th></th>
					<td></td>
			 	</tr>
			 	
			</table>				
			
			</div>
	    </form>
	</div><!-- end of panel-body -->
<iframe id="download" name="download" height="0px" width="0px"></iframe>				
</body>
</html>