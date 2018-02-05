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
	ns.dataSource.newDataSource = function(){
		$.pdialog.open("<%=basePath%>/pages/system/dataSource/add.action","newDataSourceDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	//删除实体
	ns.dataSource.deleteDataSource = function(){
		var url = "<%=basePath%>/pages/system/dataSource/delete.action";
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
	ns.dataSource.viewDataSource = function(id) {
		$.pdialog.open("<%=basePath%>/pages/system/dataSource/view.action?dataSourcePojo.id=" + id,"viewDataSourceDialog","明细",{width:950,height:650,mask:true,resizable:true});
	}
	
	//修改实体
	ns.dataSource.editDataSource = function(id){
		$.pdialog.closeCurrent();
		$.pdialog.open("<%=basePath%>/pages/system/dataSource/edit.action?dataSourcePojo.id=" + id,"editDataSourceDialog","修改",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.dataSource.setDefault = function(){
		var items = $("input[name='dataSourceIDs']:checked").length;
		if(items == 1){
			var id = $("#tableForm").find("input[name='dataSourceIDs']:checked").val();
			var urlString = "<%=basePath %>/pages/system/dataSource/isDefault.action?dataSourcePojo.id="+id;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(urlString,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
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
	<form id="pagerForm" method="post" action="<%=basePath%>/pages/system/dataSource/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="dataSourcePojo.dsName" value="${dataSourcePojo.dsName}"/>
		<input type="hidden" name="dataSourcePojo.dsUrl" value="${dataSourcePojo.dsUrl}"/>
		<input type="hidden" name="dataSourcePojo.remark" value="${dataSourcePojo.remark}"/>
		<input type="hidden" name="dataSourcePojo.comment" value="${dataSourcePojo.comment}"/>
		<input type="hidden" name="dataSourcePojo.xiugaishijian" value="${dataSourcePojo.xiugaishijian}"/>
		<input type="hidden" name="dataSourcePojo.dsStatus" value="${dataSourcePojo.dsStatus}"/>
		<input type="hidden" name="dataSourcePojo.dsType" value="${dataSourcePojo.dsType}"/>
		<input type="hidden" name="dataSourcePojo.dsUser" value="${dataSourcePojo.dsUser}"/>
		<input type="hidden" name="dataSourcePojo.dsPasswd" value="${dataSourcePojo.dsPasswd}"/>
		<input type="hidden" name="dataSourcePojo.dsEncoding" value="${dataSourcePojo.dsEncoding}"/>
		<input type="hidden" name="dataSourcePojo.dsDriver" value="${dataSourcePojo.dsDriver}"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newDataSource" name="newDataSource" class="listbutton" onclick="ns.dataSource.newDataSource();">新建</button>
					<button type="button" id="deleteDataSource" name="deleteDataSource" class="listbutton" onclick="ns.dataSource.deleteDataSource();">删除</button>
					<button type="button" id="deleteDataSource" name="deleteDataSource" class="listbutton" onclick="ns.dataSource.setDefault();">默认</button>
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/system/dataSource/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>数据源名称</td>
						<td class="queryTd">
							<input type="text" name="dataSourcePojo.dsName" value="${dataSourcePojo.dsName}"  class="textInput" style="width:180px;"/>
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
			<form id="advanceQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/system/dataSource/list.action?queryType=1" method="post">
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
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'dataSourceIDs')"/></th>
						<th align="center" width="5%">序号</th>
							<th align="center">数据源名称</th>
							<th align="center">是否默认</th>
							<th align="center">数据库类型</th>
							<th align="center">用户名</th>
							<th align="center">密码</th>
							<th align="center">连接编码</th>
							<th align="center">数据库驱动</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody>
	            <c:forEach items="${pageResult.content}" var="dataSourcePojo" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
                    	<td align="center"><input type="checkbox" class="checkbox"  name="dataSourceIDs" value="${dataSourcePojo.id}"/></td>
						<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
						<td align="center">${dataSourcePojo.dsName}</td>
						<td align="center">
							<c:if test="${dataSourcePojo.dsStatus==0}">否</c:if>
							<c:if test="${dataSourcePojo.dsStatus==1}">是</c:if>
						</td>
						<td align="center">${dataSourcePojo.dsType}</td>
						<td align="center">${dataSourcePojo.dsUser}</td>
						<td align="center">${dataSourcePojo.dsPasswd}</td>
						<td align="center">${dataSourcePojo.dsEncoding}</td>
						<td align="center">${dataSourcePojo.dsDriver}</td>
                    	<td align="center">
                    		<a style="cursor: pointer;color:blue;" onclick="ns.dataSource.viewDataSource('${dataSourcePojo.id}');">明细</a>&nbsp;
                    		<a style="cursor: pointer;color:blue;" onclick="ns.dataSource.editDataSource('${dataSourcePojo.id}');">编辑</a>
                    	</td>
                    </tr>
	            </c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>