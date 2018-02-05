<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/tabulationlistColumn.action?tabulationId=${tabulationId}">
	<input type=hidden name="model" value="${model}"/>
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="tabulation.tabulationName" value="${tabulation.tabulationName}">
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
	<div class="pagination" targetType="dialog" rel="tabulationColumnListId" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<div class="grid" id="grid_column">
	<div class="gridHeader">
		<table  class="table" width="100%">
			<thead>
				<tr>
					<th  align="center"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/></th>
					<th  align="center">序号</th>
					<th  align="center">字段名</th>
					<th  align="center">列表中显示</th>
					<th  align="center">列表显示顺序</th>
					<th  align="center">查询条件</th>
					<th  align="center">默认查询条件</th>
					<th  align="center">是否启用</th>
					<th  align="center">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="gridScroller">
	    <div class="gridTbody">
	    	<form id="tabulationColumnListForm" name="tabulationColumnListForm">
		    <table class="table" width="100%">
				<tbody>
				<c:forEach items="${pageResult.content}" var="column" varStatus="status">
			         <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
			             <td align="center" ><input type="checkbox" class="checkbox"  name="selectedIDs" value="${column.id}"></td>
			             <td><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
			             <td>${column.columnZhName}</td>
			             <td>
			             	<c:if test="${column.isShowInList==1 }">是</c:if>
			             	<c:if test="${column.isShowInList==0 }">否</c:if>
			             </td>
			             <td>${column.listOrder}</td>
						 <td>
							<c:if test="${column.isQuery==1 }">是</c:if>
			             	<c:if test="${column.isQuery==0 }">否</c:if>
						</td>
						<td>
							<c:if test="${column.isDefaultQuery==1 }">是</c:if>
			             	<c:if test="${column.isDefaultQuery==0 }">否</c:if>
						</td>
						<td>
							<c:if test="${column.isUse==1 }">是</c:if>
			             	<c:if test="${column.isUse==0 }">否</c:if>
						</td>

						<td align="center">
							&nbsp;
							<a onclick="loadEditTabulationColumn('${column.id}');" style="cursor: hand"><span>维护</span></a>
							&nbsp;
						</td>
			        </tr>
			    </c:forEach>
				</tbody>	
			</table>
			</form>
		</div>
	</div>
</div>