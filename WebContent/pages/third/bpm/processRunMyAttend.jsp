<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我参与审批流程列表</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//任务追回
	function recover(runId){
		//检查当前任务的上一步是否为当前任务的执行人员，若不是，不允许追回
		var url="${ctx}/platform/bpm/processRun/recover.ht?runId="+ runId;
		$.post(url,function(data){
			var jsonResult=eval("(" +data +")");
			if(jsonResult.result==1){
				$.ligerDialog.success('任务成功的被追回!','提示信息',function(){
					window.location.reload();
				});
			}else{
				$.ligerDialog.error('任务已经完成或正在处理，不能进行追回处理!','操作失败');
			}
        });
	}
</script>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">我参与审批流程列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
					<form id="searchForm" method="post" action="myAttend.ht">
							<ul class="row">
								<li><span class="label">流程定义名称:</span><input type="text" name="Q_processName_SL"  class="inputText" value="${param['Q_processName_SL']}"/></li>
								<li><span class="label">流程实例标题:</span><input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/></li>
								<li><span class="label">状态:</span>
								<select name="Q_status_SN" value="${param['Q_status_SN']}">
									<option value="">所有</option>
									<option value="1" <c:if test="${param['Q_status_SN'] == 1}">selected</c:if>>正在运行</option>
									<option value="2" <c:if test="${param['Q_status_SN'] == 2}">selected</c:if>>结束</option>
								</select></li>
							</ul>
							<ul class="row">
								<li><span class="label">创建时间 从:</span> <input  name="Q_begincreatetime_DL"  class="inputText date" value="${param['Q_begincreatetime_DL']}"/>
								<span class="label">至: </span><input  name="Q_endcreatetime_DG" class="inputText date" value="${param['Q_endcreatetime_DG']}"/></li>
								
								<li><span class="label">结束时间 从:</span> <input  name="Q_beginendTime_DL"  class="inputText date" value="${param['Q_beginendTime_DL']}"/>
								<span class="label">至: </span><input  name="Q_endendTime_DG" class="inputText date" value="${param['Q_endendTime_DG']}"/></li>
							</ul>
					</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
			
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="processRunList" id="processRunItem" requestURI="myAttend.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="runId" value="${processRunItem.runId}">
					</display:column>
					<display:column property="processName" title="流程定义名称" sortable="true" sortName="processName" style="text-align:left"></display:column>
					<display:column property="subject" title="流程实例标题" sortable="true" sortName="subject" style="text-align:left"></display:column>
					<display:column property="creator" title="创建人" sortable="true" sortName="creator"></display:column>
					<display:column  title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column  title="结束时间" sortable="true" sortName="endTime">
						<fmt:formatDate value="${processRunItem.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="持续时间" sortable="true" sortName="duration">
						${f:getTime(processRunItem.duration)}
					</display:column>
					<display:column title="状态" sortable="true" sortName="status">
						<c:choose>
							<c:when test="${processRunItem.status==1}">
								<span class="green">正在运行</font>
							</c:when>
							<c:when test="${processRunItem.status==2}">
								<span class="red">结束 </span>
							</c:when>
							<c:when test="${processRunItem.status==3}">
								<span class="brown">手工结束 </span>
							</c:when>
						</c:choose>
					</display:column>
					<display:column title="管理" media="html" style="width:180px">
						<a  href="#" onclick="FlowDetailWindow({runId:${processRunItem.runId}});" class="link detail">明细</a>
						<c:if test="${processRunItem.recover==1}">
							<a href="#" onclick="recover(${processRunItem.runId})" class="link redo">追回</a>
						</c:if>
					</display:column>
				</display:table>
				<hotent:paging tableId="processRunItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


