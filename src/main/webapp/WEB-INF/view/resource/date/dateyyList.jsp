<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.cloudstong.platform.core.common.PageResult"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
	<form id="pagerForm" method="post" action='<%=basePath %>/pages/resource/date/formList.action?dateId=${dateId}'>
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
			<span>20条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" rel="formList" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
<div class="grid">
	<div class="gridHeader">
		<table width="100%">
			<thead>
				<tr>
					<th width="5%" align="center">序号</th>
					<th align="center">业务名称</th>
					<th align="center">业务编码</th>
					<th align="center">所属目录</th>
					<th align="center">应用时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="gridScroller">
	    <div class="gridTbody">
	    	<form id="dateyyListForm" name="dateyyListForm">
		    <table width="100%">
				<tbody>
				<c:forEach items="${pageResult.content}" var="form" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    <td>${form.formName}</td>
	                    <td>${form.code}</td>
	                    <td></td>
	                    <td>${form.createDate}</td>
	                </tr>
	            </c:forEach>
				</tbody>	
			</table>
			</form>
		</div>
	</div>
</div>