<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	
</script>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/business/product/pingjia/sublist.action">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	<input type="hidden" name="productId" value="${productId}" />
</form>
<div class="panelBar">
	<div class="pages">
		<span>显示${pageResult.pageSize}条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="pingjiaSubListDiv" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<form id="pingjiaSubListForm" name="pingjiaSubListForm" method="post">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
			<thead>
				<tr>
					<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">
						<input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'pingjiaIDs')"/>
					</th>
					<th class="thClass">序号</th>
					<th align="center" class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">分数</th>
					<th align="center" class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">详细描述</th>
					<th class="thClass">操作</th>
				</tr>
			</thead>
			<c:forEach items="${pageResult.content}" var="pingjia" varStatus="status">
				<c:if test="${status.count%2==0}">
                	<tr target="id_column" rel="1" class='event'>
                </c:if>
                <c:if test="${status.count%2!=0}">
                	<tr target="id_column" rel="1">
                </c:if>
                   	<td class="tdClass" style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
                   		<input type="checkbox" class="checkbox"  name="pingjiaIDs" value="${pingjia.id}">
                   	</td>
                    <td style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;" class="tdClass">
                    	${(pageResult.currentPage-1) * pageResult.pageSize + status.count}
                    </td>
						<td class="tdClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;">${pingjia.scorce}</td>
						<td class="tdClass" style="border-bottom:1px #ededed solid; border-right:1px #ededed solid;">${pingjia.xxms}</td>
                    <td class="tdClass" style="width: 10%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
						 &nbsp;
						 <a onclick="ns.product.loadSubEdit('${pingjia.id}')" style="cursor: hand"><span>维护</span></a>
						 &nbsp;
					</td>
                </tr>
            </c:forEach>
		</tbody>	
	</table>
</form>
