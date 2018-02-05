<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
	<title>其他参数设置</title>
	<script type="text/javascript" src="<%=basePath %>/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
	var defId=${bpmDefinition.defId };
	
	$(function(){
		handFlowVars();
		//状态改变
		handleStatusChange();
	});
	
	function handleStatusChange(){
		$("#status").change(function(){
			var v=$(this).val();
			if(v=="2" || v=="3"){
				$("#spanMessage").hide();
			}
			else{
				$("#spanMessage").show();
			}
			if(v=="4"){
				$("#testTag").show();
			}else{
				$("#testTag").hide();
			}
		});
	}
	
	function handFlowVars(){
		var objConditionCode=$("#taskNameRule");
		$("select[name='selFlowVar']").change(function(){		
			var val=$(this).val();
			var text=$(this).find("option:selected").text();
			if(val.length==0) return;
			if(text=="发起人(长整型)")
				text=text.replace("(长整型)","");			
			var inStr="{"+text+":"+val+"}";
			InsertText(inStr);
		});
	}
	
	function InsertText(val){
		// Get the editor instance that we want to interact with.
		var oEditor = CKEDITOR.instances.taskNameRule;
		// Check the active editing mode.
		if ( oEditor.mode == 'wysiwyg' ){
			oEditor.insertText( val );
		}else
			alert( '请把编辑器设置为编辑模式' );
	}
	
	function getCheckedValue(id){
		
		var checked=$(id).attr("checked");
		if(checked==undefined){
			return 0;
		}else if(checked){
			return 1;
		}else{
			return 0;
		}
	}
	function getMsgTypeList(id){
		var msgTypeList=[];
		$("input[name='"+id+"']").each(function(){
			var me = $(this),
				val = me.val(),
				state = me.attr("checked");
			if(state)
				msgTypeList.push(val);
		});
		return msgTypeList;
	}
	function saveParam(){
		var taskNameRule=$("#taskNameRule").val();
		var toFirstNode=getCheckedValue("#toFirstNode");
		var showFirstAssignee=getCheckedValue("#showFirstAssignee");
		var submitConfirm=getCheckedValue("#submitConfirm");
		var allowDivert=getCheckedValue("#allowDivert");
		var allowFinishedDivert=getCheckedValue("#allowFinishedDivert");
		var informStart = getMsgTypeList("informStart");
		var informType = getMsgTypeList("informType");
		var allowFinishedCc=getCheckedValue("#allowFinishedCc");
		var isPrintForm=getCheckedValue("#isPrintForm");
		var formDetailUrl=$('#formDetailUrl').val();
		var attachment=$('#attachment').val();
		var status=$('#status').val();
		var sameExecutorJump=getCheckedValue("#sameExecutorJump");
		var isUseOutForm=getCheckedValue("#isUseOutForm");
		var isUseOutForm=getCheckedValue("#isUseOutForm");
		var allowRefer=getCheckedValue("#allowRefer");
		var instanceAmount=$('#instanceAmount').val();
		var testStatusTag=$('#testStatusTag').val();
		
		var directstart=getCheckedValue("#directstart");
		var ccMessageType = $("input[name='ccMessageType']").val();
		 
		
		var params={defId:defId,taskNameRule:taskNameRule,toFirstNode:toFirstNode,
				showFirstAssignee:showFirstAssignee,
				submitConfirm:submitConfirm,allowDivert:allowDivert,
				allowFinishedDivert:allowFinishedDivert,informStart:informStart.join(','),
				informType:informType.join(','),allowFinishedCc:allowFinishedCc,
				isPrintForm:isPrintForm,attachment:attachment,
				status:status,sameExecutorJump:sameExecutorJump,
				isUseOutForm:isUseOutForm,allowRefer:allowRefer,instanceAmount:instanceAmount,
				directstart:directstart,ccMessageType:ccMessageType,testStatusTag:testStatusTag};
		$.ajax( {
			type : 'POST',
			url : __basePath+'/pages/third/bpm/bpmDefinition/saveParam.action',
			data: params,
			dataType: 'json',
			success : function(json) {
				if(json.statusCode == 200){
					alertMsg.correct(json.message);
				}else{
					alertMsg.error(json.message);
				}
			}
		});
	}
	
	function openCcUserList(){
		var url=__basePath +'/pages/third/bpm/bpmDefinition/copyUserList.action?defId=${bpmDefinition.defId}';
	 	var winArgs="height=450,width=750,status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
		url=url.getNewUrl();
	 	window.open(url,"",winArgs);
	 	$("#allowFinishedCc").attr("checked","checked");
	};
	
	//添加附件
	function addFile(){
		FlexUploadDialog({isSingle:true,callback:fileCallback});
	};
	
	function fileCallback(fileIds,fileNames,filePaths){
		if(fileIds==undefined || fileIds=="") return ;
		var url=__ctx+"/platform/system/sysFile/file_"+fileIds +".ht";
		$("#attachment").val(fileIds);
		if($("#file").length>0){
			$("#file").attr("href",url).text(fileNames);
		}else{
			var node='<a href="'+url+'" target="_blank" id="file">'+fileNames+' </a><a  class="link del" onclick="del(this)" style="cursor:pointer;"> 删除</a>';
			$("#attachment").after(node);
		}
	}
	function del(obj){
		$("#attachment").val("");
		$("#file").remove();
		$(obj).remove();
	}
	var referDef;
	function referDefinition(){
		var url=__basePath +'/pages/third/bpm/bpmDefinition/defReferSelector.action?defId=${bpmDefinition.defId}';
		referDef = $.ligerDialog.open({title:'流程引用',mask:true,isResize:true,height: 500,url:url,width:700});
	};
	
	function clickAllowRefer(obj){
		var $obj=$(obj);
		if($obj.attr("checked")){
			$("#spanInstanceAmount").show();	
		}
		else{
			$("#spanInstanceAmount").hide();
		}
		
	}
	
	function delRefer(refId){
		$.post("delReferDef.ht",{refId:refId},function(msg){
			var obj=new com.hotent.form.ResultMessage(msg);
			if(obj.isSuccess()){
				$.ligerDialog.success(obj.getMessage(),$lang.tip.msg);
				$('#ref_'+refId).remove();
			}else{
				$.ligerDialog.error(obj.getMessage(),$lang.tip.msg);
			}
		});
	}
	
	</script>
