<%
	//任务设置执行人
%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<title>更改任务执行的路径</title>
		<%@include file="/commons/include/get.jsp" %>
		<f:link href="tree/zTreeStyle.css"></f:link>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
		<script type="text/javascript">
			//更改任务执行路径
			function saveTaskAssignee(){
				var userId=$("#userId:checked").val();
				if(!userId){
					$.ligerDialog.confirm('请选择执行人!');
					return;
				}
				var voteContent=$("#voteContent").val();
				if(voteContent==''){
					$.ligerDialog.confirm("请填写更改任务执行人原因!",'提示信息');
					return;
				}
				var params=$('#taskForm').serialize();
				$.post('${ctx}/platform/bpm/task/setAssignee.ht',params,function(data){
					var obj=new com.hotent.form.ResultMessage(data);
					if(obj.isSuccess()){
						$.ligerDialog.success(obj.getMessage(),"操作成功");
						window.returnValue=1;	
						window.close();
					}else{
						$.ligerDialog.err('出错信息','失败',obj.getMessage());
					}
				});
			}
			//为目标节点选择执行的人员列表
			function selectExeUsers(nodeId){
				UserDialog({isSingle:true,callback:function(uIds,uNames){
					if(uIds.isEmpty()) return;
					
					var html="<input type='checkbox' id='userId' name='userId' checked='checked' value='"+uIds+"'/>&nbsp;"+uNames;
					$("#jumpUserDiv").html(html);
				}});
			}
		</script>
	</head>
	<body>
	<div class="panel">
		<div class="panel-top">
		   <div class="tbar-title">
		    	<span class="tbar-label">任务设置执行人</span>
		   </div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="btnSearch" onclick="saveTaskAssignee()"><span></span>保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link close" onclick="javasrcipt:window.close()"><span></span>关闭</a></div>
			    </div>
			</div>
		</div>
		<div class="panel-detail">
			<form id="taskForm">
					<table class="table-detail">
						<tr>
							<th nowrap="nowrap">当前任务</th>
							<td>
								<input type="hidden" id="taskId"  name="taskId" value="${taskEntity.id}"/>
								${taskEntity.name}
							</td>
						</tr>
						<tr>
							<th nowrap="nowrap">
								执行人
							</th>
							<td>
								<div id="jumpUserDiv"></div>
								<a href="#" id="jumpUserLink" class="link get" onclick="selectExeUsers('${nodeUserMap.nodeId}')">&nbsp;&nbsp;</a>
							</td>
						</tr>
						<tr>
							<th>更改原因</th>
							<td>
								<textarea rows="5" cols="60" id="voteContent" name="voteContent" maxlength="512"></textarea>
							</td>
						</tr>
						<tr>
							<th nowrap="nowrap">通知</th>
							<td>
								<input name="informType" type="checkbox" value="3" checked="checked">内部消息 &nbsp;<input name="informType" type="checkbox" value="1" checked="checked">手机短信 &nbsp;<input name="informType" type="checkbox" value="2" checked="checked"> 邮件
							</td>
						</tr>
					</table>
					<input type="hidden" id="voteAgree" name="voteAgree" value="8"/> 
				</form>
			</div>
		</div>
	</body>
</html>