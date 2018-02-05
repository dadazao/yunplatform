<%@ page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
		<title>流程实例-[${processRun.subject}]表单</title>
		<%@include file="/commons/include/customForm.jsp" %>
		<script type="text/javascript">
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
       <div class="l-layout-header">流程实例-[${processRun.subject}]表单。]</div>
       <div class="panel">
       <c:if test="${param.tab eq 1 }">
			<f:tab curTab="taskForm" tabName="process"/>
		</c:if>
		<div class="panel-body">
			<c:choose>
				<c:when test="${isExtForm==true }">
					<div id="divExternalForm" formUrl="${form}"></div>
				</c:when>
				<c:otherwise>
					${form}
				</c:otherwise>
			</c:choose>
	   </div>
      </div> 
</body>
</html>