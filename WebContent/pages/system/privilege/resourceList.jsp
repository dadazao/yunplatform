<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/privilege/privilegeresourceList.action">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	<input type="hidden" name="module" value="${module}" />
	<input type="hidden" name="privilegeId" value="${privilegeId}" />
</form>
<form id="resourceListForm" name="resourceListForm" method="post">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
			<thead>
				<tr>
					<th class="thClass" width="5%" align="center"><input name="all" type="checkbox"  class="checkbox" onclick="dialogSelectAll(this,'selectedSubIDs')"/></th>
					<th class="thClass" width="5%" align="center">序号</th>
					<th class="thClass" width="15%" align="center">资源名称</th>
					<th class="thClass" width="60%" align="center">URL</th>
					<th class="thClass" width="10%" align="center" >是否启用</th>
					<th class="thClass" width="5%" align="center">操作</th>
				</tr>
			</thead>
            <c:forEach items="${pageResult.content}" var="resource" varStatus="status">
                <c:if test="${status.count%2==0}">
                	<tr target="id_column" rel="1" class='event'>
                </c:if>
                <c:if test="${status.count%2!=0}">
                	<tr target="id_column" rel="1">
                </c:if>
	                    <td class="tdClass" align="center" width="5%"><input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${resource.id}"></td>
	                    <td class="tdClass" width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    <td class="tdClass">${resource.resourceName}</td>
	                    <td class="tdClass">${resource.resourceUrl}</td>
	                    <td class="tdClass">
	                    	<c:if test="${resource.enabled==0}">否</c:if>
	                    	<c:if test="${resource.enabled==1}">是</c:if>
	                    </td>
	                    <td class="tdClass">
							<a style="cursor: pointer;color: blue;" onclick="ns.privilege.loadEditResource('${resource.id}')">维护</a>
	                    </td>
	                </tr>
            </c:forEach>
		</tbody>	
	</table>
</form>
<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<input type="hidden" name="numPerPage" value="20">
		<span>20条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" rel="authResourceListId" targetType="dialog" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>