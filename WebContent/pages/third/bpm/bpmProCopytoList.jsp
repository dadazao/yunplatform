<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>抄送转发列表</title>
<%@include file="/commons/include/get.jsp" %>
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
				$.ligerDialog.warn("请选择要标记的记录!","提示信息");
				return;
			}
			copyIds = idArr.join(',');
		}
		var url = __ctx + '/platform/bpm/bpmProCopyto/mark.ht';
		var params={copyIds:copyIds};
		$.post(url,params,function(d){
			var json = eval("("+d+")");
			if(json.result){
				if(!t){
					$.ligerDialog.success(json.message,"提示信息",function(){
						location.href=location.href.getNewUrl();
					});
				}
			}
			else{
				$.ligerDialog.error(json.message,"提示信息");
			}
		});
	};
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">抄送转发列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link done" onclick="mark()"><span></span>标记为已读</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" action="del.ht"><span></span>删除</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="${ctx}/platform/bpm/processRun/history.ht"><span></span>返回</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">流程标题:</span>
						<input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/>
						<span class="label" style="margin:0 0 0 20px;">抄送转发给:</span>
						<input type="text" name="Q_ccUname_SL"  class="inputText" value="${param['Q_ccUname_SL']}"/>
						<span class="label" style="margin:0 0 0 20px;">发起人:</span>
						<input type="text" name="Q_creator_SL"  class="inputText" value="${param['Q_creator_SL']}"/>
						<span class="label" style="margin:0 0 0 20px;">类型:</span>
						<select name="Q_cpType_L" class="select" style="width:60px;" value="${param['Q_cpType_L']}">
							<option value="">所有</option>
							<option value="1" <c:if test="${param['Q_cpType_L'] == 1}">selected</c:if>>抄送</option>
							<option value="2" <c:if test="${param['Q_cpType_L'] == 2}">selected</c:if>>转发</option>
						</select>
						<span class="label" style="margin:0 0 0 20px;">是否已读:</span>
						<select name="Q_isReaded_L" class="select" style="width:60px;" value="${param['Q_isReaded_L']}">
							<option value="">所有</option>
							<option value="0" <c:if test="${param['Q_isReaded_L'] == 0}">selected</c:if>>未读</option>
							<option value="1" <c:if test="${param['Q_isReaded_L'] == 1}">selected</c:if>>已读</option>
						</select>
						<input type="hidden" name="runId" value="${runId}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bpmProCopytoList" id="bpmProCopytoItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="copyId"  value="${bpmProCopytoItem.copyId}">
				</display:column>
				<display:column property="subject" title="流程标题"></display:column>
				<display:column property="ccUname" title="抄送转发给"></display:column>
				<display:column property="creator" title="发起人"></display:column>
				<display:column title="是否已读" style="width:60px;" sortable="true" sortName="IS_READED">
					<c:choose>
						<c:when test="${bpmProCopytoItem.isReaded eq 0}"><span class="red close-message">未读</span></c:when>
						<c:when test="${bpmProCopytoItem.isReaded eq 1}"><span class="green open-message">已读</span></c:when>
					</c:choose>
				</display:column>
				<display:column  title="阅读时间" sortable="true" sortName="READ_TIME">
					<fmt:formatDate value="${bpmProCopytoItem.readTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column title="类型" style="width:45px;" sortable="true" sortName="CP_TYPE">
					<c:choose>
						<c:when test="${bpmProCopytoItem.cpType eq 1}"><span class="green">抄送</span></c:when>
						<c:when test="${bpmProCopytoItem.cpType eq 2}"><span class="brown">转发</span></c:when>
					</c:choose>
				</display:column>
				<display:column title="管理" media="html" style="width:180px;line-height:21px;">
					<a class="link del" href="del.ht?copyId=${bpmProCopytoItem.copyId}">删除</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="bpmProCopytoItem"/>
		</div>
	</div>
</body>
</html>


