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
	ns.system.newAuthTemplate = function(){
		$.pdialog.open("<%=basePath%>/pages/system/authTemplate/add.action","newAuthTemplateDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	//删除
	ns.system.deleteAuthTemplate = function(){
		var url = "<%=basePath%>/pages/system/authTemplate/delete.action";
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
	ns.system.editAuthTemplate = function(id){
		$.pdialog.closeCurrent();
		$.pdialog.open("<%=basePath%>/pages/system/authTemplate/edit.action?authTemplate.id=" + id,"editAuthTemplateDialog","修改",{width:950,height:650,mask:true,resizable:true});
	}
	
	//维护
	ns.system.viewAuthTemplate = function(id) {
		$.pdialog.open("<%=basePath%>/pages/system/authTemplate/view.action?authTemplate.id=" + id,"viewAuthTemplateDialog","维护",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function() {
		//判断是否显示高级查询
		//ns.common.showQuery('${queryType}')
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		
		$("#mainauthTemplateId").combobox();
	});
//-->
</script>
</head>

<body>
  <form id="pagerForm" method="post" action="<%=basePath%>/pages/system/authTemplate/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />		
		<input type="hidden" name="authTemplate.name" value="${authTemplate.name}" />
		<input type="hidden" name="authTemplate.productid" value="${authTemplate.productid}" />
		<input type="hidden" name="authTemplate.productname" value="${authTemplate.productname}" />
		<input type="hidden" name="authTemplate.authtype" value="${authTemplate.authtype}" />
		<input type="hidden" name="authTemplate.startdate" value="${authTemplate.startdate}" />
		<input type="hidden" name="authTemplate.enddate" value="${authTemplate.enddate}" />
		<input type="hidden" name="authTemplate.validdays" value="${authTemplate.validdays}" />
		
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newAuthTemplate" name="newAuthTemplate" class="listbutton" onclick="ns.system.newAuthTemplate();" >新建</button>
					<button type="button" id="deleteAuthTemplate" name="deleteAuthTemplate" class="listbutton" onclick="ns.system.deleteAuthTemplate();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/system/authTemplate/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>
							产品
						</td>
						<td class="queryTd">
							<select id="mainauthTemplateId" name="authTemplate.productname"
								style="width: 186px">
								<option value="" ></option>
								<c:forEach items="${authProducts}" var="bean">
									<option value="${bean.id}" >${bean.name}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							模版名称
						</td>
						<td class="queryTd">
							<input name="authTemplate.name" value="${authTemplate.name}"/>
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
						<th align="center">模版名称</th>
						<th align="center">产品名称</th>
						<th align="center">授权类型</th>
						<th align="center">起始日期</th>
						<th align="center">结束日期</th>
						<th align="center">有效期</th>
						<th align="center" width="10%" >操作</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="authTemplate" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${authTemplate.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${authTemplate.name}</td>
		                    	<td align="center">${authTemplate.productname}</td>
		                    	<td align="center">
									<c:if test="${authTemplate.authtype == 'CLOUD'}">
										云授权&nbsp;
									</c:if>
									<c:if test="${authTemplate.authtype == 'NATIVE'}">
										本地授权&nbsp;
									</c:if>
								</td>
		                    	<td align="center"><fmt:formatDate value="${authTemplate.startdate}" pattern="yyyy-MM-dd"/></td>
		                    	<td align="center"><fmt:formatDate value="${authTemplate.enddate}" pattern="yyyy-MM-dd"/></td>
		                    	<td align="center">${authTemplate.validdays}</td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.system.viewAuthTemplate('${authTemplate.id}');">维护</a>&nbsp;
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>
