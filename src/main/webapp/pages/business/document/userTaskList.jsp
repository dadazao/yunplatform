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
	//新建实体
	ns.document.newFawenxinxi = function(){
		$.pdialog.open("<%=basePath%>/pages/business/document/add.action","newDocumentDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}

	ns.document.editFlow = function(taskId){
		$.pdialog.open("<%=basePath%>/pages/business/document/editFlow.action?taskId="+taskId,"editFlowDialog","办理",{width:950,height:650,mask:true,resizable:true});
	}

	$(function() {		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath%>/pages/business/document/userTaskList.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />		
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newCar" name="newCar" class="listbutton" onclick="">办理</button>
					<button type="button" id="newCar" name="newCar" class="listbutton" onclick="ns.document.newFawenxinxi()">发文</button>
				</td></tr>
			</table>
		</div>
	</div>
<%--	<div class="panelBar">--%>
<%--		<div class="pages">--%>
<%--			<span>显示</span>--%>
<%--			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">--%>
<%--				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>--%>
<%--				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>--%>
<%--				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>--%>
<%--				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>--%>
<%--			</select>--%>
<%--			<span>条，共${pageResult.totalCount}条</span>--%>
<%--		</div>--%>
<%--		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>--%>
<%--	</div>--%>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'selectedIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center">任务名称</th>
						<th align="center" width="10%">创建时间</th>
						<th align="center" width="10%" >操作</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${userTasks}" var="task" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${task.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${task.name}</td>
		                    	<td align="center"><fmt:formatDate value="${task.createTime}" pattern="yyyy-MM-dd"/></td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.document.editFlow('${task.id}');">办理</a>&nbsp;
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>