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
	ns.email.newMailAccount = function(){
		$.pdialog.open("<%=basePath%>/pages/platform/email/mailAccount/add.action","newMailAccountDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.email.deleteMailAccount = function(id){
		if(id){
			var url = "<%=basePath%>/pages/platform/email/mailAccount/delete.action?id="+id;
		}else{
			var items = $("#tableForm input[type='checkbox']:checked").length;
			if(items == 0){alertMsg.warn("请选择要删除的数据!");return;}
			var url = "<%=basePath%>/pages/platform/email/mailAccount/delete.action";
			var param = $("#tableForm").serialize();
			var url = url + "?" + param;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.email.viewMailAccount = function(id) {
		$.pdialog.open("<%=basePath%>/pages/platform/email/mailAccount/view.action?mailAccount.id=" + id,"viewMailAccountDialog","明细",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.email.editMailAccount = function(id){
		$.pdialog.closeCurrent();
		$.pdialog.open("<%=basePath%>/pages/platform/email/mailAccount/edit.action?mailAccount.id=" + id,"editMailAccountDialog","修改",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.email.defaultMailAccount = function(){
		var items = $("#mailAccountList #tableForm input[type='checkbox']:checked").length;
		if(items != 1){alertMsg.warn("请选择要一条的数据!");return;}
		var param = $('#mailAccountList #tableForm').serialize();
		var url = '<%=basePath %>/pages/platform/email/mailAccount/setDefault.action?' + param;
		$.post(url,null,refreshList,'json');
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
<div id="mailAccountList">
	<form id="pagerForm" method="post" action="<%=basePath%>/pages/platform/email/mailAccount/list.action?queryType=${queryType}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />		
		<input type="hidden" name="mailAccount.name" value="${mailAccount.name}" />
		<input type="hidden" name="mailAccount.address" value="${mailAccount.address}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newMailAccount" name="newMailAccount" class="listbutton" onclick="ns.email.newMailAccount();" >新建</button>
					<button type="button" id="deleteMailAccount" name="deleteMailAccount" class="listbutton" onclick="ns.email.deleteMailAccount();" >删除</button>				
					<button type="button" id="defaultMailAccount" name="deleteMailAccount" class="listbutton" onclick="ns.email.defaultMailAccount();" >默认</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/platform/email/mailAccount/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>账号名称	</td>
						<td class="queryTd">
							<input name="mailAccount.name" value="${mailAccount.name}"/>
						</td>
						<td>邮箱</td>
						<td class="queryTd">
							<input name="mailAccount.address" value="${mailAccount.address}" />
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
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'mailAccountIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center">账号名称</th>
						<th align="center">邮箱地址</th>
						<th align="center">默认</th>
						<th align="center" width="10%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="mailAccount" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="mailAccountIDs" value="${mailAccount.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${mailAccount.name}</td>
		                    	<td align="center">${mailAccount.address}</td>
		                    	<td align="center"><c:if test="${mailAccount.deflt eq 1}">是</c:if><c:if test="${mailAccount.deflt ne 1}">否</c:if></td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.email.viewMailAccount('${mailAccount.id}');">查看</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.email.editMailAccount('${mailAccount.id}');">编辑</a>
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.email.deleteMailAccount('${mailAccount.id}');">删除</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</div>
</body>
</html>