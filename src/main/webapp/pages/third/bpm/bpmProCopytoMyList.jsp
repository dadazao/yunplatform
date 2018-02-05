<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>抄送转办事宜</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<script type="text/javascript">
	function mark(t){
		var copyIds = "";
		if(t){
			var tr = $(t).parents("tr"),
				pk = $("input.pk",tr).val();

			if(!pk)return;
			copyIds = pk;
		}
		else{
			var idArr = [];

			$("input.pk").each(function(){
				var me = $(this),
					state = me.attr("checked");

				if(state)
					idArr.push(me.val());
			});
			if(idArr.length==0){
				$.ligerDialog.warn($lang.operateTip.selectRecord,$lang.tip.msg);
				return;
			}
			copyIds = idArr.join(',');
		}
		var url = __ctx + '/platform/bpm/bpmProCopyto/mark.ht';
		var parsms={copyIds:copyIds};
		$.post(url,parsms,function(d){
			if(t){
				return;
			}
			var json = eval("("+d+")");
			if(json.result){
				$.ligerDialog.success(json.message,$lang.tip.msg,function(){
					location.href=location.href.getNewUrl();	
				});
			}
			else{
				$.ligerDialog.error(json.message,$lang.tip.msg);
			}
		});
	};
	
	

	function showDetail(obj){
		//mark(obj);
		var url = $(obj).attr("action");
		jQuery.openFullWindow(url);
	};

	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">抄送转发事宜</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">	
					
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a href="#" class="link reset" onclick="$.clearQueryForm();"><span></span>重置</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link done" onclick="mark()"><span></span>标记为已读</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="myList.ht?porIndex=${porIndex}&tabIndex=${tabIndex}">
					<ul class="row">
						<li><span class="label">请求标题:</span><input type="text" name="Q_subject_SUPL"  class="inputText" value="${param['Q_subject_SUPL']}"/></li>
						<li><span class="label">创建人:</span><input type="text"   id="creator" name="Q_creator_SL" class="inputText"  value="${param['Q_creator_SL']}" onclick="selectUser('creatorId','creator');" readonly="readonly"  />
							<input type="button" value="..." onclick="selectUser('creatorId','creator');">
							<input type="hidden" id="tagIds" name="Q_tagIds_SL"  value="${param['Q_tagIds_SL']}" /></li>
						<%-- <li><span class="label">流程编码:</span><input  type="text" name="Q_codebefore_SUPL" class="inputText"  value="${param['Q_codebefore_SUPL']}" /></li> --%>
						
						<li><span class="label">类型:</span><select name="Q_cpType_L" class="select" style="width:60px;" value="${param['Q_cpType_L']}">
								<option value="">全部</option>
								<option value="1" <c:if test="${param['Q_cpType_L'] == 1}">selected</c:if>>抄送</option>
								<option value="2" <c:if test="${param['Q_cpType_L'] == 2}">selected</c:if>>转发</option>
							</select>
						</li>
						<li><span class="label">是否已读:</span><select name="Q_isReaded_L" class="select" style="width:60px;" value="${param['Q_isReaded_L']}">
								<option value="">全部</option>
								<option value="0" <c:if test="${param['Q_isReaded_L'] == 0}">selected</c:if>>未读</option>
								<option value="1" <c:if test="${param['Q_isReaded_L'] == 1}">selected</c:if>>已读</option>
							</select>
						</li>
						
						<div class="row_date">
						<li><span class="label">创建时间&nbsp;从:</span><input  name="Q_begincreatetime_DL"  class="inputText datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" /></li>
						<li><span class="label">至: </span><input  name="Q_endcreatetime_DG" class="inputText datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"/></li>
						</div>
						<div class="row_date">
						<li><span class="label">接收时间&nbsp;从:</span><input  name="Q_beginccTime_DL"  class="inputText datePicker" datetype="1"  value="${param['Q_beginccTime_DL']}" /></li>
						<li><span class="label">至: </span><input  name="Q_endccTime_DG" class="inputText datePicker" datetype="2" value="${param['Q_endccTime_DG']}"/></li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bpmProCopytoList" id="bpmProCopytoItem" requestURI="myList.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="copyId" value="${bpmProCopytoItem.copyId}">
				</display:column>
				<display:column title="请求标题"  sortable="true" sortName="subject">
					<a onclick="showDetail(this)" href="#${bpmProCopytoItem.runId}" action="${ctx}/platform/bpm/processRun/info.ht?link=1&runId=${bpmProCopytoItem.runId}&copyId=${bpmProCopytoItem.copyId}" title='${bpmProCopytoItem.subject}'>${f:subString(bpmProCopytoItem.subject)}</a>
				</display:column>
				<display:column  title="创建人" sortable="true" sortName="creator">
					<a href="javascript:userDetail('${bpmProCopytoItem.createId}');">${bpmProCopytoItem.creator}</a>
				</display:column>
				<display:column title="是否已读" style="width:60px;" sortable="true" sortName="IS_READED">
					<c:choose>
						<c:when test="${bpmProCopytoItem.isReaded eq 0}"><span class="red close-message">未读</span></c:when>
						<c:when test="${bpmProCopytoItem.isReaded eq 1}"><span class="green open-message">已读</span></c:when>
					</c:choose>
				</display:column>
				<display:column  title="创建时间" >
						<fmt:formatDate value="${bpmProCopytoItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column  title="接收时间" sortable="true" sortName="CC_TIME" >
					<fmt:formatDate value="${bpmProCopytoItem.ccTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column title="类型" style="width:45px;" sortable="true" sortName="CP_TYPE">
					<c:choose>
						<c:when test="${bpmProCopytoItem.cpType eq 1}"><span class="green">抄送流程</span></c:when>
						<c:when test="${bpmProCopytoItem.cpType eq 2}"><span class="brown">转发流程</c:when>
					</c:choose>
				</display:column>
			</display:table>
			<hotent:paging tableId="bpmProCopytoItem"/>
		</div>				
	</div>
</body>
</html>


