<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

	function openDialog() {
		$.pdialog.open("<%=basePath %>/pages/resource/tabulation/add.action?op=new","newDialog","新建列表",{width:950,height:650,mask:true,resizable:true});
	}
	
	function openSearch() {
		$.pdialog.open("<%=basePath %>/pages/resource/tabulation/search.action","searchDialog","高级查询",{width:620,height:250,mask:false,resizable:true});
	}
	
	function deleteDialog(){
		var urlString = "<%=basePath %>/pages/resource/tabulation/del.action";
		var param = $("#tableForm").serialize();
		var result = urlString + "?" + param;
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
	}
	
	function selectAll(obj,cName){
		var checkboxs = document.getElementsByName(cName);
		for(var i=0;i<checkboxs.length;i++){
    		checkboxs[i].checked = obj.checked;
    	}
	} 
	function loadButton() {
		var urlString = "<%=basePath %>/pages/resource/tabulationButton/add.action?tabulationId="+$("#tabulationId").val();
		$('#buttonId').load(urlString);
	}
	function loadButtonList() {
		var urlString = "<%=basePath %>/pages/resource/tabulationButton/list.action?tabulationId=" + $("#tabulationId").val();
		$.ajaxSetup({async: false});
		$('#buttonListId').loadUrl(urlString);	
		//initPagination();
		$.ajaxSetup({async: true});
	}
	function loadEditTabulationButton(id) {
		var urlString = "<%=basePath %>/pages/resource/tabulationButton/edit.action?tabulationId="+$("#tabulationId").val()+"&buttonId=" + id;
		$('#buttonId').load(urlString);	
	}
	function addTabulationButton() {
		if($("#tabulationId").val()==""||$("#tabulationId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		$("#btnId").val("");
		var urlString = "<%=basePath %>/pages/resource/tabulationButton/save.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationButtonTabulationId").serialize(),
			success: function(data){
				loadButtonList();
			}
		
		});
	}
	function updateTabulationButton() {
		if($("#tabulationId").val()==""||$("#tabulationId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		var urlString = "<%=basePath %>/pages/resource/tabulationButton/save.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationButtonTabulationId").serialize(),
			success: function(data){
				loadButtonList();
			}
		});
	}
	function deleteTabulationButton() {
		var urlString = "<%=basePath %>/pages/resource/tabulationButton/del.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationButtonListForm").serialize(),
			success: function(data){
				loadButton();
				loadButtonList();
			}
		});
	}
	
	function showButton(v){
		var urlString = "<%=basePath %>/pages/resource/tabulationButton/show.action?buttonType=" + v;
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#buttonorbuttongroupId").html(data);
			}
		});
	}
	
	function changeButtonSpan(v){
		if(v == 1){
			$("#buttonspan").html("按钮组");
		}else{
			$("#buttonspan").html("按钮");
		}
		showButton(v);
	}
	
	function loadtabulationColumn() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/addColumn.action?tabulationId="+$("#tabulationId").val();
		$('#tabulationColumnId').load(urlString);	
	}
	function loadtabulationColumnList() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/listColumn.action?tabulationId=" + $("#tabulationId").val();
		$.ajaxSetup({async: false});
		$('#tabulationColumnListId').loadUrl(urlString);	
		initPagination();
		$.ajaxSetup({async: true});
	}
	function loadEditTabulationColumn(id) {
		var urlString = "<%=basePath %>/pages/resource/tabulation/editColumn.action?tabulationId="+$("#tabulationId").val()+"&tabulationColumnId=" + id;
		$('#tabulationColumnId').load(urlString);	
	}
	function addTabulationColumn() {
		$tabulation = $("#tabulationColumnTabulationId");
		if (!$tabulation.valid()) {
			return false;
		}
		
		$("#fcId").val("");
		var urlString = "<%=basePath %>/pages/resource/tabulation/saveColumn.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationColumnTabulationId").serialize(),
			success: function(data){
				loadtabulationColumnList();
			}
		});
	}
	function updateTabulationColumn() {
		$tabulation = $("#tabulationColumnTabulationId");
		if (!$tabulation.valid()) {
			return false;
		}
		
		var urlString = "<%=basePath %>/pages/resource/tabulation/saveColumn.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationColumnTabulationId").serialize(),
			success: function(data){
				loadtabulationColumnList();
			}
		});
	}
	function deleteTabulationColumn() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/deleteColumn.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationColumnListForm").serialize(),
			success: function(data){
				loadtabulationColumn();
				loadtabulationColumnList();
			}
		});
	}
	function completeTabulationColumn() {
		$.pdialog.closeCurrent(); 
	}
	function showColumn(){
		var urlString = "<%=basePath %>/pages/resource/tabulation/showColumn.action?id=" + $("#tableId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#columnId").html(data);
			}
		});
	}
	function showQueryColumn(){
		var urlString = "<%=basePath %>/pages/resource/tabulation/showQueryColumn.action?tableId=" + $("#tableId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#queryColumnId").html(data);
			}
		});
	}
	
	function loadTabulationDetails() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/addDetails.action?tabulationId="+$("#tabulationId").val();
		$('#tabulationDetailId').load(urlString);	
	}
	
	function updateTabulationDetails() {
		$tabulation = $("#tabulationDetailsTabulationId");
		if (!$tabulation.valid()) {
			return false;
		}
		if($("#fdId").val()==undefined||$("#fdId").val()==""){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		var urlString = "<%=basePath %>/pages/resource/tabulation/saveDetails.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationDetailsTabulationId").serialize(),
			success: function(data){
				loadTabulationDetails();
			}
		});
	}
	
	//筛选条件tab
	function loadTabulationQuery() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/addQuery.action?tabulationId="+$("#tabulationId").val();
		$('#tabulationQueryId').load(urlString);	
	}
	function loadTabulationQueryList() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/listQuery.action?tabulationId=" + $("#tabulationId").val();
		$.ajaxSetup({async: false});
		$('#tabulationQueryListId').loadUrl(urlString);	
		initPagination();
		$.ajaxSetup({async: true});
	}
	function addTabulationQuery() {
		$tabulation = $("#tabulationQueryTabulationId");
		if (!$tabulation.valid()) {
			return false;
		}
		
		$("#tqId").val("");
		var urlString = "<%=basePath %>/pages/resource/tabulation/saveQuery.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationQueryTabulationId").serialize(),
			success: function(data){
				loadTabulationQueryList();
			}
		});
	}
	function updateTabulationQuery() {
		$tabulation = $("#tabulationQueryTabulationId");
		if (!$tabulation.valid()) {
			return false;
		}
		
		var urlString = "<%=basePath %>/pages/resource/tabulation/saveQuery.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationQueryTabulationId").serialize(),
			success: function(data){
				loadTabulationQueryList();
			}
		});
	}
	function deleteTabulationQuery() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/deleteQuery.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationQueryListForm").serialize(),
			success: function(data){
				loadTabulationQuery();
				loadTabulationQueryList();
			}
		});
	}
	function loadEditTabulationQuery(id) {
		var urlString = "<%=basePath %>/pages/resource/tabulation/editQuery.action?tabulationId="+$("#tabulationId").val()+"&tabulationQueryId=" + id;
		$('#tabulationQueryId').load(urlString);	
	}
	
	//操作信息tab
	function loadTabulationOpt() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/addOpt.action?tabulationId="+$("#tabulationId").val();
		$('#tabulationOptId').load(urlString);	
	}
	function loadTabulationOptList() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/listOpt.action?tabulationId=" + $("#tabulationId").val();
		$.ajaxSetup({async: false});
		$('#tabulationOptListId').loadUrl(urlString);	
		initPagination();
		$.ajaxSetup({async: true});
	}
	function addTabulationOpt() {
		$tabulation = $("#tabulationOptTabulationId");
		if (!$tabulation.valid()) {
			return false;
		}
		
		$("#tqId").val("");
		var urlString = "<%=basePath %>/pages/resource/tabulation/saveOpt.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationOptTabulationId").serialize(),
			success: function(data){
				loadTabulationOptList();
			}
		});
	}
	function updateTabulationOpt() {
		$tabulation = $("#tabulationOptTabulationId");
		if (!$tabulation.valid()) {
			return false;
		}
		
		var urlString = "<%=basePath %>/pages/resource/tabulation/saveOpt.action?tabulationId=" + $("#tabulationId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationOptTabulationId").serialize(),
			success: function(data){
				loadTabulationOptList();
			}
		});
	}
	function deleteTabulationOpt() {
		var urlString = "<%=basePath %>/pages/resource/tabulation/deleteOpt.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabulationOptListForm").serialize(),
			success: function(data){
				loadTabulationOpt();
				loadTabulationOptList();
			}
		});
	}
	function loadEditTabulationOpt(id) {
		var urlString = "<%=basePath %>/pages/resource/tabulation/editOpt.action?tabulationId="+$("#tabulationId").val()+"&tabulationOptId=" + id;
		$('#tabulationOptId').load(urlString);	
	}
	
	$(function(){
		xjUrl="<%=basePath %>/pages/resource/tabulation/add.action?op=new&formManageId=${formManageId}";
		bcUrl="<%=basePath %>/pages/resource/tabulation/save.action";
		plscUrl="<%=basePath %>/pages/resource/tabulation/del.action?model=liebiao";
		ns.common.mouseForButton();
	});
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/tabulation/list.action">
		<input type=hidden name="model" value="${model}"/>
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="tabulation.tabulationName" value="${tabulation.tabulationName}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<input type="hidden" name="code" value="${code}">
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<c:forEach items="${tabulationButtons}" var="tabulationButton" varStatus="status">
					<c:if test="${tabulationButton.buttonType == '1'}">
						<c:forEach items="${tabulationButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
							<button name="b${status.count*stat.count}" class="listbutton" onclick="eventCompex${buttonAndGroup.button.buttonBM}();">${buttonAndGroup.button.buttonName}</button>&nbsp;
						</c:forEach>
					</c:if>
					<c:if test="${tabulationButton.buttonType == '0'}">
						<button name="b${status.count}" class="listbutton" onclick="eventCompex${tabulationButton.button.buttonBM}();">${tabulationButton.button.buttonName}</button>&nbsp;	
					</c:if>
				</c:forEach>
