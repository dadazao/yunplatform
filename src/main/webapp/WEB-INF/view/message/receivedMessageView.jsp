<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div id="receivedMessageDialog">
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul id="tabLiId">
	                  	<li class="selected"><a><span>基本信息</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" id="tabDivId">
		      <div>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">标题</td>
							<td align="left" width="40%">${receivedMessage.subject}</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">回复状态</td>
							<td align="left">
								<c:if test="${receivedMessage.replyState==1}">需要回复</c:if>
								<c:if test="${receivedMessage.replyState==2}">已经恢复</c:if>
								<c:if test="${receivedMessage.replyState==0}">不需要回复</c:if>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">接收时间</td>
							<td align="left"><fmt:formatDate value="${receivedMessage.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">内容</td>
							<td align="left" style="padding: 15px;">${receivedMessage.content}</td>
						</tr>
					</table>
				</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div></div>
	</div>
</div>
