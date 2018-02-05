<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/columncolumnList.action?tableId=${tableId}">
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
	<div class="pagination" targetType="dialog" rel=table_column_list_div totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
   	<form id="tableColumnListForm" name="tableColumnListForm">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
		<thead>
			<tr>
				<th class="thClass" width="5%"><input name="all" type="checkbox"  class="checkbox" onclick="dialogSelectAll(this,'selectedSubIDs')"/></th>
				<th class="thClass" width="5%">序号</th>
				<th class="thClass">字段拼音名</th>
				<th class="thClass">字段中文名</th>
				<th class="thClass">字段英文名</th>
				<th class="thClass">功能说明</th>
				<th class="thClass">数据类型</th>
				<th class="thClass">状态</th>
				<th class="thClass" width="5%">操作</th>
			</tr>
		</thead>
		<c:forEach items="${pageResult.content}" var="column" varStatus="status">
	        <c:if test="${status.count%2==0}">
               	<tr target="id_column" rel="1" class='event'>
               </c:if>
               <c:if test="${status.count%2!=0}">
               	<tr target="id_column" rel="1">
               </c:if>
	             <td class="tdClass" width="5%"><input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${column.id}"></td>
	             <td class="tdClass" width="5%"><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
	             <td class="tdClass">${column.columnName}</td>
	             <td class="tdClass">${column.columnZhName}</td>
	             <td class="tdClass">${column.columnEnName}</td>
	             <td class="tdClass">${column.comment}</td>
	             <td class="tdClass">${column.dataType}</td>
	             <td class="tdClass">
	             	<c:if test="${column.status==0}">未锁</c:if>
	             	<c:if test="${column.status==1}">锁定</c:if>
	             </td>
				 <td class="tdClass" width="5%">
					&nbsp;
					<a href="#" onclick="loadEditTableColumn('${column.id}');"><span>维护</span></a>
					&nbsp;
				</td>
	        </tr>
	    </c:forEach>
		</tbody>	
	</table>
	</form>