<%--				<button id="b1" name="b1" class="listbutton" onClick="openDialog();">新建</button>&nbsp;--%>
<%--				<button id="b2" name="b2" class="listbutton" onclick="deleteDialog();">删 除</button>&nbsp;--%>
<%--				<button id="b10" name="b10" class="listbutton" disabled="disabled">帮助</button>--%>
			</td></tr></table>
		</div>
	</div>
	<div class="pageHeader">
		<div class="searchBar">
			<form onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/resource/tabulation/list.action" method="post">
				<input type="hidden" name="code" value="${code}">
				<table class="searchContent">
					<tr>
						<td>
							列表名称
						</td>
						<td>
							<input type="text" name="tabulation.tabulationName" value="${tabulation.tabulationName}"/>
						</td>
						<td>
							<button type="submit" class="listbutton">查询</button>
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
					<th width="5%" align="center"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/></th>
					<th width="5%" align="center">序号</th>
					<th width="100" align="center">列表名称</th>
					<th width="100" align="center">功能说明</th>
					<th width="100" align="center">引用模版</th>
					<th width="100" align="center">关联表单</th>
					<th width="100" align="center">创建人</th>
					<th width="100" align="center">创建时间</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="tabulation" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                    <td align="center" width="5%"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${tabulation.id}"></td>
	                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    <td>${tabulation.tabulationName}</td>
	                    <td>${tabulation.remarks}</td>
	                    <td>${tabulation.templateListName}</td>
	                    <td>${tabulation.formName}</td>
						<td>
							${tabulation.userName}
						</td>
						<td>
							<fmt:formatDate value="${tabulation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td align="center">
							&nbsp;
							<a target="dialog" mask="true" width="950" height="650" href="<%=basePath %>/pages/resource/tabulation/view.action?tabulationId=${tabulation.id}&formManageId=${formManageId}" target="navTab"><span>维护</span></a>
							&nbsp;
						</td>
	                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>