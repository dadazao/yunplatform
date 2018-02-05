<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>我启动的流程列表</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ProcessUrgeDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	function urge(id){
		ProcessUrgeDialog({
			actInstId : id
		});
	};
	
	function printForm(runId){
		var url="${ctx}/platform/bpm/processRun/printForm.ht?runId="+runId;
		jQuery.openFullWindow(url);
	}
	
	function recover(runId){
		FlowUtil.recover({runId:runId,backToStart:1,callback:function(){
			
		}});
	}
</script>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">我启动的流程列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link search" id="btnSearch"><span></span>查询</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link del" action="del.ht"><span></span>删除</a>
					</div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="myStart.ht">
					<ul class="row">
							<li> <span class="label">流程定义名称:</span><input type="text" name="Q_processName_SL" class="inputText" value="${param['Q_processName_SL']}"/></li>
							<li> <span class="label">流程实例标题:</span><input type="text" name="Q_subject_SL" class="inputText" value="${param['Q_subject_SL']}"/> </li>
							 <li><span class="label">创建时间 从:</span> <input name="Q_begincreatetime_DL" class="inputText date" value="${param['Q_begincreatetime_DL']}"/> 
							 <span class="label">至: </span><input name="Q_endcreatetime_DG" class="inputText date" value="${param['Q_endcreatetime_DG']}"/> </li>
							 <li><span class="label">状态:</span> <select name="Q_status_SN" value="${param['Q_status_SN']}">
							<option value="">所有</option>
							<option value="1" <c:if test="${param['Q_status_SN'] == 1}">selected</c:if>>正在运行</option>
							<option value="2" <c:if test="${param['Q_status_SN'] == 2}">selected</c:if>>结束</option>
						</select></li>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel-body">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
			<display:table name="processRunList" id="processRunItem"
				requestURI="myStart.ht" sort="external" cellpadding="1"
				cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox" class="pk" name="runId"
						value="${processRunItem.runId}">
				</display:column>
				<display:column property="processName" title="流程定义名称"
					sortable="true" sortName="processName" style="text-align:left"></display:column>
				<display:column property="subject" title="流程实例标题" sortable="true"
					sortName="subject" style="text-align:left"></display:column>
				<display:column property="creator" title="创建人" sortable="true"
					sortName="creator" style="text-align:left"></display:column>
				<display:column title="创建时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${processRunItem.createtime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column title="结束时间" sortable="true" sortName="endTime">
					<fmt:formatDate value="${processRunItem.endTime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column title="持续时间" sortable="true" sortName="duration">
								${f:getTime(processRunItem.duration)}
							</display:column>
				<display:column title="状态" sortable="true" sortName="status">
					<c:choose>
						<c:when test="${processRunItem.status==1}">
							<span class="green">正在运行</span>
						</c:when>
						<c:when test="${processRunItem.status==2}">
							<span class="red">结束 </span>
						</c:when>
						<c:when test="${processRunItem.status==3}">
								<span class="brown">手工结束 </span>
						</c:when>
					</c:choose>
				</display:column>
				<display:column title="管理" media="html" style="width:110px;text-align:left">
					<a href="#" onclick="FlowDetailWindow({runId:${processRunItem.runId}});" class="link detail">明细</a>
					<c:if test="${processRunItem.status==1}">
						&nbsp;<a href="javascript:;" onclick="urge(${processRunItem.actInstId})" class="link urge">催办</a>
					</c:if>
					
					<c:if test="${processRunItem.status==2&&processRunItem.isPrintForm==1}">
						&nbsp;<a href="javascript:;" onclick="printForm(${processRunItem.runId})" class="link print">打印表单</a>
					</c:if>
					<c:if test="${processRunItem.status!=2}">
						&nbsp;<a href="javascript:;" onclick="recover(${processRunItem.runId})" class="link back">追回</a>
					</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="processRunItem" />

		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


