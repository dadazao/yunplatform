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
	ns.desktop.newDesktopItem = function(){
		$.pdialog.open("<%=basePath%>/pages/platform/desktop/desktopItem/add.action","newDesktopItemDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.desktop.deleteDesktopItem = function(id){
		var url = '';
		if(id){
			url = "<%=basePath%>/pages/platform/desktop/desktopItem/delete.action?desktopItemIDs="+id;
		}else{
			var items = $("input[type='checkbox']:checked").length;
			if(items == 0){
				alertMsg.warn("请选择要删除的数据!");
				return;
			}
			url = "<%=basePath%>/pages/platform/desktop/desktopItem/delete.action?"+$("#tableForm").serialize();
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.desktop.viewDesktopItem = function(id) {
		$.pdialog.open("<%=basePath%>/pages/platform/desktop/desktopItem/view.action?id=" + id,"viewDesktopItemDialog","明细",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.desktop.editDesktopItem = function(id){
		$.pdialog.closeCurrent();
		$.pdialog.open("<%=basePath%>/pages/platform/desktop/desktopItem/edit.action?id=" + id,"editDesktopItemDialog","修改",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.desktop.previewDesktopItem = function(id){
		$.pdialog.open("<%=basePath%>/pages/platform/desktop/desktopItem/preview.action?id=" + id,"previewDesktopItemDialog","预览",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function() {
		ns.common.showQuery('${queryType}')
		ns.common.mouseForButton();
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath%>/pages/platform/desktop/desktopItem/list.action?queryType=${queryType}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />		
		<input type="hidden" name="desktopItem.name" value="${desktopItem.name}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" class="listbutton" onclick="ns.desktop.newDesktopItem();" >新建</button>
					<button type="button" class="listbutton" onclick="ns.desktop.deleteDesktopItem();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/platform/desktop/desktopItem/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>栏目名称	</td>
						<td class="queryTd">
							<input name="desktopItem.name" value="${desktopItem.name}"/>
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
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'desktopItemIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center">栏目名称</th>
						<th align="center">方法路径</th>
						<th align="center">模块路径</th>
						<th align="center" width="15%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="desktopItem" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="desktopItemIDs" value="${desktopItem.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${desktopItem.name}</td>
		                    	<td align="center">${desktopItem.methodUrl}</td>
		                    	<td align="center">${desktopItem.moduleUrl}</td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.desktop.viewDesktopItem('${desktopItem.id}');">查看</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.desktop.editDesktopItem('${desktopItem.id}');">编辑</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.desktop.deleteDesktopItem('${desktopItem.id}');">删除</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.desktop.previewDesktopItem('${desktopItem.id}');">预览</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>