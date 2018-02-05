<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%><%@page import="java.text.SimpleDateFormat"%>
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
	function loadButton() {
		var urlString = "<%=basePath %>/pages/resource/fbuttonaddButton.action?formId="+$("#formId").val();
		$('#buttonId').load(urlString);
	}
	function loadButtonList() {
		var urlString = "<%=basePath %>/pages/resource/fbuttonlistButton.action?formId=" + $("#formId").val();
		$.ajaxSetup({async: false});
		$('#buttonListId').loadUrl(urlString);	
		initPagination();
		$.ajaxSetup({async: true});
	}
	
	function loadEditFormButton(id) {
		var urlString = "<%=basePath %>/pages/resource/fbuttonedit.action?formId="+$("#formId").val()+"&buttonId=" + id;
		$('#buttonId').load(urlString);	
	}
	function addFormButton() {
		if($("#formId").val()==""||$("#formId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		
		$("#btnId").val("");
		var urlString = "<%=basePath %>/pages/resource/fbuttonsave.action?formId=" + $("#formId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#formButtonformId").serialize(),
			success: function(data){
				loadButtonList();
			}
		
		});
	}
	function updateFormButton() {
		if($("#formId").val()==""||$("#formId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		var urlString = "<%=basePath %>/pages/resource/fbuttonsave.action?formId=" + $("#formId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#formButtonformId").serialize(),
			success: function(data){
				loadButtonList();
			}
		});
	}
	function deleteFormButton() {
		var urlString = "<%=basePath %>/pages/resource/fbuttondelete.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#formButtonListForm").serialize(),
			success: function(data){
				loadButton();
				loadButtonList();
			}
		});
	}
	
	function showButton(v){
		var urlString = "<%=basePath %>/pages/resource/fbuttonshowButton.action?buttonType=" + v;
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
	
	function loadTab() {
		var urlString = "<%=basePath %>/pages/resource/tabadd.action";
		$('#tabId').loadUrl(urlString);
	}
	function loadTabList() {
		var urlString = "<%=basePath %>/pages/resource/tablist.action?formId=" + $("#formId").val();
		$.ajaxSetup({async: false});
		$('#tabListId').load(urlString);	
		initPagination();
		$.ajaxSetup({async: true});
	}
	function loadEditTab(id) {
		var urlString = "<%=basePath %>/pages/resource/tabedit.action?id=" + id;
		$('#tabId').load(urlString);	
	}
	function addTab() {
		$("#tId").val("");
		if($("#formId").val()==""||$("#formId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		var urlString = "<%=basePath %>/pages/resource/tabsave.action?formId=" + $("#formId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabFormId").serialize(),
			success: function(data){
				loadTabList();
				showTab();
				loadButton();
				loadFormColumn();
			}
		});
	}
	function showTab() {
		var urlString = "<%=basePath %>/pages/resource/tabshow.action?formId=" + $("#formId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#tabSelectId").html(data);
			}
		});
	}
	function updateTab() {
		if($("#formId").val()==""||$("#formId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		var urlString = "<%=basePath %>/pages/resource/tabsave.action?formId=" + $("#formId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabFormId").serialize(),
			success: function(data){
				loadTabList();
				showTab();
				loadButton();
				loadFormColumn();
			}
		});
	}
	function deleteTab() {
		var urlString = "<%=basePath %>/pages/resource/tabdelete.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#tabListForm").serialize(),
			success: function(data){
				loadTab();
				loadTabList();
				loadButton();
				loadFormColumn();
			}
		});
	}
	
	function loadFormColumn() {
		var urlString = "<%=basePath %>/pages/resource/formaddColumn.action?formId=" + $("#formId").val();
		$('#formColumnId').loadUrl(urlString);	
	}
	function loadFormColumnList(tabid,partitionid) {
		if(tabid==undefined){
			tabid='-1';
		}
		if(partitionid==undefined){
			partitionid='-1';
		}
		var urlString = "<%=basePath %>/pages/resource/formlistColumn.action?formId=" + $("#formId").val()+"&tabId="+tabid+"&partitionId="+partitionid;
		$.ajaxSetup({async: false});
		$('#formColumnListId').load(urlString);	
		initPagination();
		$.ajaxSetup({async: true});
	}
	function loadEditFormColumn(id) {
		var urlString = "<%=basePath %>/pages/resource/formeditColumn.action?id=" + id + "&formId=" + $("#formId").val();
		$('#formColumnId').loadUrl(urlString);	
	}
	function addFormColumn() {
		if($("#formId").val()==""||$("#formId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		$form = $("#formColumnFormId");
		if (!$form.valid()) {
			return false;
		}
		
		$("#fcId").val("");
		var urlString = "<%=basePath %>/pages/resource/formsaveColumn.action?formId=" + $("#formId").val();
		
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#formColumnFormId").serialize()+"&"+$("#tempParam").text(),
			success: function(data){
				var tabid=$("#formcolumntabid option:selected").val();
				var partitionid=$("#formcolumnpartitionid option:selected").val();
				loadFormColumnList(tabid,partitionid);
			}
		});
		var formOrder = $("#formOrderId").val();
		$("#formOrderId").val(parseInt(formOrder)+1);
		if($("#listOrderId").attr("disabled")!='disabled') {
			var listOrder = $("#listOrderId").val();
			$("#listOrderId").val(parseInt(listOrder)+1);
		}
	}
	function updateFormColumn() {
		if($("#formId").val()==""||$("#formId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		
		$form = $("#formColumnFormId");
		if (!$form.valid()) {
			return false;
		}
		var urlString = "<%=basePath %>/pages/resource/formsaveColumn.action?formId=" + $("#formId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#formColumnFormId").serialize()+"&"+$("#tempParam").text(),
			success: function(data){
				var tabid=$("#formcolumntabid option:selected").val();
				var partitionid=$("#formcolumnpartitionid option:selected").val();
				loadFormColumnList(tabid,partitionid);
			}
		});
	}
	function deleteFormColumn() {
		var urlString = "<%=basePath %>/pages/resource/formdeleteColumn.action";
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#formColumnListForm").serialize(),
			success: function(data){
				loadFormColumn();
				var tabid=$("#formcolumntabid option:selected").val();
				var partitionid=$("#formcolumnpartitionid option:selected").val();
				loadFormColumnList(tabid,partitionid);
			}
		});
	}
	function completeFormColumn() {
		$.pdialog.closeCurrent(); 
	}
	function showColumn(){
		var urlString = "<%=basePath %>/pages/resource/formshowColumn.action?id=" + $("#tableId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#columnId").html(data);
			}
		});
	}
	
	function setDefaultQuery(columnId){
		var urlString = "<%=basePath %>/pages/resource/formsetDefaultQuery.action?id=" + columnId+"&formId=" + $("#formId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				var tabid=$("#formcolumntabid option:selected").val();
				var partitionid=$("#formcolumnpartitionid option:selected").val();
				loadFormColumnList(tabid,partitionid);
			}
		});
	}
	
	$(function(){
		xjUrl="<%=basePath %>/pages/resource/formadd.action?op=new&formManageId=${formManageId}";
		bcUrl="<%=basePath %>/pages/resource/formsave.action";
		plscUrl="<%=basePath %>/pages/resource/formdelete.action?model=biaodan";
	});
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/formlist.action">
		<input type=hidden name="model" value="${model}"/>
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="form.formName" value="${form.formName}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<input type="hidden" name="code" value="${code}">
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<%--<button id="b1" name="b1" class="listbutton" onClick="openDialog();">新建</button>&nbsp;
				<button id="b2" name="b2" class="listbutton" onclick="deleteDialog();">删 除</button>&nbsp;
				<button id="b3" name="b3" class="listbutton" disabled="disabled">清 空</button>&nbsp;
				<button id="b4" name="b4" class="listbutton" disabled="disabled">流 转</button>&nbsp;
				<button id="b5" name="b5" class="listbutton" disabled="disabled">打 印</button>&nbsp;
				<button id="b6" name="b6" class="listbutton" disabled="disabled">导 入</button>&nbsp;
				<button id="b7" name="b7" class="listbutton" disabled="disabled">导 出</button>&nbsp;
				<button id="b8" name="b8" class="listbutton" disabled="disabled">刷 新</button>&nbsp;
				<button id="b9" name="b9" class="listbutton" disabled="disabled">复 制</button>&nbsp;
				<button id="b10" name="b10" class="listbutton" disabled="disabled">粘 贴</button>
				--%>
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
			</td></tr></table>
		</div>
	</div>
	<div class="pageHeader">
		<div class="searchBar">
			<form onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/resource/formlist.action" method="post">
				<input type="hidden" name="code" value="${code}">
				<table class="searchContent">
					<tr>
						<td>
							表单名称${codeParam}
						</td>
						<td>
							<input type="text" name="form.formName" value="${form.formName}"/>
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
					<th align="center" width="5%"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/></th>
					<th align="center" width="5%">序号</th>
					<th width="100" align="center">表单名称</th>
					<%--<th width="100" align="center">编码</th>
					<%--<th width="100" align="center">权限</th>
<%--					<th width="100" align="center">是否上传文件</th>--%>
					<th align="center">功能说明</th>
					<th align="center">主表</th>
					<th width="100" align="center">创建人</th>
					<th width="140" align="center">创建时间</th>
					<th align="center" width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="form" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                    <td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${form.id}"></td>
	                    <td>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    <td>${form.formName}</td>
						<%--<td>${form.code}</td>
						<td>${form.right}</td>
						--%>
<%--						<td>--%>
<%--							<c:if test="${form.isUploadFile == 1}">是</c:if>--%>
<%--							<c:if test="${form.isUploadFile == 0}">否</c:if>--%>
<%--						</td>--%>
						<td>
							${form.remarks}
						</td>
						<td>
							${form.tableChName}
						</td>
						<td>
							${form.userName }
						</td>
						<td><fmt:formatDate value="${form.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td align="center">
							&nbsp;
							<a target="dialog" rel="formDialog" mask="true" width="950" height="650" href="<%=basePath %>/pages/resource/formview.action?id=${form.id}&formManageId=${formManageId}" target="navTab"><span>维护</span></a>
							&nbsp;
						</td>
	                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>