<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<f:js pre="js/lang/view/platform/bpm" ></f:js>
	<title>流程转发</title>
	<f:link href="form.css" ></f:link>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript">
		 
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			$("#bpmTaskExeForm").ajaxForm(options);
			
			$("a.save").click(delegate);
		});
		
	
		
		function delegate(){
			var frm=$('#bpmTaskExeForm').form();
			if(!frm.valid()) return;
			$.ligerDialog.confirm($lang_bpm.forward.confirm,$lang.tip.msg,function(rtn){
				if(!rtn) return;
				$("#bpmTaskExeForm").submit();
			});
		}
			
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {//成功
				 $.ligerDialog.success(obj.getMessage(),$lang.tip.msg,function(){
					 window.returnValue=1;
					 window.close();
				 });
			} else {
				$.ligerDialog.error(obj.getMessage(),$lang.tip.msg);
			}
		}
	
		function chooseUser(){
			var aryExistUser=$("#assigneeId").val().split(",");

			var userIds=$("#assigneeId").val();
			var userNames=$("#assigneeName").val();
			
			
			var aryNewId=[];
			var aryNewName=[];
			UserDialog({callback:function(userId,names){
				if(userId=="") {
					clearUser();
					return;
				};
				var aryUserId=userId.split(",");
				var aryUserName=names.split(",");
				for(var i=0;i<aryUserId.length;i++){
					var id=aryUserId[i];
					var name=aryUserName[i];
					if(!isExist(aryExistUser,id)){
						aryNewId.push(id);
						aryNewName.push(name);
					}
				}
				if(aryNewId.length>0){
					if(userIds==""){
						userIds=aryNewId.join(",");
						userNames=aryNewName.join(",");
					}
					else{
						userIds+="," + aryNewId.join(",");
						userNames+="," + aryNewName.join(",");
					}
				}
				$("#assigneeId").val(userIds);
				$("#assigneeName").val(userNames);
			 }});
		}
		
		function isExist(aryExistUser ,userId){
			for(var i=0;i<aryExistUser.length;i++){
				if(aryExistUser[i]==userId){
					return true;
				}
			}
			return false;
		}
		
		function clearUser(){
			$("#assigneeId").val("");
			$("#assigneeName").val("");
		}

	</script>
</head>
<body style="overflow-x:hidden;">
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">
				流程转发
			</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>转发</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link close" href="#" onclick="window.close()" ><span></span>关闭</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="bpmTaskExeForm" method="post" action="finishDivert.ht">
			<input type="hidden" name="runId" value="${param.runId}">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th nowrap="nowrap">提醒消息方式： </th>
					<td>
						<label><input type="checkbox" name="informtype" value="3"  checked="checked"/>站内消息</label>
						<label><input type="checkbox" name="informtype" value="1"  checked="checked"/>邮件</label>
						<label><input type="checkbox" name="informtype" value="2" />短信</label>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">转发意见： </th>
					<td>
					<textarea   id="suggestion" name="suggestion" style="width:300px;"  ></textarea>	
					</td>
				</tr>
				<tr>
					<th width="120px">接收人：</th>
					<td>
						<input type="hidden" id="assigneeId" name="assigneeId" >
						<textarea   id="assigneeName" name="assigneeName" readonly="readonly" style="width:300px;" validate="{required:true}"  ></textarea>
						<br>
						<a class="link users" id="btnSelect" href="#" onclick="chooseUser();">选择</a>
						<a class="link reset" id="btnClear" onclick="clearUser()">清空</a>
						
					</td>
				</tr>
				
				
				
				
			</table>
		</form>
	</div>
</div>
</body>
</html>