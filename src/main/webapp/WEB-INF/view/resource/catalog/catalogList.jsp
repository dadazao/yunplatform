<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

	//function openDialog() {
	//	$.pdialog.open("<%=basePath %>/pages/resource/catalogadd.action?op=new","newCatalogDialog","新建目录",{width:1024,height:768,mask:true,resizable:true});
	//}
	
	function openSearch() {
		$.pdialog.open("<%=basePath %>/pages/system/search/catalogSearch.jsp","searchCatalogDialog","高级查询",{width:620,height:250,mask:false,resizable:true});
	}
	
	function deleteDomains(){
		var urlString = "<%=basePath %>/pages/resource/catalogdelete.action";
		var param = $("#tableForm").serialize();
		var result = urlString + "?" + param ;
		var items = $("input[name='selectedIDs']:checked").length;//
		if(items == 0){
			alertMsg.info("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(result);}});
	}
	
	function fresh() {
		var urlString = "<%=basePath %>/pages/system/treeshow.action?model=catalog&root=1&idColumn=id&nameColumn=name&codeColumn=code&parentIdColumn=parentId&pathColumn=path&orderColumn=orderNum";
		$.ajaxSetup({async: false});
		$.ajaxSetup ({ 
			cache: false 
		});
		$('#sidebar').load(urlString);
		initUI();
		$.ajaxSetup({async: true});
	}
	
	function showCatalogTree() {
		$.pdialog.open("<%=basePath %>/pages/resource/catalog/catalogTree.jsp","selectCatalogDialog","目录树",{width:300,height:600,mask:true,resizable:true});
	}
	
	function search(){
		$("#catalogformSubmitId").submit();
	}
	
	$(function() {
		plscUrl = "<%=basePath %>/pages/resource/catalogbatchDelete.action?model=${model}";
		//新建ACTION URL
		xjUrl = "<%=basePath %>/pages/resource/catalogadd.action?op=new&formId=${formId}&model=${model}";
	});
	
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/cataloglist.action">
		<input type=hidden name="model" value="catalog"/>
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="catalog.name" value="${catalog.name}" />
		<input type="hidden" name="catalog.code" value="${catalog.code}" />
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<input type="hidden" name="seqcode" value="${seqcode}"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<c:forEach items="${tabulationButtons}" var="tabulationButton" varStatus="status">
					<c:if test="${tabulationButton.buttonType == '1'}">
						<c:forEach items="${tabulationButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
							<button name="b${status.count*stat.count}" class="listbutton" onclick="event${buttonAndGroup.button.buttonBM}();">${buttonAndGroup.button.buttonName}</button>&nbsp;
						</c:forEach>
					</c:if>
					<c:if test="${tabulationButton.buttonType == '0'}">
						<button name="b${status.count}" class="listbutton" onclick="event${tabulationButton.button.buttonBM}();">${tabulationButton.button.buttonName}</button>&nbsp;	
					</c:if>
				</c:forEach>
			</td></tr></table>
		</div>
	</div>
	<div class="pageHeader">
		<div class="searchBar">
			<form id="catalogformSubmitId" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/resource/cataloglist.action" method="post">
				<table class="searchContent">
					<tr>
						<td>
							目录名称
						</td>
						<td>
							<input type="text" name="catalog.name" value="${catalog.name}"/>
						</td>
<%--						<td>--%>
<%--							编码<input type="text" name="catalog.code" value="${catalog.code}"/>--%>
<%--						</td>--%>
						<td>
							<input type="hidden" name="model" value="catalog"/>
							<input type="hidden" name="seqcode" value="${seqcode}"/>
							<button type="button" class="listbutton" onclick="search()">查询</button>&nbsp;&nbsp;
<%--							<button id="b12" type="button" name="b12" class="listbutton" onclick="openSearch();">高级查询</button>--%>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<!-- 
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${pageResult.pageSize==20 }">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50 }">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100 }">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200 }">selected="selected"</c:if>>200</option>
			</select>
			 -->
			<input type="hidden" name="numPerPage" value="20">
			<span>20条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="5%" align="center"><input name="all" type="checkbox" class="checkbox"  onclick="selectAll(this,'selectedIDs')"/></th>
					<th width="5%" align="center">序号</th>
					<th  align="center">目录名称</th>
<%--					<th  align="center">编码</th>--%>
					<th  align="center">父目录</th>
					<th  align="center">顺序</th>
					<th  align="center">对应路径</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="catalog" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                    <td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${catalog.id}"></td>
	                    <td>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    <td>${catalog.name}</td>
<%--	                    <td>${catalog.code}</td>--%>
	                    <td>${catalog.parentName}</td>
	                    <td>${catalog.orderNum}</td>
	                    <td>${catalog.path}</td>
						<td align="center">
							&nbsp;
							<a target="dialog" mask="true" width="1024" height="768" href="<%=basePath %>/pages/resource/catalogview.action?id=${catalog.id}&model=${model}&formId=${formId}" target="navTab"><span>维护</span></a>
							&nbsp;
						</td>
	                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>