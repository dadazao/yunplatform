<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	ns.role.roleAdd = function(){
		$.pdialog.open("<%=basePath %>/pages/system/role/roleadd.action","newDialog","新建角色",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.role.roleView = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/role/roleview.action?roleId="+id,"viewDialog","查看角色",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.role.copyRole = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/role/roleinitCopy.action?roleId="+id,"copyDialog","复制角色",{width:550,height:150,mask:true,resizable:true});
	}
	
	ns.role.assignAuth = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/role/assignAuth.jsp?roleId="+id,"assignAuthDialog","权限分配",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.role.roleDelete = function(){
		var url = "<%=basePath %>/pages/system/role/roledelete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		var candelete="true";
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		url:'<%=basePath %>/pages/system/role/roleisCanDelete.action?'+param,
	  		success:function(data){
				candelete=data;
	  		}
	  	});
	  	if(candelete=="false"){
	  		alertMsg.warn("选中的部分角色不允许删除，请重新选择后再试！");
			return false;
	  	}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.role.loadUserTabList = function(){
		var urlString = "<%=basePath %>/pages/system/role/roleuserList.action?roleId=" + $("#roleId").val();
		$.ajaxSetup({async: false});
		$('#userTabList').load(urlString);
		initPagination();
		$.ajaxSetup({async: true});
	}
	
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/role/rolelist.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<button type="button" class="listbutton" onclick="ns.role.roleAdd();">新建</button>
				<button type="button" class="listbutton" onclick="ns.role.roleDelete();">删除</button>
			</td></tr></table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader" > 
		<div class="searchBar" style="height:40px;">
			<form onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/system/role/rolelist.action" method="post">
				<table class="searchContent">
					<tr>
						<td>
							角色名
						</td>
						<td class="queryTd">
							<input class="queryInput" type="text" name="sysRole.roleName" value="${sysRole.roleName}"/>
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
					<th width="30%" align="center">角色名</th>
					<th width="35%" align="center">角色说明</th>
					<th align="center">允许删除</th>
					<th align="center">允许编辑</th>
					<th align="center">是否启用</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="role" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
		                    <td align="center" width="5%"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${role.id}"></td>
		                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    <td>${role.roleName}</td>
		                    <td>${role.comment}</td>
		                    <td>
		                    	<c:if test="${role.canDelete==1}">允许</c:if>
		                    	<c:if test="${role.canDelete==0}">不允许</c:if>
		                    </td>
		                    <td>
		                    	<c:if test="${role.canEdit==1}">允许</c:if>
		                    	<c:if test="${role.canEdit==0}">不允许</c:if>
		                    </td>
		                    <td>
		                    	<c:if test="${role.enable==1}">启用</c:if>
		                    	<c:if test="${role.enable==0}">禁用</c:if>
		                    </td>
		                    <td>
								<a style="cursor: pointer;color: blue;" onclick="ns.role.roleView('${role.id}')">维护</a>&nbsp;
								<a style="cursor: pointer;color: blue;" onclick="ns.role.copyRole('${role.id}')">复制角色</a>&nbsp;
								<a style="cursor: pointer;color: blue;" onclick="ns.role.assignAuth('${role.id}')">权限分配</a>&nbsp;
		                    </td>
		                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>