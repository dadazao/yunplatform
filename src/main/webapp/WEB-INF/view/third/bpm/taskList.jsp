<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<script type="text/javascript">
	//为某个任务分配人员
	ns.bpm.assignTask = function (){
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要分配的任务!");
			return;
		}
		ns.common.userDialog({callback:
			function(userId,fullname){
				if(!userId) return;
				var param = $("#tableForm").serialize();
				var url="<%=basePath %>/pages/third/bpm/task/assign.action?userId=" + userId;
				$.ajax( {
					type : 'POST',
					async : false,
					data: param,
					dataType:'json',
					url : url,
					success : function(data) {
						DWZ.ajaxDone(data);
					}
				})
			}
		});
	}
	
	function setTaskAssignee(link,taskId){
		var url="${ctx}/platform/bpm/task/setAssignee.ht";
		var callback=function(userId,fullname){
			$(link).siblings("span").html('<img src="${ctx}/styles/default/images/bpm/user-16.png">' + fullname);	
		};
		setTaskExecutor(taskId,url,callback);
	}
	
	function setTaskOwner(link,taskId){
		var url="${ctx}/platform/bpm/task/setOwner.ht";
		var callback=function(userId,fullname){
			$(link).siblings("span").html('<img src="${ctx}/styles/default/images/bpm/user-16.png">' + fullname);	
		};
		setTaskExecutor(taskId,url,callback);
	}
	
	//设置任务的执行人
	function setTaskExecutor(taskId,url,callback){
		//显示用户选择器
		UserDialog({
			isSingle:true,
			callback:function(userId,fullname){
				if(userId=='' || userId==null || userId==undefined) return;
				var params= { taskId:taskId,userId:userId};
				$.post(url,params,function(responseText){
					var obj=new com.hotent.form.ResultMessage(responseText);
					if(obj.isSuccess()){
						$.ligerMsg.correct('<p><font color="green">操作成功!</font></p>'); 
				    	 if(!callback){
				    	 	document.location.reload();
				    	 }else{
				    		 callback.call(this,userId,fullname);
				    	 }
					}
					else{
						 $.ligerMsg.error('操作失败!'); 
					}
				});
			}
		});
	}
	//执行任务
	function executeTask(taskId){
		 var url="${ctx}/platform/bpm/task/toStart.ht?taskId="+taskId;
		 jQuery.openFullWindow(url);
	}
	
	ns.bpm.endProcess = function(taskId){
		var urlString = "<%=basePath %>/pages/third/bpm/task/endProcess.action?taskId=" + taskId;
		alertMsg.confirm("确定要结束吗?", {okCall:function(){ajaxTodo(urlString,refreshList);}});
	}
	
	function init(){
		var layout=$("#taskLayout").ligerLayout({rightWidth:300,height: '100%',isRightCollapse:true});
		$("tr.odd,tr.even").each(function(){
			$(this).bind("mousedown",function(event){
				if(event.target.tagName!="TD")  return;
				var strFilter='input[type="checkbox"][class="pk"]';
				var obj=$(this).find(strFilter);
				if(obj.length==1){
					var taskId=obj.val();
					layout.setRightCollapse(false);
					$("#taskDetailPanel").html("<div>正在加载...</div>");
					//在任务表单明细面版中加载任务详细
					$("#taskDetailPanel").load('${ctx}/platform/bpm/task/miniDetail.ht?manage=true&taskId='+taskId,null);
				}
			});    
		});
	}
	
	$(function(){
		//init();
		//endProcess();
	});
	
	function endProcess(){
		$.confirm("a.link.stop",'确认结束该流程吗？');
	}
	
</script>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/third/bpm/task/list.action">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="exportXml" name="exportXml" class="listbutton" style="width:70px;" onclick="ns.bpm.assignTask();" >分配任务</button>
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/third/bpm/task/list.action" method="post">
				<table class="searchContent">
					<tr>
						<td>
							<span class="label">流程定义名称&nbsp;</span><input type="text" name="Q_processName_SL"  class="inputText" value="${param['Q_processName_SL']}" />&nbsp;&nbsp;
							<span class="label">事项名称&nbsp;</span><input type="text" name="Q_subject_SL"  class="inputText"  value="${param['Q_subject_SL']}"/>&nbsp;&nbsp;
							<span class="label">任务名称&nbsp;</span><input type="text" name="Q_name_SL"  class="inputText"  value="${param['Q_name_SL']}"/>&nbsp;&nbsp;
						</td>
						<td>
							<button type="button" disabled class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'selectedIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center" width="25%">事项名称</th>
						<th align="center">任务名称</th>
						<th align="center">所属人</th>
						<th align="center">执行人</th>
						<th align="center">状态</th>
						<th align="center">创建时间</th>
						<th align="center" width="15%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="taskItem" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${taskItem.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${taskItem.subject}</td>
		                    	<td align="center">${taskItem.name}</td>
		                    	<td align="center">${taskItem.owner}</td>
		                    	<td align="center">${taskItem.assignee}</td>
		                    	<td align="center">
			                    	<c:choose>
										<c:when test="${empty taskItem.delegationState}">待执行</c:when>
										<c:otherwise>${taskItem.delegationState}</c:otherwise>
									</c:choose>
								</td>
		                    	<td align="center">
		                    		<fmt:formatDate value="${taskItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
		                    	</td>
		                    	<td align="center">
<!-- 		                    		<a style="cursor: pointer;color:blue;" onclick="ns.bpm.executeTask(${taskItem.id},'${taskItem.name}')"  title="主办">办理</a>&nbsp; -->
									<a style="cursor: pointer;color:blue;" onclick="ns.bpm.endProcess(${taskItem.id})"  title="结束">结束</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</html>


