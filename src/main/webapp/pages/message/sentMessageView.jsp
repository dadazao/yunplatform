<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div id="sentMessageDialog">
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul id="tabLiId">
	                  	<li class="selected"><a><span>基本信息</span></a></li>
	                  	<li><a><span>已读消息人员列表</span></a></li>
	                  	<li><a><span>已回复消息人员列表</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" id="tabDivId">
		      <div>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">标题</td>
							<td align="left" width="40%">${sentMessage.subject}</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">收信人</td>
							<td align="left">${sentMessage.receiver}</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">收信组织</td>
							<td align="left">${sentMessage.receiveGroups}</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">需要回复</td>
							<td align="left"><c:if test="${sentMessage.needReply==true}">是</c:if><c:if test="${sentMessage.needReply!=true}">否</c:if></td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">内容</td>
							<td align="left">${sentMessage.content}</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">发送时间</td>
							<td align="left"><fmt:formatDate value="${sentMessage.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</table>
				</div>
				<div id="readReceiversListDiv">
					<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="table">
					<thead><tr><th align="center" width="20%">读消息人</th><th align="center" width="80%">读消息时间</th></tr></thead>
					<tbody>
						<c:forEach items="${readerList}" var="reader">
						<tr>
							<td>${reader.name}</td><td><fmt:formatDate value="${reader.readtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						</c:forEach>
					</tbody>
					</table>
				</div>
				<div id="replyedReceiversListDiv">
					<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="table">
						<thead><tr><th align="center" width="20%">回复人</th><th align="center" width="20%">回复时间</th><th align="center" width="60%">回复内容</th></tr></thead>
						<tbody>
						<c:forEach items="${replyedMessageList}" var="reMessage">
							<tr>
								<td>${reMessage.sender}</td>
								<td><fmt:formatDate value="${reMessage.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td title="${reMessage.content}">
									<c:if test="${fn:length(reMessage.content)>40}">${fn:substring(reMessage.content,0,40)}...</c:if>
		                    		<c:if test="${fn:length(reMessage.content)<=40}">${reMessage.content}</c:if>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div></div>
	</div>
</div>
