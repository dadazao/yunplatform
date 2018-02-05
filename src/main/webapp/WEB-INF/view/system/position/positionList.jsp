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
	ns.position.positionAdd = function(){
		$.pdialog.open("<%=basePath %>/pages/system/position/positionadd.action?parentId=${position.parentId}","newDialog","新建岗位",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.position.positionView = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/position/positionview.action?positionId="+id,"viewDialog","查看岗位",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.position.setuserList = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/position/positionuserList.action?positionId="+id+"&forward=setUserList","viewDialog","人员设置",{width:950,height:650,mask:true,resizable:true});
	}

	ns.position.positionDelete = function(){
		var url = "<%=basePath %>/pages/system/position/positiondelete.action";
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
	  		url:'<%=basePath %>/pages/system/position/positionisCanDelete.action?'+param,
	  		success:function(data){
				candelete=data;
	  		}
	  	});
	  	if(candelete=="false"){
	  		alertMsg.warn("选中的岗位中存在子岗位，请重新选择后再试！");
			return false;
	  	}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,ns.position.refreshIndex);}});
	}
	
	ns.position.refreshIndex = function(json) {
		if(json.statusCode == '200') {
			var treeObj = $.fn.zTree.getZTreeObj("positionShowTree");
			var ids = $("#tableForm").serializeArray();
			$.each(ids, function(i, id){
				var node = treeObj.getNodeByParam("id", id.value, null);
				treeObj.removeNode(node);
			});
		}
		refreshList(json);
	}
	
	ns.position.loadUserTabList = function(){
		var urlString = "<%=basePath %>/pages/system/position/positionuserList.action?positionId=" + $("#positionId").val();
		$.ajaxSetup({async: false});
		$('#userTabList').load(urlString);
		initPagination();
		$.ajaxSetup({async: true});
	}
	
	ns.position.loadSetUserTabList = function(positionId){
		var urlString = "<%=basePath %>/pages/system/position/positionuserList.action?positionId="+positionId+"&forward=setUserList";
		$.ajaxSetup({async: false});
		$('#userTabList').load(urlString);
		initPagination();
		$.ajaxSetup({async: true});
	}
	
	ns.position.deleteUser = function(){
		var items = $("input[type='checkbox']:checked",$.pdialog.getCurrent()).length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		var param = $("#puserListForm").serialize();
		var urlString = "<%=basePath %>/pages/system/position/positiondeleteUser.action?" + param;
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
	
	ns.position.shezhiZhugang = function(id){
		var urlString = "<%=basePath %>/pages/system/position/positionshezhiZhugang.action?userPositionId="+id;
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
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/position/positionlist.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="position.parentId" value="${position.parentId}"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<button type="button" class="listbutton" onclick="ns.position.positionAdd();">新建</button>
				<button type="button" class="listbutton" onclick="ns.position.positionDelete();">删除</button>
			</td></tr></table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader" > 
		<div class="searchBar" style="height:40px;">
			<form onsubmit="return divSearch(this,'listId');" action="<%=basePath %>/pages/system/position/positionlist.action" method="post">
				<table class="searchContent">
					<tr>
						<td>
							岗位名称
						</td>
						<td class="queryTd">
							<input class="queryInput" type="text" name="position.positionName" value="${position.positionName}"/>
						</td>
						<td>
							岗位编号
						</td>
						<td class="queryTd">
							<input class="queryInput" type="text" name="position.positionNo" value="${position.positionNo}"/>
						</td>
						<td>
							<input type="hidden" name="position.parentId" value="${position.parentId}"/>
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
					<th width="30%" align="center">岗位名称</th>
					<th width="20%" align="center">岗位编号</th>
					<th width="30%" align="center">岗位说明</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="position" varStatus="status">
	            	<c:if test="${position.id!='1'}">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
			                    <td align="center" width="5%">
			                    	<c:if test="${position.id!='1'}">
			                    		<input type="checkbox" class="checkbox"  name="selectedIDs" value="${position.id}">
			                    	</c:if>
			                    </td>
			                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
			                    <td>${position.positionName}</td>
			                    <td>${position.positionNo}</td>
			                    <td>${position.comment}</td>
			                    <td>
		                    		<a style="cursor: pointer;color: blue;" onclick="ns.position.positionView('${position.id}')">维护</a>&nbsp;&nbsp;
		                    		<a style="cursor: pointer;color: blue;" onclick="ns.position.setuserList('${position.id}')">人员设置</a>
			                    </td>
			                </tr>
		              </c:if>
	            </c:forEach>
			</tbody>
		</table>
	</form>
</body>
</html>