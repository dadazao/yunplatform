<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div align="center">
		<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td class="Input_Table_Label" width="20%">
					运行实例ID(runId)
				</td>
				<td>${processRun.runId}</td>
			</tr>
			<tr>
				<td class="Input_Table_Label" width="20%">流程定义ID:</td>
				<td>${processRun.defId}</td>
			</tr>
			<tr>
				<td class="Input_Table_Label" width="20%">流程实例标题:</td>
				<td>${processRun.subject}</td>
			</tr>
<%--			<tr>--%>
<%--				<td class="Input_Table_Label" width="20%">创建人ID:</td>--%>
<%--				<td>${processRun.creatorId}</td>--%>
<%--			</tr>--%>
			<tr>
				<td class="Input_Table_Label" width="20%">创建人:</td>
				<td>${processRun.creator}</td>
			</tr>
			<tr>
				<td class="Input_Table_Label" width="20%">创建时间:</td>
				<td><fmt:formatDate value="${processRun.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
<%--			<tr>--%>
<%--				<td class="Input_Table_Label" width="20%">业务表单简述:</td>--%>
<%--				<td>${processRun.busDescp}</td>--%>
<%--			</tr>--%>
			<tr>
				<td class="Input_Table_Label" width="20%">状态:</td>
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
				</c:choose>
				</td>
			</tr>
			<tr>
				<td class="Input_Table_Label" width="20%">ACT流程实例ID:</td>
				<td>${processRun.actInstId}</td>
			</tr>
			<tr>
				<td class="Input_Table_Label" width="20%">ACT流程定义ID:</td>
				<td>${processRun.actDefId}</td>
			</tr>
			<tr>
				<td class="Input_Table_Label" width="20%">businessKey:</td>
				<td>${processRun.businessKey}</td>
			</tr>
		</table>
</div>

