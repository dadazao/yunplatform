<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<form id="pagerForm" method="post"
	action="<%=basePath %>/pages/resource/tabulationlistOpt.action?tabulationId=${tabulationId}">
	<input type=hidden name="model" value="${model}" />
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
	<div class="pagination" targetType="dialog" rel="tabulationOptListId"
		totalCount="${pageResult.totalCount}"
		numPerPage="${pageResult.pageSize}"
		pageNumShown="${pageResult.pageCountShow}"
		currentPage="${pageResult.currentPage}"></div>
</div>
	<input type="hidden" id="tabulationOptOMC" value="${operationManage.optManagerCount}"/>
   	<form id="tabulationOptListForm" name="tabulationOptListForm">
		<table id="tableOptOMC" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
			<thead>
				<tr>
					<th width="5%" class="thClass">
						<input name="all" type="checkbox" class="checkbox" 
							onclick="selectAll(this,'selectedSubIDs')" />
					</th>
					<th width="5%" class="thClass">
						序号
					</th>
					<th class="thClass">
						操作按钮
					</th>
					<th class="thClass">
						功能说明
					</th>
					<th class="thClass">
						顺序
					</th>
					<th width="5%" class="thClass">
						操作
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageResult.content}" var="opt"
					varStatus="status">
						<c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
						<td width="5%" class="tdClass">
							<input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${opt.id}">
						</td>
						<td width="5%" class="tdClass">
							${(pageResult.currentPage-1) * pageResult.pageSize + status.count}
						</td>
						<td class="tdClass">
							${opt.showName}
						</td>
						<td class="tdClass">
							${opt.fcomment}
						</td>
						<td class="tdClass">
							${opt.order}
						</td>
						<td width="5%" class="tdClass">
							<a href="#" onclick="loadEditTabulationOpt('${opt.id}','${op}');" style="cursor: hand"><span>维护</span></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
