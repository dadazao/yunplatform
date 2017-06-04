<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp" %>
<title>已办事宜</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/tabOperator.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRightDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<script type="text/javascript">
	function showDetail(obj){
		var url = $(obj).attr("action");
		jQuery.openFullWindow(url);
	};
	
</script>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">已办事宜</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a href="#" class="link reset" onclick="$.clearQueryForm()"><span></span>重置</a></div>
				</div>	
			</div>
			<div class="panel-search">
					<form id="searchForm" method="post" action="alreadyMattersList.ht">
							<ul class="row">
							<input type="hidden" name="nodePath" value="${param['nodePath']}" title="流程分类节点路径"></input>
							<li>
								<span class="label">请求标题</span><input  type="text" name="Q_subject_SUPL"  class="inputText" value="${param['Q_subject_SUPL']}" />						
							</li>
							<li>
								<span class="label">流程名称:</span><input  type="text" name="Q_processName_SUPL"  class="inputText" value="${param['Q_processName_SUPL']}"  />
							
							</li>
							<li>	
								<span class="label">创 建 人:</span>
								<input type="hidden" id="creatorId" name="Q_creatorId_L"  value="${param['Q_creatorId_L']}" />
								<input type="text"   id="creator" name="Q_creator_SL" class="inputText"  value="${param['Q_creator_SL']}" onclick="selectUser('creatorId','creator');" readonly="readonly"  />
								<input type="button" value="..." onclick="selectUser('creatorId','creator');"/>
							</li>
							<li>
								<span class="label">状态:</span><select name="Q_status_SN">
									<option value="">所有</option>
									<option value="1" <c:if test="${param['Q_status_SN']==1}">selected</c:if>>审批中</option>
									<option value="5" <c:if test="${param['Q_status_SN']==5}">selected</c:if>>已撤销</option>
									<option value="6" <c:if test="${param['Q_status_SN']==6}">selected</c:if>>已驳回</option>
									<option value="11" <c:if test="${param['Q_status_SN']==11}">selected</c:if>>重启任务</option>				
								</select>
							</li>
							<div class="row_date">
							<li>
								<span class="label">创建时间&nbsp;从:</span> <input  name="Q_begincreatetime_DL"  class="inputText datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
							</li>
							<li>
								<span class="label">至: </span><input  name="Q_endcreatetime_DG" class="inputText datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"/>
							</li>
							</div>
							
							</ul>
					</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
				<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="processRunList" id="processRunItem" requestURI="alreadyMattersList.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column titleKey="序号" media="html" style="width:15px;">
						${processRunItem_rowNum}
					</display:column>
					<display:column  titleKey="请求标题" sortable="true" sortName="subject" style="text-align:left">
						<a name="processDetail" onclick="showDetail(this)" href="#${processRunItem.runId}"  action="info.ht?link=1&runId=${processRunItem.runId}" title='${processRunItem.subject}' >${processRunItem.subject}<%-- ${f:subString(processRunItem.subject)} --%></a>
					</display:column>
					<display:column property="processName" titleKey="流程名称" sortable="true" sortName="processName" style="text-align:left"></display:column>
					<%--
					<display:column property="codebefore" titleKey="processCenter.codebefore" sortable="true" sortName="codebefore" style="text-align:left"></display:column>
					 --%>
					<display:column titleKey="创建人" sortable="true" sortName="creator" style="text-align:left">
						<a href="javascript:userDetail('${processRunItem.creatorId}');">${processRunItem.creator}</a>
					</display:column>
					<display:column  titleKey="创建时间" sortable="true" sortName="createtime" style="text-align:left">
						<fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column titleKey="持续时间" style="text-align:left" >					
						<c:choose>
							<c:when test="${processRunItem.status==10 or processRunItem.status ==2 or processRunItem.status ==3}">
									${f:getTime(processRunItem.duration)}
							</c:when>
							<c:otherwise>
 							${f:getDurationTime(processRunItem.createtime)} 
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="状态" sortable="true" sortName="status" style="text-align:left">
						<f:processStatus status="${processRunItem.status}"></f:processStatus>
					</display:column>
				</display:table>
				<hotent:paging tableId="processRunItem" />
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