</head>
<body> 
            <div class="panelBar">
				<button type="button" class="listbutton" onclick="saveParam()">保存</button>
			</div>
			<div>
			<table class="Input_Table" cellpadding="0" cellspacing="0" border="0" width="100%" align="left" style="border-top:0px;">
				<tr>
					<td width="15%" class="Input_Table_Label">流程标题规则定义</td>
					<td >
						<textarea id="taskNameRule" cols="103" rows="3" name="taskNameRule" >${bpmDefinition.taskNameRule }</textarea>
					</td>	
				</tr>
				
				<tr>
					<td width="15%" class="Input_Table_Label">跳过第一个任务:</td>
					<td >
						<input title="流程启动后直接完成第一个节点的任务" id="toFirstNode" type="checkbox"  name="toFirstNode" value="0"  <c:if test="${bpmDefinition.toFirstNode==1 }">checked="checked"</c:if> />
					</td>	
				</tr>
				<c:if test="${!isStartMultipleNode}">
				<tr>
					<td width="15%" class="Input_Table_Label">直接启动流程:</td>
					<td >
						<input title="不使用表单直接启动流程，启动流程时不传入主键" id="directstart" type="checkbox" name="directstart" value="0"  <c:if test="${bpmDefinition.directstart==1 }">checked="checked"</c:if> />
					</td>	
				</tr>
				</c:if>
				<tr>
					<td width="15%" class="Input_Table_Label">流程启动选择执行人:</td>
					<td >
						<input title="如果勾选，那么流程启动时可以改变下一步的执行人，默认不可以。" id="showFirstAssignee" type="checkbox" name="showFirstAssignee" value="1"  <c:if test="${bpmDefinition.showFirstAssignee==1 }">checked="checked"</c:if> />
					</td>	
				</tr>
				<tr>
					<td width="20%" class="Input_Table_Label">状态:</td>
					<td >
						<select id="status" name="status">
							<option value="1" <c:if test="${bpmDefinition.status==1}">selected='selected'</c:if>>启用</option>
							<option value="2" <c:if test="${bpmDefinition.status==2}">selected='selected' </c:if>>禁用</option>
							<option value="3" <c:if test="${bpmDefinition.status==3}">selected='selected' </c:if>>禁用(实例)</option>
							<option value="4" <c:if test="${bpmDefinition.status==4}">selected='selected' </c:if>>测试</option>
						</select>
					</td>	
				</tr>
			</table>
		</div>
		<input type="hidden" id="defId" name="defId" value="${bpmDefinition.defId }">
</body>
</html>
