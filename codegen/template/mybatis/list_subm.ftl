<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	
</script>
<form id="pagerForm" method="post" action="<%=basePath %>/${mainTable.namespace}/${mainTable.variables.class?uncap_first}/sublist.action">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${'${'}pageResult.pageSize${'}'}" />
	<input type="hidden" name="${mainTable.parentClassName?uncap_first}Id" value="${'${'}${mainTable.parentClassName?uncap_first}Id${'}'}" />
</form>
<div class="panelBar">
	<div class="pages">
		<#noparse><span>显示${pageResult.pageSize}条，共${pageResult.totalCount}条</span></#noparse>
	</div>
	<div class="pagination" targetType="dialog" rel="${mainTable.variables.class?uncap_first}SubListDiv" <#noparse>totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div></#noparse>
</div>
<form id="${mainTable.variables.class?uncap_first}SubListForm" name="${mainTable.variables.class?uncap_first}SubListForm" method="post">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
			<thead>
				<tr>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">
						<input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'${mainTable.variables.class?uncap_first}IDs')"/>
					</th>
					<th class="thClass">序号</th>
					<#list mainTable.listColumns as col>
						<#if col.inList==true>
					<th align="center" class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">${col.column.chName}</th>
						</#if>
					</#list>
					<th class="thClass">操作</th>
				</tr>
			</thead>
			<c:forEach items="${'${'}pageResult.content${'}'}" var="${mainTable.variables.class?uncap_first}" varStatus="status"><#noparse>
				<c:if test="${status.count%2==0}">
                	<tr target="id_column" rel="1" class='event'>
                </c:if>
                <c:if test="${status.count%2!=0}">
                	<tr target="id_column" rel="1">
                </c:if></#noparse>
                   	<td class="tdClass" style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
                   		<input type="checkbox" class="checkbox"  name="${mainTable.variables.class?uncap_first}IDs" value="${'${'}${mainTable.variables.class?uncap_first}.id${'}'}">
                   	</td><#noparse>
                    <td style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;" class="tdClass">
                    	${(pageResult.currentPage-1) * pageResult.pageSize + status.count}
                    </td></#noparse>
					<#list mainTable.listColumns as col>
							<#if col.inList>
								<#if col.inputType==4>
						<td class="tdClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;"><fmt:formatDate value="${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<#else>
						<td class="tdClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;">${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}</td>
								</#if>
							</#if>
					</#list>
                    <td class="tdClass" style="width: 10%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
						 &nbsp;
						 <a onclick="ns.${mainTable.parentClassName?uncap_first}.loadSubEdit('<#noparse>${</#noparse>${mainTable.variables.class?uncap_first}.id<#noparse>}</#noparse>')" style="cursor: hand"><span>维护</span></a>
						 &nbsp;
					</td>
                </tr>
            </c:forEach>
		</tbody>	
	</table>
</form>
