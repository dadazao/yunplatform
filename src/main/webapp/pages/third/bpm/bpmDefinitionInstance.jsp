<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
    
<div id="defaultQuery" class="pageHeader">
	<div class="searchBar">
		<form id="defaultQueryForm" onsubmit="return divSearch(this,'instancePage');" action="<%=basePath %>/pages/third/bpm/processRun/history.action" method="post">
			<table class="searchContent">
				<tr>
					<td>
						<span class="label">流程实例标题&nbsp;</span><input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/>&nbsp;&nbsp;
						<span class="label">创建时间从&nbsp;</span> <input  type="text" name="Q_begincreatetime_DL"  class="inputText" value="${param['Q_begincreatetime_DL']}"/>&nbsp;&nbsp;
						<span class="label">至&nbsp;</span><input  type="text" name="Q_endcreatetime_DG" class="inputText" value="${param['Q_endcreatetime_DG']}">&nbsp;&nbsp;
						<span class="label">状态&nbsp;</span>
						<select name="Q_status_SN" value="${param['Q_status_SN']}">
							<option value="">所有</option>
							<option value="1" <c:if test="${param['Q_status_SN'] == 1}">selected</c:if>>正在运行</option>
							<option value="2" <c:if test="${param['Q_status_SN'] == 2}">selected</c:if>>结束</option>
						</select>&nbsp;&nbsp;
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
	<div class="pagination" targetType="navTab" rel="instancePage" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<form id="tableForm" name="tableForm" method="post">
	<div>
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'selectedIDs')"/></th>
					<th align="center" width="5%" >序号</th>
					<th align="center" width="25%">流程实例标题</th>
					<th align="center">创建人</th>
					<th align="center">创建时间</th>
					<th align="center">结束时间</th>
					<th align="center">持续时间</th>
					<th align="center">状态</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="processRunItem" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${processRunItem.runId}"/></td>
	                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    	<td align="center">${processRunItem.subject}</td>
	                    	<td align="center">${processRunItem.creator}</td>
	                    	<td align="center"><fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                    	<td align="center"><fmt:formatDate value="${processRunItem.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>			                    	
	                    	<td align="center">${processRunItem.duration}</td>
	                    	<td align="center">
	                    		<c:choose>
									<c:when test="${processRunItem.status==1}">
										<span style="color: green;height:26px;line-height:26px;">正在运行</span>
									</c:when>
									<c:when test="${processRunItem.status==2}">
										<span style="color: red;height:26px;line-height:26px;">结束</span>
									</c:when>
									<c:when test="${processRunItem.status==3}">
										<span style="color: brown;height:26px;line-height:26px;">人工结束</span>
									</c:when>
								</c:choose>
							</td>
	                    </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</div>
</form>


