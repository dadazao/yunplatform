<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	
</script>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/business/employee/project/sublist.action">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	<input type="hidden" name="employeeId" value="${employeeId}" />
</form>
<div class="panelBar">
	<div class="pages">
		<span>显示${pageResult.pageSize}条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="projectSubListDiv" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<form id="projectSubListForm" name="projectSubListForm" method="post">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
			<thead>
				<tr>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">
						<input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'projectIDs')"/>
					</th>
					<th class="thClass">序号</th>
					<th align="center" class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">项目名称</th>
					<th align="center" class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">项目金额</th>
					<th class="thClass">操作</th>
				</tr>
			</thead>
			<c:forEach items="${pageResult.content}" var="project" varStatus="status">
				<c:if test="${status.count%2==0}">
                	<tr target="id_column" rel="1" class='event'>
                </c:if>
                <c:if test="${status.count%2!=0}">
                	<tr target="id_column" rel="1">
                </c:if>
                   	<td class="tdClass" style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
                   		<input type="checkbox" class="checkbox"  name="projectIDs" value="${project.id}">
                   	</td>
                    <td style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;" class="tdClass">
                    	${(pageResult.currentPage-1) * pageResult.pageSize + status.count}
                    </td>
						<td class="tdClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;">${project.name}</td>
						<td class="tdClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;">${project.money}</td>
                    <td class="tdClass" style="width: 10%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
						 &nbsp;
						 <a onclick="ns.project.loadSubEdit('${project.id}')" style="cursor: hand"><span>维护</span></a>
						 &nbsp;
					</td>
                </tr>
            </c:forEach>
		</tbody>	
	</table>
</form>
