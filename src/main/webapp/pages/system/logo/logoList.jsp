<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>logo管理</title>
<script type="text/javascript">
	function addLogo(){
		$.pdialog.open("<%=basePath%>/pages/system/logo/logoEdit.jsp","newDialog","上传",{width:450,height:140,mask:true,resizable:false});
	}
	
	function deleteLogo(){
		alertMsg.confirm("确定删除吗？",{okCall:function(){
			var urlString = "<%=basePath%>/pages/system/logodelete.action";
			var param = $("#logoForm").serialize();
			urlString = urlString + "?" + param;
			ajaxTodo(urlString);
		}});
	}
	
	function selectAll(obj,cName){
		var checkboxs = document.getElementsByName(cName);
		for(var i=0;i<checkboxs.length;i++){
    		checkboxs[i].checked = obj.checked;
    	}
	}
	
	function save(){
		$.ajaxSetup({async: false});
		$("#logoForm").submit();
		$.ajaxSetup({async: true});
		showLogo();
	}
	
</script>
</head>
<body>
	<form id="pagerForm" name="pagerForm" method="post" action="<%=basePath %>/pages/system/logolist.action">
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
	</form>
	<form method="post" id="logoForm" name="logoForm" action="<c:url value='/pages/system/logosave.action'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
			<button id="b1" name="b1" class="listbutton" onClick="addLogo()" type="button">上传</button>
			<button id="b2" name="b2" class="listbutton" onclick="deleteLogo();" type="button">删 除</button>
			<button id="b3" name="b3" class="listbutton" type="submit" onclick="save()">保存</button>
			</td></tr></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<input type="hidden" name="numPerPage" value="20">
			<span>20条，共${pageResult.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>

	</div>
		<table width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="10" align="right"><input name="all" type="checkbox" class="checkbox"  onclick="selectAll(this,'selectedLogos')"/></th>
					<th width="50" align="center">序号</th>
					<th width="100" align="center">logo名称</th>
					<th width="100" align="center">图片</th>
					<th width="100" align="center">是否设置为默认</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="column" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                	<td align="right"><input type="checkbox" class="checkbox"  name="selectedLogos" value="${column.logoPath}"></td>
	                    <td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    <td align="center">${column.logoName}</td>
	                    <td align="left" height="50"><img height="70" width="750" src="<%=basePath %>/images/logo/${column.logoName}"></td>
						<td align="center">
							<c:if test="${defaultLogo==column.logoName}">
								<input type="radio" name="logo.logoPath" value="${column.logoPath}" checked="checked"/>
							</c:if>
							<c:if test="${defaultLogo!=column.logoName}">
								<input type="radio" name="logo.logoPath" value="${column.logoPath}"/>
							</c:if>
						</td>
	                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form> 	
</body>
</html>