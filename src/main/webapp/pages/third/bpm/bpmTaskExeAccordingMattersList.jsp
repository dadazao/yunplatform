<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>转办(代理)事宜</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
</head>
	<script type="text/javascript">

	
	function cancel(id){
		var url=__ctx + '/platform/bpm/bpmTaskExe/cancelDialog.ht?id=' + id;
		var winArgs="dialogWidth=550px;dialogHeight=300px;help=0;status=0;scroll=0;center=1";
		url=url.getNewUrl();
		var rtn=window.showModalDialog(url,"",winArgs);
		if(rtn=="ok"){
			location.href=location.href.getNewUrl();
		}
	}
	
	
	//批量取消转办(代理)
	function batchBack(){
		var ids=new Array();
		$("input[name='id']:checked:enabled").each(function(){
			ids.push($(this).val());
		});
		if(ids.length==0){
			$.ligerDialog.warn($lang.operateTip.selectRecord,$lang.tip.msg);
			return;
		}
		
		var url=__ctx + '/platform/bpm/bpmTaskExe/batCancel.ht?ids=' + ids;
		var winArgs="dialogWidth=500px;dialogHeight=250px;help=0;status=0;scroll=0;center=1";
		url=url.getNewUrl();
		var rtn=window.showModalDialog(url,"",winArgs);
		if(rtn=="ok"){
			location.href=location.href.getNewUrl();
		}
	}


	function showDetail(obj){
		var url = $(obj).attr("action");
		jQuery.openFullWindow(url);
	};
	</script>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">转办(代理)事宜</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a href="#" class="link reset" onclick="$.clearQueryForm();"><span></span>重置</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link cancel" id="btnBack" onclick="batchBack();"><span></span>批量取消</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="accordingMattersList.ht">
					<ul class="row">
						<input type="hidden" name="nodePath" value="${param['nodePath']}" title="流程分类节点路径"></input>
						<li><span class="label">请求标题:</span><input type="text" name="Q_subject_SUPL"  class="inputText"   value="${param['Q_subject_SUPL']}"/></li>
						<li><span class="label">流程名称:</span><input type="text" name="Q_processName_SUPL"  class="inputText"   value="${param['Q_processName_SUPL']}"/>
						<input type="hidden" id="assignId" name="Q_assigneeId_L"   value="${param['Q_assigneeId_L']}"></li>
						<li><span class="label">转办(代理)人:</span>
						<input type="text" id="assignName" name="Q_assignName_S"  class="inputText" readonly="readonly"  value="${param['Q_assignName_S']}" />
						<input type="button" onclick="selectUser('assignId','assignName');"  value="..."/>
						</li>					
						<li><span class="label">创 建 人:</span>
						<input type="hidden" id="creatorId" name="Q_creatorId_L"  value="${param['Q_creatorId_L']}" />
						<input type="text"   id="creator" name="Q_creator_SL" class="inputText"  value="${param['Q_creator_SL']}" onclick="selectUser('creatorId','creator');" readonly="readonly"  />
						<input type="button" value="..." onclick="selectUser('creatorId','creator');"></li>
						<div class="row_date">
						<li><span class="label">创建时间&nbsp;从:</span><input  name="Q_begincratetime_DL"  class="inputText datePicker" datetype="1" value="${param['Q_begincratetime_DL']}" /></li>
						<li><span class="label">至: </span><input  name="Q_endcratetime_DG" class="inputText datePicker"  datetype="2"  value="${param['Q_endcratetime_DG']}"/></li>
						</div>
						<li><span class="label">转办(代理)类型:</span>
							<select type="text" name="Q_status_S" value="${param['Q_status_S']}">
								<option value="" >所有</option>
								<option value="0" <c:if test="${param['Q_status_S'] == '0'}">selected</c:if>>初始状态</option>
								<option value="1" <c:if test="${param['Q_status_S'] == '1'}">selected</c:if>>完成任务</option>
								<option value="2" <c:if test="${param['Q_status_S'] == '2'}">selected</c:if>>取消转办(代理)</option>
								<option value="3" <c:if test="${param['Q_status_S'] == '3'}">selected</c:if>>其他人完成</option> 
							</select>
						</li>
						<li>
							<span class="label">当前状态:</span>
							<select type="text" name="Q_assignType_S" value="${param['Q_assignType_S']}">
								<option value="" >所有</option>
								<option value="1" <c:if test="${param['Q_assignType_S'] == 1}">selected</c:if>>代理</option>
								<option value="2" <c:if test="${param['Q_assignType_S'] == 2}">selected</c:if>>转办</option>
								<option value="4" <c:if test="${param['Q_assignType_S'] == 4}">selected</c:if>>多级转办</option> 
							</select>
						</li>
					</ul>
				</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bpmTaskExeList" id="bpmTaskExeItem" requestURI="accordingMattersList.ht" cellpadding="1" cellspacing="1"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${bpmTaskExeItem.id}"  <c:if test="${bpmTaskExeItem.status!=0}">disabled="disabled"</c:if> >
				</display:column>
				<display:column  titleKey="请求标题" sortable="true" sortName="subject">
					<a name="processDetail" onclick="showDetail(this)" href="#${bpmTaskExeItem.id}"  action="${ctx}/platform/bpm/processRun/info.ht?link=1&runId=${bpmTaskExeItem.runId}" title='${bpmTaskExeItem.subject}' >${f:subString(bpmTaskExeItem.subject)}</a>
				</display:column>
				<display:column property="processName" titleKey="流程名称" sortable="true" sortName="processName" style="text-align:center;"></display:column>
				<display:column titleKey="创建人" sortable="true" sortName="creator" style="text-align:center;">
					<a href="javascript:userDetail('${bpmTaskExeItem.creatorId}');">${bpmTaskExeItem.creator}</a>
				</display:column>
				<display:column titleKey="转办(代理)人" sortable="true" sortName="assigneeName" style="text-align:center;">
					<a href="javascript:userDetail('${bpmTaskExeItem.assigneeId}');">${bpmTaskExeItem.assigneeName}</a>
				</display:column>
				<display:column  titleKey="创建时间" sortable="true" sortName="cratetime" style="text-align:center;">
					<fmt:formatDate value="${bpmTaskExeItem.cratetime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column titleKey="转办(代理)类型" sortable="true" sortName="status" style="text-align:center;">
					<c:choose>
						<c:when test="${bpmTaskExeItem.status==0}">
							<span class="green">初始状态</span>
						</c:when>
						<c:when test="${bpmTaskExeItem.status==1}">
							<span class="green">完成任务</span>
						</c:when>
						<c:when test="${bpmTaskExeItem.status==2}">
							<c:choose>
								<c:when test="${bpmTaskExeItem.assignType==1}">
									<span class="red">取消代理</span>
								</c:when>
								<c:when test="${bpmTaskExeItem.assignType==2 || bpmTaskExeItem.assignType==4}">
									<span class="red">取消转办</span>
								</c:when>
								<c:otherwise>
									<span class="red">取消流转</span>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${bpmTaskExeItem.status==3}">
							<span class="green">其他人完成</span>
						</c:when>
					</c:choose>
				</display:column>
				<display:column titleKey="待办类型" sortable="true" sortName="assignType" style="text-align:center;">
					<c:choose>
						<c:when test="${bpmTaskExeItem.assignType==1}">
							<span class="brown">代理</span>
						</c:when>
						<c:when test="${bpmTaskExeItem.assignType==2}">
							<span class="green">转办</span>
						</c:when>
						<c:when test="${bpmTaskExeItem.assignType==4}">
							<span class="green">多级转办</span>
						</c:when>
						<c:otherwise>
							<span class="red">流转</span>
						</c:otherwise>
					</c:choose>
				</display:column>
	
				<display:column titleKey="管理" media="html" style="width:150px">
					<c:if test="${bpmTaskExeItem.status==0}">
							<c:set var="cancel_title"/>
							<c:choose>
								<c:when test="${bpmTaskExeItem.assignType==1}">
									<c:set var="cancel_title">取消代理</c:set>
								</c:when>
								<c:when test="${bpmTaskExeItem.assignType==2 || bpmTaskExeItem.assignType==4}">
									<c:set var="cancel_title">取消转办</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="cancel_title">取消流转</c:set>
								</c:otherwise>
							</c:choose>	
						<a href="#" onclick="cancel(${bpmTaskExeItem.id})" title="${cancel_title}"  class="link cancel">取消</a>
					</c:if>
					<c:if test="${bpmTaskExeItem.status!=0}">
						<a href="del.ht?id=${bpmTaskExeItem.id}" class="link del" >删除</a>
					</c:if>
					<c:if test="${bpmTaskExeItem.assignType==2 || bpmTaskExeItem.assignType==4}">
						<a href="getAssignDetail.ht?taskId=${bpmTaskExeItem.taskId}" class="link detail" target="_blank">转办明细</a>
					</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="bpmTaskExeItem" />
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


