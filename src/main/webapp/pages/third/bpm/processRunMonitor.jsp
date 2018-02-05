<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/get.jsp"%>
<title>流程监控展示</title>
<script type="text/javascript" src="${ctx}/hotent/sf/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/hotent/sf/platform/bpm/SelectUtil.js" ></script>
<script type="text/javascript">
	function showDetail(obj){
		var url = $(obj).attr("action");
		jQuery.openFullWindow(url);
	};


	function executeTask(actInstId){
		 var url="${ctx}/platform/bpm/task/doNext.ht?instanceId="+actInstId;
		var rtn = jQuery.openFullWindow(url);
		
	}
</script>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程监控展示</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link search" id="btnSearch"><span></span>查询</a>
						<div class="l-bar-separator"></div>
						<div class="group"><a href="#" class="link reset" onclick="$.clearQueryForm();"><span></span>重置</a></div>
					</div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="monitor.ht">
						<div class="row">
							<span class="label">请求标题:</span>
							<input type="text" name="Q_subject_SUPL" size="18" class="inputText"  value="${param['Q_subject_SUPL']}"/>
							<span class="label">流程名称:</span>
							<input type="text" name="Q_processName_SUPL" size="18" class="inputText"  value="${param['Q_processName_SUPL']}" />
							<span class="label">创建时间  从:</span>
							<input name="Q_begincreatetime_DL" id="Q_begincreatetime_DL" size="18" class="inputText datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
							<span class="label">至: </span>
							<input name="Q_endcreatetime_DG" id="Q_endcreatetime_DG" size="18" class="inputText datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"  />
							<br/>
							<span class="label">当前状态:</span>
							<select name="Q_status_SN">
								<option value="">全部</option>
								<option value="1" <c:if test="${param['Q_status_SN'] == 1}">selected</c:if>>审批中</option>
								
								<option value="5" <c:if test="${param['Q_status_SN'] == 5}">selected</c:if>>已驳回</option>
								<option value="6" <c:if test="${param['Q_status_SN'] == 6}">selected</c:if>>已撤销</option>
								<option value="3" <c:if test="${param['Q_status_SN'] == 3}">selected</c:if>>已终止</option>
								<option value="2" <c:if test="${param['Q_status_SN'] == 2}">selected</c:if>>已归档</option>
							</select>
							<span class="label">创建人:</span>
								<input type="hidden" id="Q_creatorId_L" name="Q_creatorId_L" value="${param['Q_creatorId_SL']}"/>
								<input type="text" id="creator"  class="inputText"   />
								<input type="button" value="..."  onclick="selectUser();" />
						</div>
					</form>
			</div>
		</div>
		<div class="panel-body">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
			<display:table name="processRunList" id="processRunItem" requestURI="monitor.ht" sort="external" cellpadding="1"
				cellspacing="1" class="table-grid">
				<display:column title="序号" media="html" style="width:20px;">${processRunItem_rowNum}</display:column>
				<display:column  title="请求标题" sortable="true" sortName="subject" style="text-align:left">
					<c:choose>
						<c:when test="${processRunItem.grade eq 2 }">
						   <a name="processDetail" onclick="showDetail(this)" href="#"  action="info.ht?prePage=myRequest&link=1&runId=${processRunItem.runId}" title="${processRunItem.subject}">${f:subString(processRunItem.subject)}</a>
						</c:when>
						<c:when test="${processRunItem.grade eq 3 and processRunItem.status eq 1}">
						   <a name="subject"  href="javascript:executeTask(${processRunItem.actInstId})" title="${processRunItem.subject}" >${f:subString(processRunItem.subject)}</a>
						</c:when>
						<c:when test="${processRunItem.grade eq 3 and processRunItem.status ne 1}">
						   <a name="processDetail" onclick="showDetail(this)" href="#"  action="info.ht?prePage=myRequest&link=1&runId=${processRunItem.runId}" title="${processRunItem.subject}">${f:subString(processRunItem.subject)}</a>
						</c:when>
						<c:otherwise>
							<div title="${processRunItem.subject}">${f:subString(processRunItem.subject)}</div>
						</c:otherwise>
					</c:choose>
					
				</display:column>
				<display:column property="processName" title="流程名称" sortable="true" sortName="processName" style="text-align:left"></display:column>
				<display:column title="创建时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${processRunItem.createtime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column title="持续时间" sortable="true" sortName="duration">
								${f:getDurationTime(processRunItem.createtime)}
				</display:column>
				<display:column title="类型" >
						<c:out value="${processRunItem.typeName}"></c:out>
				</display:column>		
				<display:column title="归属组织" >
						<c:out value="${processRunItem.orgName}"></c:out>
				</display:column>
				<display:column title="状态" sortable="true" sortName="status" style="width:50px;" >
						<f:processStatus status="${processRunItem.status}"></f:processStatus>
				</display:column>
				
			</display:table>
			<hotent:paging tableId="processRunItem" />

		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


