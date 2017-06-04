<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/get.jsp"%>
<title>我的请求</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js"></script>
<script type="text/javascript">

	function showDetail(obj){
		var url = $(obj).attr("action");
		jQuery.openFullWindow(url);
	};
	
	//重新提交
	function executeTask(procInstId){
		 var url= "${ctx}/platform/bpm/task/toStart.ht?instanceId="+procInstId;
		 jQuery.openFullWindow(url);
	};
	
	//追回
	function recover(runId){
		FlowUtil.recover({runId:runId,backToStart:0,callback:function(){
			
		}});
	}
	
</script>

</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">我的请求</span>
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
				<form id="searchForm" method="post" action="myRequestList.ht">
						<ul class="row">
						<input type="hidden" name="nodePath" value="${param['nodePath']}" title="流程分类节点路径"></input>
						<li>
							<span class="label">请求标题:</span>
							<input type="text" name="Q_subject_SUPL" size="18" class="inputText"  value="${param['Q_subject_SUPL']}"/>
						</li>
						<li>
							<span class="label">流程名称:</span>
							<input type="text" name="Q_processName_SUPL" size="18" class="inputText"  value="${param['Q_processName_SUPL']}" />
							<input type="hidden"   id="orgId" name="Q_orgId_L" value="${param['Q_orgId_L']}" />
						</li>
						<li>
							<span class="label">状态:</span>
							<select name="Q_status_SN">
								<option value="">所有</option>
								<option value="1" <c:if test="${param['Q_status_SN'] == '1'}">selected</c:if>>正在运行</option>
								<option value="5" <c:if test="${param['Q_status_SN'] == '5'}">selected</c:if>>撤销</option>
								<option value="6" <c:if test="${param['Q_status_SN'] == '6'}">selected</c:if>>驳回</option>
								<option value="7" <c:if test="${param['Q_status_SN'] == '7'}">selected</c:if>>追回</option>
							</select>
						</li>
						<div class="row_date">
						<li>
							<span class="label">创建时间&nbsp;从:</span>
							<input name="Q_begincreatetime_DL" id="Q_begincreatetime_DL" size="18" class="inputText datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
						</li>
						<li>
							<span class="label">至: </span>
							<input name="Q_endcreatetime_DG" id="Q_endcreatetime_DG" size="18" class="inputText datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"  />
						</li>
						</div>
						
						</ul>
					</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
			<display:table name="processRunList" id="processRunItem" requestURI="myRequestList.ht" sort="external" cellpadding="1"
				cellspacing="1" class="table-grid">
				<display:column titleKey="序号" media="html" style="width:20px;">${processRunItem_rowNum}</display:column>
				<display:column  titleKey="请求标题" sortable="true" sortName="subject" style="text-align:left">
						<c:choose>
							<c:when test="${!processRunItem.allowBackToStart and (processRunItem.status==4 or processRunItem.status==5)}">
								<a href="#${processRunItem.actInstId}" onclick="javascript:executeTask('${processRunItem.actInstId}')" title='${processRunItem.subject}'>${f:subString(processRunItem.subject)}</a>
							</c:when>
							<c:otherwise>
								<a name="processDetail" onclick="showDetail(this)" href="#${processRunItem.runId}"  action="info.ht?prePage=myRequest&link=1&runId=${processRunItem.runId}" title='${processRunItem.subject}'>${f:subString(processRunItem.subject)}</a>								
							</c:otherwise>
						</c:choose>
				</display:column>
				<display:column property="processName" titleKey="流程名称" sortable="true" sortName="processName" style="text-align:left"></display:column>
				<display:column titleKey="创建时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${processRunItem.createtime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column titleKey="持续时间" sortable="true" sortName="duration">
								${f:getDurationTime(processRunItem.createtime)}
				</display:column>
				<display:column titleKey="类型" >
						<c:out value="${processRunItem.typeName}"></c:out>
				</display:column>		
				<display:column titleKey="状态" sortable="true" sortName="status" style="width:50px;" >
						<f:processStatus status="${processRunItem.status}"></f:processStatus>
				</display:column>
				<display:column title="管理" media="html" style="width:50px;text-align:center" class="rowOps">			
					<c:if test="${processRunItem.status!=2}">
									&nbsp;<a href="javascript:;" onclick="recover(${processRunItem.runId})" class="link back">追回</a>
					</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="processRunItem"></hotent:paging>
		</div>
	</div>
</body>
</html>


