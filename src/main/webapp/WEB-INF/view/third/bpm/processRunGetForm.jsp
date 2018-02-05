<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
	<head>
		<title>流程实例业务表单明细</title>
		<%@include file="/commons/include/customForm.jsp" %>
		<script type="text/javascript">
			var isExtForm=${isExtForm};
			var isFormEmpty=${isFormEmpty};
			$(function(){
				if(!isFormEmpty){
					if(isExtForm){
						var formUrl=$('#divExternalForm').attr("formUrl");
						$('#divExternalForm').load(formUrl, function() {
							hasLoadComplete=true;
							//动态执行第三方表单指定执行的js
							try{
								afterOnload();
							}catch(e){}
						});
					}
				}
			});
		</script>
	</head>
	<body >
		<div class="panel">
		<div class="panel-top">
			<div class="l-layout-header">流程实例-【<i>${processRun.subject}</i>】业务表单。</div>
		</div>
		<c:choose>
			<c:when test="${processRun.status==2  || processRun.status==3}">
				<f:tab curTab="taskForm" tabName="process"/>
			</c:when>
			<c:otherwise>
				<f:tab curTab="taskForm" tabName="process"  hideTabs="copyUser"/>
			</c:otherwise>
		</c:choose>
		
		<div class="panel-body">
			<c:choose>
				<c:when test="${!isFormEmpty}">
					<c:choose>
						<c:when test="${isExtForm}">
							<div id="divExternalForm" formUrl="${form}"></div>
						</c:when>
						<c:otherwise>
							${form}
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					未设置流程实例业务表单
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	</body>
</html>