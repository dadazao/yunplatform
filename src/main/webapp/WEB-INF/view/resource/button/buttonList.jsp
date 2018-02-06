<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
	String searchAction = basePath + "/pages/resource/button/list.action";
	String modifyAction = basePath + "/pages/resource/button/view.action";
%>
<html>
<head>
<script type="text/javascript">
	$(function() {
		//新建ACTION URL
		xjUrl = "<%=basePath%>/pages/resource/button/add.action?formId=${formId}&model=${model}";
		//批量删除ACTION URL
		plscUrl = "<%=basePath%>/pages/resource/button/batchDelete.action?model=${model}";
	});
		
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=searchAction %>">
		<input type=hidden name="model" value="${model}"/>
		<input type="hidden" name="status" value="${param.status}">
		<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce">
			<c:if test="${ce.formColumn.isDefaultQuery == 1}">
				<input type="hidden" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
			</c:if>
		</c:forEach>
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<input type="hidden" name="seqcode" value="${seqcode}"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<c:forEach items="${tabulationButtons}" var="tabulationButton" varStatus="status">
					<c:if test="${tabulationButton.buttonType == '1'}">
						<c:forEach items="${tabulationButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
							<button name="b${status.count*stat.count}" class="listbutton" onclick="event${buttonAndGroup.button.buttonBM}();">${buttonAndGroup.button.buttonName}</button>&nbsp;
						</c:forEach>
					</c:if>
					<c:if test="${tabulationButton.buttonType == '0'}">
						<button name="b${status.count}" class="listbutton" onclick="event${tabulationButton.button.buttonBM}();">${tabulationButton.button.buttonName}</button>&nbsp;	
					</c:if>
				</c:forEach>
			</td></tr></table>
		</div>
	</div>
	<div class="pageHeader">
		<div class="searchBar">
			<form onsubmit="return navTabSearch(this);" action="<%=searchAction %>" method="post">
				<table class="searchContent">
					<tr>
						<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce">
							<c:if test="${ce.formColumn.isDefaultQuery == 1}">
								<td>
									${ce.formColumn.columnZhName}
								</td>
								<td>
									<input type="text" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
								</td>
							</c:if>
						</c:forEach>
						<td>
							<input type="hidden" name="listId" value="${listId}"/>
							<input type="hidden" name="seqcode" value="${seqcode}"/>
							<button type="submit" class="listbutton">查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<!-- 
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${pageResult.pageSize==20 }">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50 }">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100 }">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200 }">selected="selected"</c:if>>200</option>
			</select>
			 -->
			<input type="hidden" name="numPerPage" value="20">
			<span>20条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<c:if test="${tabulation.isSelect == '1'}">
						<th width="5%" align="center">
							<input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/>
						</th>
					</c:if>
					<th width="5%" align="center">序号</th>
					<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce">
						<c:if test="${ce.formColumn.isShowInList==1}">
							<th align="center">${ce.formColumn.columnZhName}</th>
						</c:if>
					</c:forEach>
					<c:if test="${tabulation.isModify == '1'}">
						<th width="10%" align="center">操作</th>
					</c:if>						
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="dom" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                	<c:if test="${tabulation.isSelect == '1'}">
	                    	<td align="center">
	                    		<input type="checkbox" class="checkbox"  name="selectedIDs" value="<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == 'id'}">${ce.value}</c:if></c:forEach>">
	                    	</td>
	                    </c:if>
	                    <td>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    
	                    <c:forEach items="${dom.tabulationColumnExtends}" var="ce" >
	                    	<c:if test="${ce.formColumn.columnName != 'id'}">
	                    		<c:if test="${ce.formColumn.isShowInList==1}">
									<td >
										<c:choose>
											<c:when test="${ce.formColumn.inputType == '1'}">
												<c:forEach items="${ce.codes}" var="code">
													<c:if test="${code.value==ce.value}">
														${code.text}
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise>
												${ce.value}
											</c:otherwise>
										</c:choose>
									</td>
								</c:if>
							</c:if>
	                    </c:forEach>
	                    <c:if test="${tabulation.isModify == '1'}">
							<td align="center">
								&nbsp;
								<a target="dialog" mask="true" width="1024" height="768" href="<%=modifyAction %>?model=${model}&formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == 'id'}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>" target="navTab"><span>${tabulation.modifyName}</span></a>
								&nbsp;
							</td>
						</c:if>
	                </tr>
	            </c:forEach>
			</tbody>
		</table>
	</form>
</body>
</html>
