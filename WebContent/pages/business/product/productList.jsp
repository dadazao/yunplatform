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
	ns.product.newProduct = function(){
		$.pdialog.open("<%=basePath%>/pages/business/product/add.action","newProductDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	//删除实体
	ns.product.deleteProduct = function(){
		var url = "<%=basePath%>/pages/business/product/delete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	//查看实体明细
	ns.product.viewProduct = function(id) {
		$.pdialog.open("<%=basePath%>/pages/business/product/view.action?product.id=" + id,"viewProductDialog","明细",{width:950,height:650,mask:true,resizable:true});
	}
	
	//修改实体
	ns.product.editProduct = function(id){
		$.pdialog.closeCurrent();
		$.pdialog.open("<%=basePath%>/pages/business/product/edit.action?product.id=" + id,"editProductDialog","修改",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function() {
		//判断是否显示高级查询
		ns.common.showQuery('${queryType}')
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/business/product/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="product.name" value="${product.name}"/>
		<input type="hidden" name="product.price" value="${product.price}"/>
		<input type="hidden" name="product.description" value="${product.description}"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newProduct" name="newProduct" class="listbutton" onclick="ns.product.newProduct();" >新建</button>
					<button type="button" id="deleteProduct" name="deleteProduct" class="listbutton" onclick="ns.product.deleteProduct();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/business/product/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr><td>
						<td>名称</td>
						<td class="queryTd">
							<input type="text" name="product.name" value="${product.name}"  class="textInput" style="width:180px;"/>
						</td>
						<td>价格</td>
						<td class="queryTd">
							<input type="text" name="product.price" value="${product.price}"  class="textInput" style="width:180px;"/>
						</td>
						<td>描述</td>
						<td class="queryTd">
							<input type="text" name="product.description" value="${product.description}"  class="textInput" style="width:180px;"/>
						</td>
						<td>
							<button type="button" class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button>
							<button type="button" class="listbutton" onclick="ns.common.showQuery(1);">高级查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="advanceQuery" align="center" class="pageHeader" style="display:none;">
		<fieldset class="queryFieldset" >
			<legend style="border:0px;">查询项</legend>
			<form id="advanceQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/business/product/list.action?queryType=1" method="post">
				<table width="98%"  class="queryTable" >
					<tr>
						<td class="advanceTd">高级查询字段名</td><td class="advanceTd">高级查询字段值	</td>
					</tr>
				</table>
				<div align="center" style="margin-top: 5px;">
					<input type="hidden" name="listId" value="${listId}"/>
					<button type="button" class="listbutton" onclick="ns.common.showQuery(0);">隐藏</button>
					<button type="button" class="listbutton" onclick="ns.common.query('advanceQueryForm');">查询</button>
				</div>
			</form>
		</fieldset>
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
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'productIDs')"/></th>
						<th align="center" width="5%">序号</th>
							<th align="center">名称</th>
							<th align="center">价格</th>
							<th align="center">描述</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody>
	            <c:forEach items="${pageResult.content}" var="product" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
                    	<td align="center"><input type="checkbox" class="checkbox"  name="productIDs" value="${product.id}"/></td>
						<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
						<td align="center">${product.name}</td>
						<td align="center">${product.price}</td>
						<td align="center">${product.description}</td>
                    	<td align="center">
                    		<a style="cursor: pointer;color:blue;" onclick="ns.product.viewProduct('${product.id}');">明细</a>&nbsp;
                    		<a style="cursor: pointer;color:blue;" onclick="ns.product.editProduct('${product.id}');">编辑</a>
                    	</td>
                    </tr>
	            </c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>