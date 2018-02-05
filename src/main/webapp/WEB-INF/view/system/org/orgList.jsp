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
	ns.org.orgAdd = function(){
		$.pdialog.open("<%=basePath %>/pages/system/org/orgadd.action?parentId=${sysOrg.parentId}","newDialog","新建机构",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.org.orgView = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/org/orgview.action?orgId="+id,"viewDialog","查看机构",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.org.setuserList = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/org/orguserList.action?orgId="+id+"&forward=setUserList","viewDialog","人员设置",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.org.orgDelete = function(){
		var url = "<%=basePath %>/pages/system/org/orgdelete.action";
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
	  		url:'<%=basePath %>/pages/system/org/orgisCanDelete.action?'+param,
	  		success:function(data){
				candelete=data;
	  		}
	  	});
	  	if(candelete=="false"){
	  		alertMsg.warn("选中的机构中存在子机构，请重新选择后再试！");
			return false;
	  	}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,ns.org.refreshIndex);}});
	}
	
	ns.org.refreshIndex = function(json) {
		if(json.statusCode == '200') {
			var treeObj = $.fn.zTree.getZTreeObj("orgShowTree");
			var ids = $("#tableForm").serializeArray();
			$.each(ids, function(i, id){
				var node = treeObj.getNodeByParam("id", id.value, null);
				treeObj.removeNode(node);
			});
		}
		refreshList(json);
	}
	
	ns.org.loadUserTabList = function(){
		var urlString = "<%=basePath %>/pages/system/org/orguserList.action?orgId=" + $("#orgId").val();
		$.ajaxSetup({async: false});
		$('#userTabList').load(urlString);
		initPagination();
		$.ajaxSetup({async: true});
	}
	
	ns.org.loadSetUserTabList = function(orgId){
		var urlString = "<%=basePath %>/pages/system/org/orguserList.action?orgId="+orgId+"&forward=setUserList";
		$.ajaxSetup({async: false});
		$('#userTabList').load(urlString);
		initPagination();
		$.ajaxSetup({async: true});
	}
	
	ns.org.deleteUser = function(){
		var items = $("input[type='checkbox']:checked",$.pdialog.getCurrent()).length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		var param = $("#puserListForm").serialize();
		var urlString = "<%=basePath %>/pages/system/org/orgdeleteUser.action?" + param;
		alertMsg.confirm("确定要删除吗?", {okCall:function(){
			$.ajax({
				url : urlString,
				type: 'post',
				dataType : "json",
				success : function(data) {
					alertMsg.info("操作成功!");
					dialogRefresh();
				}
			});
		}});
	}

	ns.org.setupMainOrg = function(id){
		var urlString = "<%=basePath %>/pages/system/org/orgsetupMainOrg.action?userOrgId="+id;
		$.ajax({
			url : urlString,
			type: 'post',
			dataType : "json",
			success : function(data) {
				alertMsg.info("操作成功!");
				dialogRefresh();
			}
		});
	}
	
	ns.org.setupPrincipal = function(id){
		var urlString = "<%=basePath %>/pages/system/org/orgsetupPrincipal.action?userOrgId="+id;
		$.ajax({
			url : urlString,
			type: 'post',
			dataType : "json",
			success : function(data) {
				alertMsg.info("操作成功!");
				dialogRefresh();
			}
		});
	}
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/org/orglist.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="sysOrg.parentId" value="${sysOrg.parentId}"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<button type="button" class="listbutton" onclick="ns.org.orgAdd();">新建</button>
				<button type="button" class="listbutton" onclick="ns.org.orgDelete();">删除</button>
			</td></tr></table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader" > 
		<div class="searchBar" style="height:40px;">
			<form onsubmit="return divSearch(this,'listId');" action="<%=basePath %>/pages/system/org/orglist.action" method="post">
				<table class="searchContent">
					<tr>
						<td>
							部门名称
						</td>
						<td class="queryTd">
							<input class="queryInput" type="text" name="sysOrg.orgName" value="${sysOrg.orgName}"/>
						</td>
						<td>
							<input type="hidden" name="sysOrg.parentId" value="${sysOrg.parentId}"/>
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
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'listId')">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" rel="listId" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<table class="table" width="100%">
			<thead>
				<tr>
					<th width="5%" align="center"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/></th>
					<th width="5%" align="center">序号</th>
					<th width="25%" align="center">部门名称</th>
					<th width="20%" align="center">上级部门</th>
					<th width="30%" align="center">部门职能</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="org" varStatus="status">
	            	<c:if test="${org.id!='1'}">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
			                    <td align="center" width="5%">
			                    	<c:if test="${org.id!='1'}">
										<input type="checkbox" class="checkbox"  name="selectedIDs" value="${org.id}">
									</c:if>
			                    </td>
			                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
			                    <td>${org.orgName}</td>
			                    <td>${org.parentName}</td>
			                    <td>${org.orgFunction}</td>
			                    <td>
									<a style="cursor: pointer;color: blue;" onclick="ns.org.orgView('${org.id}')">维护</a>&nbsp;&nbsp;
									<a style="cursor: pointer;color: blue;" onclick="ns.org.setuserList('${org.id}')">人员设置</a>
			                    </td>
			                </tr>
		                </c:if>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>