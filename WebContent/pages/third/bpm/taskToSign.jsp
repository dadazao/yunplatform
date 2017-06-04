<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<div class="panel-top">
		<div class="panel-detail">
			<table class="table-detail">
				<tr>
					<th nowrap="nowrap" width="120">会签人</th>
					<td>${curUser.fullname}</td>
				</tr>
				<tr>
					<th nowrap="nowrap">会签意见</th>
					<td>
						<textarea rows="3" cols="78" id="voteContent" name="voteContent" maxlength="512"></textarea>
						<input type="hidden" id="isSignTask" name="isSignTask" value="true"/>
					</td>
				</tr>
				<c:if test="${not empty bpmNodeSign}">
					<tr>
						<th>投票决策方式</th>
						<td>
							投
							<font color='green'>
							<b>
							<c:choose>
								<c:when test="${bpmNodeSign.decideType==1}">同意</c:when>
								<c:otherwise>反对</c:otherwise>
							</c:choose>
							</b>
							</font>
							票数
							<c:choose>
								<c:when test="${bpmNodeSign.voteType==1}">占百分比</c:when>
								<c:otherwise>达</c:otherwise>
							</c:choose>
							<b>${bpmNodeSign.voteAmount}</b>,则完成投票。
						</td>
					</tr>
				</c:if>	
			</table>
			</div>
	</div>
	<br/>
	<div class="panel-body">
		<display:table name="signDataList" id="taskSignDataItem" cellpadding="1" cellspacing="1" class="table-grid">
			<display:column property="voteUserName" title="投票人名"></display:column>
			<display:column  title="投票时间">
				<fmt:formatDate value="${taskSignDataItem.voteTime}" pattern="yyyy-MM-dd HH:mm"/>
			</display:column>
			<display:column title="是否同意">
				<c:choose>
					<c:when test="${taskSignDataItem.isAgree==1}"><font color='green'>同意</font></c:when>
					<c:when test="${taskSignDataItem.isAgree==2}"><font color='red'>反对</font></c:when>
					<c:when test="${taskSignDataItem.isAgree==0}"><font color='gray'>弃票</font></c:when>
					<c:when test="${taskSignDataItem.isAgree==5}"><font color='green'>直接同意</font></c:when>
					<c:when test="${taskSignDataItem.isAgree==6}"><font color='red'>直接反对</font></c:when>
				</c:choose>
			</display:column>
			<display:column property="signNums" title="投票次号"/>
			<display:column title="投票意见内容">
				<c:choose>
					<c:when test="${empty taskSignDataItem.voteTime}">尚未签批</c:when>
					<c:otherwise>${taskSignDataItem.content}</c:otherwise>
				</c:choose>
			</display:column>
		</display:table>
	</div>
