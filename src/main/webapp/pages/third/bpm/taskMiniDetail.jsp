<%
	//用于任务管理(taskList.jsp)中的某一任务的右边明细显示 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>

<table class="table-detail" style="width:100%">
	<tr>
		<th nowrap="nowrap" width="100">流程事项名称</th>
		<td width="*">
			${processRun.subject}
		</td>
	</tr>
	<tr>
		<th>任务ID</th>
		<td>${taskEntity.id}</td>
	</tr>
	<tr>
		<th nowrap="nowrap">
			流程运行(RUNID)
		</th>
		<td>${processRun.runId}</td>
	</tr>
	<tr>
		<th nowrap="nowrap">任务名</th>
		<td>${taskEntity.name}</td>
	</tr>
	<tr>
		<th>任务描述</th>
		<td>
			<c:choose>
				<c:when test="${not empty task.description}">
					${taskEntity.description}
				</c:when>
				<c:otherwise>${taskEntity.name}</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">执行人</th>
		<td>
			<span><f:userName userId="${taskEntity.assignee}"/></span>&nbsp;<c:if test="${not empty param['manage']}"><a href='#' class='button' onclick="setTaskAssignee(this,${taskEntity.id})"><span>更改..</span></c:if></a>
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">候选执行人</th>
		<td>
			<c:choose>
				<c:when test="${fn:length(candidateUsers)==0}">
					<font color='red'>暂无</font>
				</c:when>
				<c:otherwise>
					<c:forEach items="${candidateUsers}" var="executor">
					
						<c:if test="${not empty executor }">
						<c:set var="type" >
							<c:choose>
								<c:when test="${executor.type eq 'org' }">(组织)</c:when>
								<c:when test="${executor.type eq 'role' }">(角色)</c:when>
								<c:when test="${executor.type eq 'pos' }">(岗位)</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</c:set>
						<img src='${ctx}/styles/default/images/bpm/user-16.png'>${executor.executor}${type }
						</c:if>
					</c:forEach>		
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th>当前活动任务</th>
		<td>
			<c:forEach items="${curTaskList}" var="curTask" varStatus="i"><c:if test="${i.count>1}">,</c:if>${curTask.name}(<f:userName userId="${curTask.assignee}"/>)</c:forEach>
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">创建时间</th>
		<td>
			<fmt:formatDate value="${taskEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">到期时间</th>
		<td>
			<fmt:formatDate value="${taskEntity.dueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
</table>
<br/>
<table class="table-detail" style="width:100%">
	<tr>
		<th nowrap="nowrap" width="100">流程定义名称</th>
		<td>${processDefinition.subject}</td>
	</tr>
	<tr>
		<th>版本</th>
		<td>
			${processDefinition.versionNo}
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">流程定义描述</th>
		<td>
			${processDefinition.descp}
		</td>
	</tr>
	
</table>

