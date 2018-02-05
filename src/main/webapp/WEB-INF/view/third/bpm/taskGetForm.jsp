<%@ page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
		<title>流程任务-[${task.name}]执行</title>
		<%@include file="/commons/include/customForm.jsp" %>
		<script type="text/javascript">
		var taskId='${task.id}';
		var isExtForm=eval('${isExtForm}');
		$(function(){
			if(isExtForm){
				var formUrl=$('#divExternalForm').attr("formUrl");
				$('#divExternalForm').load(formUrl, function() {});
			}
		});
	</script>	
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务明细--${processRun.subject}--${task.name}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link close" href="javascript:window.close();"><span></span>关闭</a></div>
				</div>
			</div>
		</div>
		<c:if test="${action=='task'}">
			<f:tab curTab="taskForm" tabName="task"/>
		</c:if>
		<div class="panel-body">
			<form id="frmWorkFlow" method="post" >
				<c:choose>
					<c:when test="${isExtForm==true}">
						<div id="divExternalForm" formUrl="${form}"></div>
					</c:when>
					<c:otherwise>
						${form}
						<input type="hidden" id="formData" name="formData" value=""/>
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="taskId" value="${task.id}"/> 
			</form>
	   </div>
     </div>
   </div>
</body>
</html>