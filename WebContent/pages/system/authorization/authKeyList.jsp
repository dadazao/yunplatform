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
	//添加授权码
	ns.system.newAuthKey = function(){
		$.pdialog.open("<%=basePath%>/pages/system/authKey/add.action","newAuthKeyDialog","添加授权码",{width:950,height:650,mask:true,resizable:true});
	}
	
	//禁用
	ns.system.deleteAuthKey = function(){
		var url = "<%=basePath%>/pages/system/authKey/delete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要禁用的数据!");
			return;
		}
		alertMsg.confirm("确定要禁用吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	//查看
	ns.system.viewAuthKey = function(id) {
		$.pdialog.open("<%=basePath%>/pages/system/authKey/view.action?authKey.id=" + id,"viewAuthKeyDialog","查看",{width:950,height:650,mask:true,resizable:true});
	}
	
	//产品模版联动
	ns.system.MainFindTemplatesByPId = function(){
		var pid = $("#mainAuthProductId").val();
		$.ajax({
			url:__basePath+"/pages/system/authKey/findTemplatesByPId.action",
			dataType:"json",
			data:{authProductId:pid},
			success:function(data){
				var shtml = "<option value=''></option>";
				$.each(data,function (index, elem) { 
					shtml += "<option value='"+elem.id+"' >"+elem.name+"</option>";
				});
				$("#mainAuthTemplateId").html(shtml);
			}
		});
		
	}
	
	$(function() {
		//判断是否显示高级查询
		//ns.common.showQuery('${queryType}')
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		
		$("#mainAuthProductId").combobox();
		$("#mainAuthCustomerId").combobox();
	});
//-->
</script>
</head>

<body>
  <form id="pagerForm" method="post" action="<%=basePath%>/pages/system/authKey/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />		
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button style="width:100px;" type="button" id="newAuthKey" name="newAuthKey" class="listbutton" onclick="ns.system.newAuthKey();" >添加授权码</button>
					<button type="button" id="deleteAuthKey" name="deleteAuthKey" class="listbutton" onclick="ns.system.deleteAuthKey();" >禁用</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/system/authKey/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>
							SN
						</td>
						<td class="queryTd">
							<input name="authKey.sn" value="${authKey.sn}"/>
						</td>
						<td>
							产品
						</td>
						<td class="queryTd">
							<select id="mainAuthProductId" name="authKey.productid" onchange="ns.system.MainFindTemplatesByPId();"
								style="width: 186px">
								<option value="" ></option>
								<c:forEach items="${authProducts}" var="bean">
									<option value="${bean.id}" >${bean.name}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							模版
						</td>
						<td class="queryTd">
							<select id="mainAuthTemplateId" name="authKey.templateid"
								style="width: 186px">
								<option value="" ></option>
							</select>
						</td>
						<td>
							客户
						</td>
						<td class="queryTd">
							<select id="mainAuthCustomerId" name="authKey.customerid"
								style="width: 186px">
								<option value="" ></option>
								<c:forEach items="${authCustomers}" var="bean">
									<option value="${bean.id}" >${bean.name}</option>
								</c:forEach>
							</select>
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
						<th align="center" width="15%">授权码</th>
						<th align="center" width="25%">客户</th>
						<th align="center" width="5%">授权类型</th>
						<th align="center" width="5%">状态</th>
						<th align="center" width="10%">产生</th>
						<th align="center" width="10%">激活</th>
						<th align="center" width="10%">失效</th>
						<th align="center" width="10%" >操作</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="authKey" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${authKey.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${authKey.sn}</td>
		                    	<td align="center">${authKey.customername}</td>
		                    	<td align="center">
			                    	<c:if test="${authKey.authtype == 'CLOUD'}">
										云授权&nbsp;
									</c:if>
			                    	<c:if test="${authKey.authtype == 'NATIVE'}">
										本地授权&nbsp;
									</c:if>
		                    	</td>
		                    	<td align="center">
		                    		<c:if test="${authKey.status == 'UNACTIVE'}">
										<span style="">未激活</span>&nbsp;
									</c:if>
		                    		<c:if test="${authKey.status == 'ACTIVED'}">
										<span style="color: #17A325;">已激活</span>&nbsp;
									</c:if>
		                    		<c:if test="${authKey.status == 'UNVALID'}">
										<span style="color: red;">失效</span>&nbsp;
									</c:if>
		                    	</td>
		                    	<td align="center"><fmt:formatDate value="${authKey.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
		                    	<td align="center"><fmt:formatDate value="${authKey.activationdate}" pattern="yyyy-MM-dd HH:mm"/></td>
		                    	<td align="center"><fmt:formatDate value="${authKey.expirydate}" pattern="yyyy-MM-dd HH:mm"/></td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.system.viewAuthKey('${authKey.id}');">查看</a>&nbsp;
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>
