<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/form.jsp" %>
<title>任务流转</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<style type="text/css">
th{width:15%;}
</style>
<script type="text/javascript">
var taskId=${param.taskId};
var formData = window.dialogArguments.data;

function callBack(rtn) {
	if(!rtn) return;
	var cmpIds=$("#cmpIds").val();
	var transType=$("input[name='transType']:checked").val();
	var arrCmpId = new Array();
	arrCmpId = cmpIds.split(",");
	if(transType=='2' && arrCmpId.length<=1){
		$.ligerDialog.error("会签类型的人数必须大于1！");
		return;
	}
		
	var taskOpinion=$("#opinion").val();
	var action=$("input[name='action']:checked").val();
	var informType=$.getChkValue("informType");
	var params= {cmpIds:cmpIds,
				 opinion:taskOpinion,
				 transType:transType,
				 action:action,
				 informType:informType,
				 taskId:taskId,
				 formData:formData};
	var url="${ctx}/platform/bpm/task/toStartTransTo.ht";
	$.post(url,params,function(msg){
		var obj=new com.hotent.form.ResultMessage(msg);
		if(obj.isSuccess()){
			 $.ligerDialog.success("加签成功！",function(){
				 window.returnValue="ok";
	    		 window.close();
	    	 });
		}else{
			 $.ligerDialog.error("加签失败！"+obj.getMessage());
		}
	});
}


function save(){
	var rtn=$("#frmComm").form().valid();
	if(!rtn) return;
 	$.ligerDialog.confirm("确定发送流转意见？",callBack);
 }
 
function dlgCallBack(userIds, fullnames) {
 	if (userIds.length > 0) {
		var cmpIds=$("#cmpIds");
		var cmpNames=$("#cmpNames");
		cmpIds.val(userIds);
		cmpNames.val(fullnames);
	}
};

function add() {
	UserDialog({
		selectUserIds:$("#cmpIds").val(),
	    selectUserNames:$("#cmpNames").val(),
		callback : dlgCallBack,
		isSingle : false
	});
}

</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">任务流转</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#" onclick="save()"><span></span>提交</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link close" href="#" onclick="window.close();"><span></span>关闭</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="frmComm">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">

			<tr>
				<th nowrap="nowrap">添加用户</th>
				<td>
					<input type="hidden" id="cmpIds" /> 
					<textarea id="cmpNames"  cols="50" style="width:300px"  rows="2" class="textarea" readonly="readonly" validate="{required:true}"></textarea>
					<a class="link grant" onclick="add(this);"><span></span><span>选择人员</span></a>
				</td>
			</tr>
			<tr>
				<th>会签</th>
				<td>
					<input type="radio" name="transType"  class="isNotSign" value="1" checked="checked">非会签
					<input type="radio" name="transType" class="isSign" value="2">会签
				</td>
			</tr>
			<tr>
				<th>流转结束后</th>
				<td>
					<input type="radio" name="action" class="back" value="1" checked="checked">返回
					<input type="radio" name="action" class="submit" value="2">提交
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td>
					<textarea rows="5" cols="50" style="width:300px" id="opinion" name="opinion" validate="{required:true,maxLength:1000}" maxLength="1000"></textarea>
				</td>
			</tr>
			<tr>
				<th>提醒消息方式</th>
				<td>
					<input type="checkbox" name="informType" value="3" checked="checked">站内消息
					<input type="checkbox" name="informType" value="1">邮件
					<input type="checkbox" name="informType" value="2">手机短信
				</td>
			</tr>
			<tr>
				<th>流转操作说明</th>
				<td>
					<div class="content"  style=" background: none repeat scroll 0 0 #EFEFEF;">
						<ul class="help">
							<li>1、添加用户：选择流转人员，可多选(其中会签状态下必须选多人)。</li>
							<li>2、会签：选择非会签，则其中一个人审批完成流程就返回或提交；选择会签，则所选人员全部审批完成流程才返回或提交。</li>
							<li>3、流转结束后，返回：流转结束后，原来的任务将会回到您的待办事项中；提交：流转结束后，流转人不需要执行原来的任务，流程就往下执行。</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
</body>
</html>