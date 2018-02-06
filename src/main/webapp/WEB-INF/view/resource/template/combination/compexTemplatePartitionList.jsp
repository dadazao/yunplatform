<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/partition/list.action?templateId=${templateId}">
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
	<div class="pagination" targetType="dialog" rel="partitionListDiv" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<form id="partitionListForm" name="partitionListForm">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<thead>
			<tr>
				<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;" align="center"><input name="all" type="checkbox" onclick="dialogSelectAll(this,'selectedIDs')"/></th>
				<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;" align="center">序号</th>
				<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;" align="center">分区名称</th>
				<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;" align="center">分区类型</th>
				<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;" align="center">基础模板</th>
				<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;" align="center">顺序</th>
				<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageResult.content}" var="partition" varStatus="status">
		         <c:if test="${status.count%2==0}">
                	<tr target="id_column" rel="1" class='event'>
                </c:if>
                <c:if test="${status.count%2!=0}">
                	<tr target="id_column" rel="1">
                </c:if>
		             <td class="tdClass" style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;" align="center" ><input type="checkbox" class="checkbox"  name="selectedIDs" value="${partition.id}"></td>
		             <td class="tdClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;" align="center"><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
		             <td class="tdClass" style=" border-bottom:1px #ededed solid; border-right:1px #ededed solid;" align="center">${partition.partitionName}</td>
		             <td class="tdClass" style=" border-bottom:1px #ededed solid; border-right:1px #ededed solid;" align="center">
		             	<c:forEach items="${partitionTypes}" var="partitionType">
							<c:if test="${partitionType.value==partition.partitionType }">${partitionType.name}</c:if>
						</c:forEach>
		             </td>
					 <td class="tdClass" style=" border-bottom:1px #ededed solid; border-right:1px #ededed solid;" align="center">
						${partition.baseTemplateName}
					</td>
					<td class="tdClass" style=" border-bottom:1px #ededed solid; border-right:1px #ededed solid;" align="center">
						${partition.showOrder}
					</td>
					<td class="tdClass" style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;" align="center">
						&nbsp;
						<a onclick="loadEditPartition('${partition.id}');" style="cursor: hand"><span>维护</span></a>
						&nbsp;
					</td>
		        </tr>
		    </c:forEach>
		</tbody>
	</table>
</form>
