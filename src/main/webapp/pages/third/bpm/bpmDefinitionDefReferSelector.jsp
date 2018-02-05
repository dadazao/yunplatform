<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>流程引用</title>
<f:js pre="js/lang/view/platform/bpm" ></f:js>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ProcessUrgeDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript">
	function urge(id){
		ProcessUrgeDialog({
			actInstId : id
		});
	};
	function selectProcess(){
		var chIds= $("input.pk[name='defId']:checked");
		var aryDefId = [];
		$.each(chIds,function(i,ch){
			aryDefId.push($(ch).val());
		});
		
		var params={defId:$('#bpmDefId').val(),refers:aryDefId.join(",")};
		if(aryDefId.length > 0){
			$.post("saveReferDef.ht",params,function(msg){
				var obj=new com.hotent.form.ResultMessage(msg);
				if(obj.isSuccess()){
					$.ligerDialog.success(obj.getMessage(),$lang.tip.msg ,function(){
							window.parent.location.href=window.parent.location.href.getNewUrl();
					});
					
				}else{
					$.ligerDialog.error(obj.getMessage(),$lang.tip.msg);
				}
			});
		}else{
			$.ligerDialog.warn($lang_bpm.bpmDefinition.defReferSelector.selectFlow,$lang.tip.msg);
		}
	}
	function dialogClose(){
		 parent.referDef.close();
	}
</script>
</head>
<body>
<div id="bpmTagLayoutt">
	<div position="center" >
		<div class="panel">
		<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link search" id="btnSearch"><span></span>查询</a>  
						<a href="#" class="link done"  onclick="selectProcess()"  id="processSave"><span></span>选择</a>
						<a href="#" class="link cancel"   onclick="dialogClose() "><span></span>取消</a>
					</div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="defReferSelector.ht">
					<input type="hidden" name="defId" id="bpmDefId" value="${defId }"/>
					<div class="row">
						<span class="label">流程标题:</span><input type="text" name="Q_subject_SL"  class="inputText" style="width:120px;"/>
						<span class="label">流程定义Key:</span><input type="text" name="Q_defKey_SL"  class="inputText" style="width:120px;"/>
						<br>
						<span class="label" style="margin-left:10px">创建时间:</span><input type="text" name="Q_createtime_DL"  class="inputText date" style="width:120px;"/>
						<span class="label">至</span><input name="Q_endcreatetime_DG" class="inputText date" style="width:120px;"/>
					</div>
				</form>
			</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="bpmDefinitionList" id="bpmDefinitionItem" requestURI="defReferSelector.ht" sort="external" 
			    	cellpadding="1" cellspacing="1" export="false"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="defId" value="${bpmDefinitionItem.defId}">
					</display:column>
					<display:column property="subject" titleKey="流程标题" sortable="true" sortName="subject" ></display:column>
					
					<display:column titleKey="分类" sortable="true" sortName="typeName">
						<c:out value="${bpmDefinitionItem.typeName}"></c:out>
					</display:column>
					<display:column property="versionNo" titleKey="版本号" sortable="true" sortName="versionNo" style="width:60px"></display:column>
					<display:column titleKey="状态" sortable="true" sortName="status" style="width:60px">
						<c:choose>
							<c:when test="${bpmDefinitionItem.status eq 0}">未发布</span></c:when>
							<c:when test="${bpmDefinitionItem.status eq 1}"><span class="green">发布</span></c:when>
							<c:when test="${bpmDefinitionItem.status eq 2}"><span class="red">禁用</span></c:when>
							<c:when test="${bpmDefinitionItem.status eq 3}"><span class="red">禁用(实例)</span></c:when>
							<c:when test="${bpmDefinitionItem.status eq 4}"><span class="green">测试</span></c:when>
							<c:otherwise><span class="red">未选择</span></c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="创建时间" sortable="true" sortName="createtime" >
						<fmt:formatDate value="${bpmDefinitionItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
					
				</display:table>
				<hotent:paging tableId="bpmDefinitionItem"></hotent:paging>
		</div>			
	</div> 
	</div>
</div>
</body>
</html>


