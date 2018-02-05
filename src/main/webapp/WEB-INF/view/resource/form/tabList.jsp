<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/tab/list.action?formId=${formId}">
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
	<div class="pagination" targetType="dialog" rel="tabListId" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
   	<form id="tabListForm" name="tabListForm">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
		<thead>
			<tr>
				<th class="thClass" width="5%"><input name="all" type="checkbox"  class="checkbox" onclick="dialogSelectAll(this,'selectedSubIDs')"/></th>
				<th class="thClass" width="5%">序号</th>
				<th class="thClass">选项卡名称</th>
				<th class="thClass">选择模板</th>
				<th class="thClass">显示顺序</th>
				<th class="thClass" width="10%">操作</th>
			</tr>
		</thead>
		<c:forEach items="${pageResult.content}" var="tab" varStatus="status">
	        <c:if test="${status.count%2==0}">
               	<tr target="id_column" rel="1" class='event'>
               </c:if>
               <c:if test="${status.count%2!=0}">
               	<tr target="id_column" rel="1">
               </c:if>
	             <td class="tdClass" width="5%"><input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${tab.id}"></td>
	             <td class="tdClass" width="5%"><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
	             <td class="tdClass">${tab.tabName}</td>
	             <td class="tdClass">&nbsp;${tab.templateName}</td>
	             <td class="tdClass">${tab.tabOrder}</td>
				 <td class="tdClass" width="10%">
					&nbsp;
					<a href="#" onclick="loadEditTab('${tab.id}','${op}');"><span>维护</span></a>
					&nbsp;
				</td>
	        </tr>
	    </c:forEach>
		</tbody>	
	</table>
	</form>
