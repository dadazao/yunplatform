<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 系统角色表</title>
	<%@include file="/commons/include/get.jsp" %>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js" ></script>
     <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskChangePathWindow.js" ></script>
    <script type="text/javascript">
            var taskId="${taskEntity.id}";
        	$(function(){
        		var menuItem={ width: 120, items:[
          					            { id:'changePath',taskId:taskId,text: '更改执行路径', click: itemclick},
          					            { id:'completeTask', taskId:taskId,text: '完成任务', click: itemclick},
          					            { id:'approvalDetail',taskId:taskId, text: '审批明细', click: itemclick},
          					            { id:'businessForm',taskId:taskId, text: '业务表单', click: itemclick}
          					           ]
          					        };
        		//加上菜单
        		$("div[name='taskMenu']").ligerMenuBar({ items: [
                				{ text: '<a href="#">更多任务操作</a>', menu: menuItem}]
        		});
        		
        		$("div[name='taskMenu']").removeClass('l-menubar');
        	});
        	function itemclick(item){
        	    if(item.id=='changePath'){//更改执行路径
        	    	TaskChangePathWindow({taskId:taskId,callback:function(rtn){
        	    		if(rtn==1){
        	    			if(window.opener){
        						try{
        							window.opener.location.href=__ctx + "/platform/bpm/task/list.ht";
        						}
        						catch(e){}
        					}	
        					window.close();
        	    		}
        	    	}});
        	    }else if(item.id=='completeTask'){//结束任务
        	    	$.ligerDialog.confirm('确定结束该任务吗?','提示信息',function(rtn){
        	    		if(!rtn) return;
        	    		url=__ctx + "/platform/bpm/task/end.ht?taskId="+ item.taskId;
           				$.post(url,function(responseText){
           					var obj=new com.hotent.form.ResultMessage(responseText);
           					if(obj.isSuccess()){//成功
           						if(window.opener){
            						try{
            							window.opener.location.href=__ctx + "/platform/bpm/task/list.ht";
            						}
            						catch(e){}
            					}	
           						$.ligerDialog.success(obj.getMessage(),'提示信息',function(){
           							window.close();
           						});
           						
           					}
           					else{
           						$.ligerDialog.err('出错信息',"更改执行路径失败",obj.getMessage());
           					}
           				});
        	    	});	
        	    }else if(item.id=='approvalDetail'){
        	    	var winArgs="dialogWidth=780px;dialogHeight=600px;help=0;status=0;scroll=1;center=1";
        	    	var url=__ctx + '/platform/bpm/taskOpinion/list.ht?taskId='+item.taskId;
        	    	url=url.getNewUrl();
        			window.showModalDialog(url,"",winArgs);
        	    }else if(item.id=='taskComment'){// 任务评论
        	    	TaskCommentWindow({taskId:item.taskId});
        	    }
        	    else if(item.id=="businessForm"){
        	    	var url=__ctx+ '/platform/bpm/task/getForm.ht?taskId='+item.taskId;
        	    	jQuery.openFullWindow(url);
        	    }
        	}
           
            
     </script> 
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务明细--${processRun.subject}--${taskEntity.name}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link close" href="javascript:window.close();"><span></span>关闭</a></div>
				</div>
			</div>
		</div>
		<f:tab curTab="detail" tabName="task"/>
		<div class="panel-body">
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><div name="taskMenu"></div></div>
			</div>
		</div>
		<table class="table-detail" style="width:100%">
			<tr>
				<th nowrap="nowrap" width="100">流程事项名称</th>
				<td width="*">
					${processRun.subject}
				</td>
			</tr>
			<tr>
				<th>任务ID</th>
				<td>${taskEntity.id}</td>
			</tr>
			<tr>
				<th nowrap="nowrap">
					流程运行(RUNID)
				</th>
				<td>${processRun.runId}</td>
			</tr>
			<tr>
				<th nowrap="nowrap">任务名</th>
				<td>${taskEntity.name}</td>
			</tr>
			<tr>
				<th>任务描述</th>
				<td>
					<c:choose>
						<c:when test="${not empty taskEntity.description}">
							${taskEntity.description}
						</c:when>
						<c:otherwise>${taskEntity.name}</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap">所属人</th>
				<td>
				 <span><f:userName userId="${taskEntity.owner}"/></span>&nbsp; <c:if test="${not empty param['manage']}"><a href='#' class='button' onclick="setTaskOwner(this,${taskEntity.id})"><span>更改..</span></c:if> </a>
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap">执行人</th>
				<td>
					<span><f:userName userId="${taskEntity.assignee}"/></span>&nbsp;<c:if test="${not empty param['manage']}"><a href='#' class='button' onclick="setTaskAssignee(this,${taskEntity.id})"><span>更改..</span></c:if></a>
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap">候选执行人</th>
				<td>
				
					<c:choose>
						<c:when test="${fn:length(candidateUsers)==0}">
							<font color='red'>暂无</font>
						</c:when>
						<c:otherwise>
							<c:forEach items="${candidateUsers}" var="executor">
								
								<c:if test="${not empty executor }">
								<c:set var="type" >
									<c:choose>
										<c:when test="${executor.type == 'org' }">(组织)</c:when>
										<c:when test="${executor.type == 'role' }">(角色)</c:when>
										<c:when test="${executor.type == 'pos' }">(岗位)</c:when>
										<c:otherwise>(用户)</c:otherwise>
									</c:choose>
								</c:set>
								<img src='${ctx}/styles/default/images/bpm/user-16.png'>${executor.executor}${type }
								</c:if>
							</c:forEach>		
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>当前活动任务</th>
				<td>
					<c:forEach items="${curTaskList}" var="curTask" varStatus="i"><c:if test="${i.count>1}">,</c:if>${curTask.name}(<f:userName userId="${curTask.assignee}"/>)</c:forEach>
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap">创建时间</th>
				<td>
					<fmt:formatDate value="${taskEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap">到期时间</th>
				<td>
					<fmt:formatDate value="${taskEntity.dueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</table>
		<br/>
		<table class="table-detail" style="width:100%">
			<tr>
				<th nowrap="nowrap" width="100">流程定义名称</th>
				<td>${processDefinition.subject}</td>
			</tr>
			<tr>
				<th>版本</th>
				<td>
					${processDefinition.versionNo}
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap">流程定义描述</th>
				<td>
					${processDefinition.descp}
				</td>
			</tr>
			
		</table>
	</div>
</div>
</body>
</html>
