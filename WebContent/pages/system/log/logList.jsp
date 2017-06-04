<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
<!--
	
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/syslogquerylog.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="year" value="${year}"/>
		<input type="hidden" name="month" value="${month}"/>
	</form>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/system/syslogquerylog.action" method="post">
				<table class="searchContent">
					<tr>
						<td>年份</td>
						<td class="queryTd">
							<input type="text" name="year" value="${year}" maxlength="4" class="textInput number" style="width:80px;"/>
						</td>
						<td>月份</td>
						<td class="queryTd">
							<select name="month">
								<option value="">请选择</option>
								<option value="01" <c:if test="${month=='01'}">selected="selected"</c:if>>一月</option>
								<option value="02" <c:if test="${month=='02'}">selected="selected"</c:if>>二月</option>
								<option value="03" <c:if test="${month=='03'}">selected="selected"</c:if>>三月</option>
								<option value="04" <c:if test="${month=='04'}">selected="selected"</c:if>>四月</option>
								<option value="05" <c:if test="${month=='05'}">selected="selected"</c:if>>五月</option>
								<option value="06" <c:if test="${month=='06'}">selected="selected"</c:if>>六月</option>
								<option value="07" <c:if test="${month=='07'}">selected="selected"</c:if>>七月</option>
								<option value="08" <c:if test="${month=='08'}">selected="selected"</c:if>>八月</option>
								<option value="09" <c:if test="${month=='09'}">selected="selected"</c:if>>九月</option>
								<option value="10" <c:if test="${month=='10'}">selected="selected"</c:if>>十月</option>
								<option value="11" <c:if test="${month=='11'}">selected="selected"</c:if>>十一月</option>
								<option value="12" <c:if test="${month=='12'}">selected="selected"</c:if>>十二月</option>
							</select>
						</td>
						<td>操作人</td>
						<td class="queryTd">
							<input type="text" name="sysLog.operatorName" value="${sysLog.operatorName}" maxlength="50" class="textInput number" style="width:120px;"/>
						</td>
						<td>IP地址</td>
						<td class="queryTd">
							<input type="text" name="sysLog.ip" value="${sysLog.ip}" maxlength="50" class="textInput number" style="width:120px;"/>
						</td>
						<td>操作内容</td>
						<td class="queryTd">
							<input type="text" name="sysLog.operationContent" value="${sysLog.operationContent}" maxlength="50" class="textInput number" style="width:120px;"/>
						</td>
						<td>
							<button type="button" class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'documentIDs')"/></th>
						<th align="center" width="5%">序号</th>
						<th align="center">操作人</th>
						<th align="center">IP地址</th>
						<th align="center">操作内容</th>
						<th align="center">操作时间</th>
					</tr>
				</thead>
				<tbody>
	            <c:forEach items="${pageResult.content}" var="sysLog" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
                    	<td align="center"><input type="checkbox" class="checkbox"  name="documentIDs" value="${sysLog.id}"/></td>
						<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
						<td align="center">${sysLog.operatorName}</td>
						<td align="center">${sysLog.ip}</td>
						<td align="center">${sysLog.operationContent}</td>
						<td align="center"><fmt:formatDate value="${sysLog.operationTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
	            </c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>