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
	//新建
	ns.system.newAuthCustomer = function(){
		$.pdialog.open("<%=basePath%>/pages/system/authCustomer/add.action","newAuthCustomerDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	//删除
	ns.system.deleteAuthCustomer = function(){
		var url = "<%=basePath%>/pages/system/authCustomer/delete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	//修改
	ns.system.editAuthCustomer = function(id){
		$.pdialog.closeCurrent();
		$.pdialog.open("<%=basePath%>/pages/system/authCustomer/edit.action?authCustomer.id=" + id,"editAuthCustomerDialog","修改",{width:950,height:650,mask:true,resizable:true});
	}
	
	//维护
	ns.system.viewAuthCustomer = function(id) {
		$.pdialog.open("<%=basePath%>/pages/system/authCustomer/view.action?authCustomer.id=" + id,"viewAuthCustomerDialog","维护",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function() {
		//判断是否显示高级查询
		//ns.common.showQuery('${queryType}')
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//-->
</script>
</head>

<body>
  <form id="pagerForm" method="post" action="<%=basePath%>/pages/system/authCustomer/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />		
		<input type="hidden" name="authCustomer.name" value="${authCustomer.name}" />
		<input type="hidden" name="authCustomer.linkman" value="${authCustomer.linkman}" />
		<input type="hidden" name="authCustomer.mobile" value="${authCustomer.mobile}" />
		<input type="hidden" name="authCustomer.email" value="${authCustomer.email}" />
		<input type="hidden" name="authCustomer.tel" value="${authCustomer.tel}" />
		<input type="hidden" name="authCustomer.address" value="${authCustomer.address}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newAuthCustomer" name="newAuthCustomer" class="listbutton" onclick="ns.system.newAuthCustomer();" >新建</button>
					<button type="button" id="deleteAuthCustomer" name="deleteAuthCustomer" class="listbutton" onclick="ns.system.deleteAuthCustomer();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/system/authCustomer/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>
							客户名称
						</td>
						<td class="queryTd">
							<input name="authCustomer.name" value="${authCustomer.name}"/>
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
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'selectedIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center">客户名称</th>
						<th align="center">联系人</th>
						<th align="center">手机</th>
						<th align="center">邮箱</th>
						<th align="center">公司电话</th>
						<th align="center">公司地址</th>
						<th align="center" width="10%" >操作</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="authCustomer" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${authCustomer.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${authCustomer.name}</td>
		                    	<td align="center">${authCustomer.linkman}</td>
		                    	<td align="center">${authCustomer.mobile}</td>
		                    	<td align="center">${authCustomer.email}</td>
		                    	<td align="center">${authCustomer.tel}</td>
		                    	<td align="center">${authCustomer.address}</td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.system.viewAuthCustomer('${authCustomer.id}');">维护</a>&nbsp;
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>
