<%@ page pageEncoding="UTF-8"%>
<html>
	<head>
		<title>流程任务[${task.name}]执行</title>
		<%@include file="/commons/include/form.jsp" %>
		<f:link href="Aqua/css/ligerui-all.css"></f:link>
		<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
		<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerWindow.js" ></script>
		<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js" ></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskJumpWindow.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskSignWindow.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskAddSignWindow.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskBackWindow.js"></script>
		<script type="text/javascript">
		//显示自由跳转对话框
		function showJumpFreeDlg()
		{
			var win=TaskJumpWindow({
				taskId:'${task.id}'
			});
			win.show();
		}
		
		function showSignWindow()
		{
			var win=new TaskSignWindow({
				title:'任务-${task.name}会签情况列表',
				postUrl:'${ctx}/platform/bpm/task/saveSign.ht?taskId=${task.id}',
				url:'${ctx}/platform/bpm/task/toSign.ht?taskId=${task.id}',
				height:480
			});
			win.show();
		}
		//补签
		function showAddSignWindow()
		{
			TaskAddSignWindow({taskId:'${task.id}'});
		}
		
		function showTaskUserDlg()
		{
			new HtWindow({
				title:'任务执行人员示意图',
				height:450,
				width:650,
				frameid:'taskUserFrm',
				url:'${ctx}/platform/bpm/processRun/processImage.ht?actInstanceId=${task.ProcessInstanceId}'
			}).show();
		}
		
		function completeTask()
		{
			$('#taskForm').submit();
		}
		
		//弹出回退窗口
		function showBackWindow()
		{
			new TaskBackWindow({taskId:'${task.id}'}).show();
		}
		
		$(function(){
			$("#taskLayout").ligerLayout({rightWidth:200,height: '100%',bottomHeight:200,allowBottomResize:true});
			<c:if test="${isSignTask==true}">
			$("#taskSign").load('${ctx}/platform/bpm/task/toSign.ht?taskId=${task.id}');
			</c:if>
			$("#taskOpinionDiv").load('${ctx}/platform/bpm/taskOpinion/list.ht?actInstId=${task.processInstanceId}'.getNewUrl());
		});
		
		
	</script>
		
	</head>
	<body>
		
		<div id="taskLayout">
            <div position="center">
	            <div class="l-layout-header">任务审批处理--<b>${task.name}</b>--<i>[${bpmDefinition.subject}-V${bpmDefinition.versionNo}]</i></div>
	            
	            <div class="panel">
					<div class="panel-top">
						<div class="panel-toolbar">
							<div class="toolBar">
								<div class="group"><a class="link search" onclick="completeTask()"><span></span>完成任务</a></div>
								<c:if test="${isAllowBack==true}">
								<div class="group"><a class="link search" onclick="showBackWindow()"><span></span>驳回</a></div>
								</c:if>
								<div class="group"><a class="link search"><span></span>转交代办</a></div>
								<div class="group"><a class="link search " onclick="showJumpFreeDlg()"><span></span>自由跳转</a></div>

								<c:if test="${isSignTask==true}">
									<div class="group"><a class="bar-button" onclick="showAddSignWindow()"><span></span>补签</a></div>
								</c:if>
								
								<div class="group"><a class="link back"  href="list.ht"><span></span>返回</a></div>
								<div class="l-bar-separator"></div>
								<div class="group"><a class="link print"><span></span>打印</a></div>
								<div class="group">
									通知方式：<input type="checkbox" value="1"> 手机短信
								</div>
								<div class="group">
									<input type="checkbox" value="1"> 邮件
								</div>
								<div class="l-bar-separator"></div>
								<div class="group"><a class="link search" onclick="showTaskUserDlg()"><span></span>流程执行示意图</a></div>
							</div>	
						</div>
					</div>
					<div class="panel-body">
						<form id="taskForm" action="${ctx}/platform/bpm/task/complete.ht" method="post" >
							<input type="hidden" name="taskId" value="${task.id}"/> 
							<c:choose>
								<c:when test="${isSignTask==true}">
									<div>
										<div class="tbar-title">
											<span class="tbar-label">会签投票</span>
											<input type="hidden" name="isSignTask" value="true"/>
										</div>
										<div id="taskSign"></div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="panel-detail">
										<table style="width:100%" class="table-detail">
											<tr>
												<th>审批意见</th>
												<td>
													<input type="radio" name="voteAgree" value="1" checked="checked">&nbsp;同意&nbsp;
													<input type="radio" name="voteAgree" value="2">&nbsp;反对&nbsp;
													<c:if test="${isAllowBack==true}">
													<input type="radio" name="voteAgree" value="3">&nbsp;驳回&nbsp;
													</c:if>
												</td>
											</tr>
											<tr>
												<th>意见</th>
												<td>
													<textarea rows="5" cols="78" id="voteContent" name="voteContent" maxlength="512"></textarea>
												</td>
											</tr>
										</table>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="panel-detail">
								<table style="width:100%" class="table-detail">
									<c:if test="${not empty businessUrl}">
									<tr>
										<td colspan="2">
											<div id="businessDetail">
												<iframe src="${ctx}${businessUrl}" width="100%" height="400" style="border: 0px"></iframe>
											</div>
										</td>
									</tr>
									</c:if>
									<tr>
										<td></td>
										<td>
											<input type="submit" value="完成任务" />
										</td>
									</tr>
								</table>
							</div>
						</form>
				</div>
					
	            </div>
	            
            </div> <!--  end of center div -->
            <div position="right" title="任务评论">
            	<div class="panel-top">
	            	<div class="panel-toolbar">
		            	<div class="toolBar">
			            	<div class="group"><a class="link search"><span></span>添加评论</a></div>
							<div class="group"><a class="link search"><span></span>关联内容</a></div>
						</div>
					</div>
				</div>
            </div>
            <div position="bottom">
            	<div class="l-layout-header">审批历史</div>
            	<div id="taskOpinionDiv" style="overflow:auto;"></div>
            </div>
    	</div> 
		
	</body>
</html>