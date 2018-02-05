<%
	//显示流程示意图及显示每个任务节点上的执行人员
%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery-powerFloat.js"></script>
<%-- <link href="${ctx}/js/jquery/plugins/powerFloat.css" rel="stylesheet" type="text/css" /> --%>
<f:link href="jquery/plugins/powerFloat.css"></f:link>
<script type="text/javascript">
	$(function(){
		$.each($("div.flowNode"),function(){
			if($(this).attr('type')=='userTask' || $(this).attr('type')=='multiUserTask'){
				$(this).css('cursor','pointer');
				var nodeId=$(this).attr('id');
				$(this).powerFloat({ eventType: "click", target: "${ctx}/platform/bpm/bpmDefinition/nodeUser.ht?processDefinitionId=${processDefinitionId}&nodeId=" + nodeId, targetMode: "ajax"});
			}
		});
	});
</script>

<div><b>说明：</b>点击任务节点可以查看节点的执行人员</div>
<div style="padding-top:40px;background-color: white;">
	<div style="position: relative;background:url('${ctx}/bpmImage?definitionId=${processDefinitionId}') no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
		${shapeMeta.xml}
	</div>
</div>