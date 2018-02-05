<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<form id="pagerForm" method="post"
	action="<%=basePath %>/pages/resource/tabulationlistQuery.action?tabulationId=${tabulationId}">
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
	<div class="pagination" targetType="dialog" rel="tabulationQueryListId"
		totalCount="${pageResult.totalCount}"
		numPerPage="${pageResult.pageSize}"
		pageNumShown="${pageResult.pageCountShow}"
		currentPage="${pageResult.currentPage}"></div>
</div>
	    	<form id="tabulationQueryListForm" name="tabulationQueryListForm">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
					<tbody>
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
								所属表
							</th>
							<th class="thClass">
								字段
							</th>
							<th class="thClass">
								字段值
							</th>
							<th class="thClass">
								顺序
							</th>
							<th width="5%" class="thClass">
								操作
							</th>
						</tr>
						</thead>
						<c:forEach items="${pageResult.content}" var="query"
							varStatus="status">
								<c:if test="${status.count%2==0}">
				                	<tr target="id_column" rel="1" class='event'>
				                </c:if>
				                <c:if test="${status.count%2!=0}">
				                	<tr target="id_column" rel="1">
				                </c:if>
								<td width="5%" class="tdClass">
									<input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${query.id}">
								</td>
								<td width="5%" class="tdClass">
									${(pageResult.currentPage-1) * pageResult.pageSize + status.count}
								</td>
								<td class="tdClass">
									${query.tableCNName}
								</td>
								<td class="tdClass">
									${query.columnCNName}
								</td>
								<td class="tdClass">
									${query.columnValue}
								</td>
								<td class="tdClass">
									${query.order}
								</td>
								<td width="5%" class="tdClass">
									<a href="#" onclick="loadEditTabulationQuery('${query.id}','${op}');" style="cursor: hand"><span>维护</span></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>