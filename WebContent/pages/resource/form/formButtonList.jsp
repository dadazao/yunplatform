<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/fbuttonlistButton.action?formId=${formId}">
	<input type=hidden name="model" value="${model}"/>
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>
<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<input type="hidden" name="numPerPage" value="20">
		<span>${pageResult.pageSize}条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="buttonListId" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
   	<form id="formButtonListForm" name="formButtonListForm">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
		<thead>
			<tr>
				<th class="thClass" width="5%"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedSubIDs')"/></th>
				<th class="thClass" width="5%">序号</th>
				<th class="thClass">按钮名称</th>
<%--				<th class="thClass">按钮类型</th>--%>
				<th class="thClass">功能说明</th>
				<th class="thClass">应用位置</th>
				<th class="thClass">显示顺序</th>
				<th class="thClass" width="10%">操作</th>
			</tr>
		</thead>
		<c:forEach items="${pageResult.content}" var="button" varStatus="status">
	         <tr>
	             <td class="tdClass" width="5%"><input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${button.id}"></td>
	             <td class="tdClass" width="5%"><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
	             <td class="tdClass">${button.showName}</td>
	             <td class="tdClass" width="45%" title="${button.fcomment}">
	             	<c:choose>
	             		<c:when test="${fn:length(button.fcomment)>20}">${fn:substring(button.fcomment,0,20)}....</c:when>
	             		<c:otherwise>${button.fcomment}</c:otherwise>
	             	</c:choose>
	             </td>
<%--	             <td class="tdClass">--%>
<%--	             	<c:if test="${button.buttonType == 0}">按钮</c:if>--%>
<%--					<c:if test="${button.buttonType == 1}">按钮组</c:if>--%>
<%--				 </td>--%>
				 <td class="tdClass">
				 	<c:if test="${button.tabId=='-1'}">整个表单</c:if>
				 	<c:if test="${button.tabId!='-1'}">${button.tabName}</c:if>
				 </td>
				 <td class="tdClass" width="10%">
				 	${button.showOrder}
				 </td>
				 <td class="tdClass" width="10%">
					 &nbsp;
					 <a href="#" onclick="loadEditFormButton('${button.id}','${op}');" style="cursor: hand"><span>维护</span></a>
					 &nbsp;
				 </td>
	        </tr>
	    </c:forEach>
		</tbody>	
	</table>
	</form>
