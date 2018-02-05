<%--
	time:2011-12-04 18:56:52
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>${processRun.subject}</title>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<%@include file="/commons/include/get.jsp" %>
	<style type="text/css"> 
	    body{ padding:0px; margin:0;overflow:hidden;}  
	</style>
</head>
<body>
<div class="panel">
		<div class="l-layout-header">流程实例-【<i>${processRun.subject}</i>】明细。</div>
		
		<c:if test="${param.tab eq 1 }">
				<c:choose>
					<c:when test="${processRun.status==2  || processRun.status==3}">
						<f:tab curTab="detail" tabName="process" />
					</c:when>
					<c:otherwise>
						<f:tab curTab="detail" tabName="process" hideTabs="copyUser"/>
					</c:otherwise>
				</c:choose>
		</c:if>
		<div class="panel-body">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th width="20%">
							运行实例ID(runId)
						</th>
						<td>${processRun.runId}</td>
					</tr>
					<tr>
						<th width="20%">流程定义ID:</th>
						<td>${processRun.defId}</td>
					</tr>
					<tr>
						<th width="20%">流程实例标题:</th>
						<td>${processRun.subject}</td>
					</tr>
					<tr>
						<th width="20%">创建人ID:</th>
						<td>${processRun.creatorId}</td>
					</tr>
					<tr>
						<th width="20%">创建人:</th>
						<td>${processRun.creator}</td>
					</tr>
					<tr>
						<th width="20%">创建时间:</th>
						<td>${f:shortDate(processRun.createtime)}</td>
					</tr>
					<tr>
						<th width="20%">业务表单简述:</th>
						<td>${processRun.busDescp}</td>
					</tr>
					<tr>
						<th width="20%">状态:</th>
						<td>
							<c:choose>
							<c:when test="${processRun.status==1}">
								<font color='green'>正在运行</font>
							</c:when>
							<c:when test="${processRun.status==2}">
								结束
							</c:when>
							<c:when test="${processRun.status==3}">
								人工结束
							</c:when>
							<c:when test="${processRun.status==4}">
								流程草稿
							</c:when>
							<c:when test="${processRun.status==5}">
								已撤销
							</c:when>
							<c:when test="${processRun.status==6}">
								已驳回
							</c:when>
							<c:when test="${processRun.status==10}">
								逻辑删除
							</c:when>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th width="20%">ACT流程实例ID:</th>
						<td>${processRun.actInstId}</td>
					</tr>
					<tr>
						<th width="20%">ACT流程定义ID:</th>
						<td>${processRun.actDefId}</td>
					</tr>
					<tr>
						<th width="20%">businessKey:</th>
						<td>${processRun.businessKey}</td>
					</tr>
				</table>
			</div><!-- end of tab panel -->
		</div>
</div>

</body>
</html>
