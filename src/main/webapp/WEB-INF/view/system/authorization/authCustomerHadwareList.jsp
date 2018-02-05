<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	
</script>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/authCustomerHadware/list.action">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="authCustomerId" value="${authCustomerId}" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
</form>
<div class="panelBar">
	<div class="pages">
		<span>显示${pageResult.pageSize}条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="subListDiv" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<form id="subListForm" name="subListForm" method="post">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
			<thead>
				<tr>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">
						<input name="all2" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'selectedSubIDs')"/>
					</th>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">序号</th>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">Mac地址</th>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">机器码</th>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">操作</th>
				</tr>
			</thead>
            <c:forEach items="${pageResult.content}" var="authCustomerHadware" varStatus="status">
                <c:if test="${status.count%2==0}">
                	<tr target="id_column" rel="1" class='event'>
                </c:if>
                <c:if test="${status.count%2!=0}">
                	<tr target="id_column" rel="1">
                </c:if>
                   	<td class="tdClass" style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
                   		<input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${authCustomerHadware.id}">
                   	</td>
                    <td style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;" class="tdClass">
                    	${(pageResult.currentPage-1) * pageResult.pageSize + status.count}
                    </td>
                    <td class="tbClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
                    	${authCustomerHadware.mac}
                    </td>
                    <td class="tbClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
                    	${authCustomerHadware.computerkey}
                    </td>
					<td class="tdClass" style="width: 10%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
						 &nbsp;
						 <a onclick="ns.system.loadSubEdit('${authCustomerHadware.id}')" style="cursor: point;"><span>维护</span></a>
						 &nbsp;
					</td>
                </tr>
            </c:forEach>
		</tbody>	
	</table>
</form>
