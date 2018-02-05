<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	ns.user.userAdd = function(){
		$.pdialog.open("<%=basePath %>/pages/system/user/useradd.action","newDialog","新建用户",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.user.userView = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/user/userview.action?userId="+id,"viewDialog","查看用户",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.user.resetPassword = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/user/userinitResetPassword.action?userId="+id,"resetPasswordDialog","重置密码",{width:350,height:150,mask:true,resizable:true});
	}
	
	ns.user.resetStatus = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/user/userinitResetStatus.action?userId="+id,"resetStatusDialog","设置状态",{width:350,height:150,mask:true,resizable:true});
	}
	
	ns.user.userDelete = function(){
		var url = "<%=basePath %>/pages/system/user/userdelete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.user.loadOrgTabList = function(){
		var urlString = "<%=basePath %>/pages/system/user/selectOrg.jsp";
		$('#orgTabList').loadUrl(urlString);
	}
	
	ns.user.loadPositionTabList = function(){
		var urlString = "<%=basePath %>/pages/system/user/selectPosition.jsp";
		$('#positionTabList').loadUrl(urlString);
	}
	
	ns.user.loadRoleTabList = function(){
		var urlString = "<%=basePath %>/pages/system/user/selectRole.jsp";
		$('#roleTabList').loadUrl(urlString);
	}
	
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/user/userlist.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<button type="button" class="listbutton" onclick="ns.user.userAdd();">新建</button>
				<button type="button" class="listbutton" onclick="ns.user.userDelete();">删除</button>
			</td></tr></table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader" > 
		<div class="searchBar" style="height:40px;">
			<form onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/system/user/userlist.action" method="post">
				<table class="searchContent">
					<tr>
						<td>
							姓名
						</td>
						<td class="queryTd">
							<input class="queryInput" type="text" name="sysUser.fullname" value="${sysUser.fullname}"/>
						</td>
						<td>
							<button type="button" class="listbutton" onclick="query();">查询</button>
							<div style="display: none;"><button id="querySubmit" type="submit" >查询</button></div>
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
		<table class="table" width="100%">
			<thead>
				<tr>
					<th width="5%" align="center"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/></th>
					<th width="5%" align="center">序号</th>
					<th align="center">姓名</th>
					<th align="center">账号</th>
					<th align="center">创建时间</th>
					<th align="center">是否过期</th>
					<th align="center">是否锁定</th>
					<th align="center">状态</th>
					<th width="15%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="user" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
		                    <td align="center" width="5%"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${user.id}"></td>
		                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    <td>${user.fullname}</td>
		                    <td>${user.username}</td>
		                    <td>
		                    	<fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		                    </td>
		                    <td>
		                    	<c:if test="${user.overdue==1}">已过期</c:if>
		                    	<c:if test="${user.overdue==0}">未过期</c:if>
		                    </td>
		                    <td>
		                    	<c:if test="${user.lock==1}">已锁定</c:if>
		                    	<c:if test="${user.lock==0}">未锁定</c:if>
		                    </td>
		                    <td>
		                    	<c:if test="${user.active==1}">未激活</c:if>
		                    	<c:if test="${user.active==0}">激活</c:if>
		                    </td>
		                    <td>
								<a style="cursor: pointer;color: blue;" onclick="ns.user.userView('${user.id}')">维护</a>&nbsp;
								<a style="cursor: pointer;color: blue;" onclick="ns.user.resetPassword('${user.id}')">重置密码</a>&nbsp;
								<a style="cursor: pointer;color: blue;" onclick="ns.user.resetStatus('${user.id}')">设置状态</a>&nbsp;
		                    </td>
		                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>