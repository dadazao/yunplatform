<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	ns.privilege.privilegeAdd = function(){
		$.pdialog.open("<%=basePath %>/pages/system/privilege/privilegeadd.action?module=${module}","newDialog","新建权限",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.privilege.privilegeView = function(id){
		$.pdialog.open("<%=basePath %>/pages/system/privilege/privilegeview.action?privilegeId="+id,"viewDialog","查看权限",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.privilege.privilegeDelete = function(){
		var url = "<%=basePath %>/pages/system/privilege/privilegedelete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.privilege.privilegeEnable = function(){
		var urlString = "<%=basePath %>/pages/system/privilege/privilegeenable.action";
		var param = $("#tableForm").serialize();
		var items = $("input[name='selectedIDs']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择数据!");
			return;
		}
		$.ajax({
			url : urlString,
			type: 'post',
			dataType : "json",
			data : param,
			success : function(data) {
				alertMsg.info("操作成功!");
				refresh();
			}
		});
	}
	
	ns.privilege.privilegeDisable = function(){
		var urlString = "<%=basePath %>/pages/system/privilege/privilegedisable.action";
		var param = $("#tableForm").serialize();
		var items = $("input[name='selectedIDs']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择数据!");
			return;
		}
		$.ajax({
			url : urlString,
			type: 'post',
			dataType : "json",
			data : param,
			success : function(data) {
				alertMsg.info("操作成功!");
				refresh();
			}
		});
	}
	//以下是资源相关方法
	ns.privilege.loadResource = function() {
		var urlString = "<%=basePath %>/pages/system/privilege/privilegeaddResource.action?module=${module}";
		$('#authResourceId').loadUrl(urlString);
	}
	ns.privilege.loadResourceList = function() {
		var urlString = "<%=basePath %>/pages/system/privilege/privilegeresourceList.action?privilegeId=" + $("#privilegeId").val()+"&module=${module}";
		$.ajaxSetup({async: false});
		$('#authResourceListId').load(urlString);	
		initPagination();
		$.ajaxSetup({async: true});
	}
	ns.privilege.loadEditResource = function(id) {
		var urlString = "<%=basePath %>/pages/system/privilege/privilegeeditResource.action?resourceId=" + id;
		$('#authResourceId').loadUrl(urlString);	
	}
	ns.privilege.addResource = function() {
		$("#resourceId").val("");
		if($("#privilegeId").val()==""||$("#privilegeId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		$resource = $("#resourceFormId");
		if (!$resource.valid()) {
			return false;
		}
		var urlString = "<%=basePath %>/pages/system/privilege/privilegesaveResource.action?privilegeId=" + $("#privilegeId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#resourceFormId").serialize(),
			success: function(data){
				alertMsg.info("保存成功");
				ns.privilege.loadResourceList();
			}
		});
	}
	
	ns.privilege.updateResource = function() {
		if($("#privilegeId").val()==""||$("#privilegeId").val()==undefined){
			alertMsg.warn('请先保存基本信息！');
			return;
		}
		$resource = $("#resourceFormId");
		if (!$resource.valid()) {
			return false;
		}
		var urlString = "<%=basePath %>/pages/system/privilege/privilegesaveResource.action?privilegeId=" + $("#privilegeId").val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#resourceFormId").serialize(),
			success: function(data){
				alertMsg.info("保存成功");
				ns.privilege.loadResourceList();
			}
		});
	}
	
	ns.privilege.deleteResource = function() {
		var params = $("#resourceListForm").serialize();
		if(params==undefined || params==''){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){
				var urlString = "<%=basePath %>/pages/system/privilege/privilegedeleteResource.action?privilegeId=" + $("#privilegeId").val();
				$.ajax({
					type:'post',
					url: urlString,
					data:$("#resourceListForm").serialize(),
					success: function(data){
						alertMsg.info("删除成功");
						dialogRefresh();
					}
				});
			}
		});
	}
	
	ns.privilege.enableResource = function(){
		var urlString = "<%=basePath %>/pages/system/privilege/privilegeenableResource.action";
		var param = $("#resourceListForm").serialize();
		var items = $("input[name='selectedSubIDs']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择数据!");
			return;
		}
		$.ajax({
			url : urlString,
			type: 'post',
			dataType : "json",
			data : param,
			success : function(data) {
				alertMsg.info("操作成功!");
				refresh();
			}
		});
	}
	
	ns.privilege.disableResource = function(){
		var urlString = "<%=basePath %>/pages/system/privilege/privilegedisableResource.action";
		var param = $("#resourceListForm").serialize();
		var items = $("input[name='selectedSubIDs']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择数据!");
			return;
		}
		$.ajax({
			url : urlString,
			type: 'post',
			dataType : "json",
			data : param,
			success : function(data) {
				alertMsg.info("操作成功!");
				refresh();
			}
		});
	}
	
	ns.privilege.addModuleAuth = function(obj,module){
		$.ajax({
			url : "<%=basePath %>/pages/system/privilege/privilegeaddModuleAuth.action?status="+obj.checked+"&module="+module,
			type: 'post',
			success : function(data) {
				//alertMsg.info("操作成功!");
				refresh();
			}
		});
	}
	
	ns.privilege.addTabulationButtonAuth = function(obj,module,tbuttonId){
		$.ajax({
			url : "<%=basePath %>/pages/system/privilege/privilegeaddTabulationButtonAuth.action?status="+obj.checked+"&module="+module+"&tbuttonId="+tbuttonId,
			type: 'post',
			success : function(data) {
				//alertMsg.info("操作成功!");
				refresh();
			}
		});
	}
	
	ns.privilege.addOptButtonAuth = function(obj,module,optbuttonId){
		$.ajax({
			url : "<%=basePath %>/pages/system/privilege/privilegeaddOptButtonAuth.action?status="+obj.checked+"&module="+module+"&optbuttonId="+optbuttonId,
			type: 'post',
			success : function(data) {
				//alertMsg.info("操作成功!");
				refresh();
			}
		});
	}

	ns.privilege.addFormButtonAuth = function(obj,module,fbuttonId){
		$.ajax({
			url : "<%=basePath %>/pages/system/privilege/privilegeaddFormButtonAuth.action?status="+obj.checked+"&module="+module+"&fbuttonId="+fbuttonId,
			type: 'post',
			success : function(data) {
				//alertMsg.info("操作成功!");
				refresh();
			}
		});
	}
</script>
</head>
<body>
	<div class="panel" defH="20">
		<h1>
			模块访问
		</h1>
		<div>
			<input type="checkbox" onclick="ns.privilege.addModuleAuth(this,'${module}')" <c:forEach items="${allModuleAuths}" var="auth"><c:if test="${auth.buttonId==-1}">checked="checked"</c:if></c:forEach>/>访问权
		</div>
	</div>
	<div class="panel" defH="20">
		<h1>
			列表按钮
		</h1>
		<div>
			<c:forEach items="${tbuttonList}" var="tbutton">
				<span title="${tbutton.buttonName}"><input type="checkbox" onclick="ns.privilege.addTabulationButtonAuth(this,'${module}','${tbutton.id}')" <c:forEach items="${allModuleAuths}" var="auth"><c:if test="${auth.buttonId==tbutton.id}">checked="checked"</c:if></c:forEach>/>${tbutton.showName}</span>
			</c:forEach>
			<c:forEach items="${optbuttonList}" var="optbutton">
				<span title="${optbutton.fcomment}"><input type="checkbox" onclick="ns.privilege.addOptButtonAuth(this,'${module}','${optbutton.id}')" <c:forEach items="${allModuleAuths}" var="auth"><c:if test="${auth.buttonId==optbutton.id}">checked="checked"</c:if></c:forEach>/>${optbutton.showName}</span>
			</c:forEach>
		</div> 
	</div>
	<div class="panel" defH="20">
		<h1>
			表单按钮
		</h1>
		<div>
			<c:forEach items="${fbuttonList}" var="fbutton">
				<span title="${fbutton.buttonName}"><input type="checkbox" onclick="ns.privilege.addFormButtonAuth(this,'${module}','${fbutton.id}')" <c:forEach items="${allModuleAuths}" var="auth"><c:if test="${auth.buttonId==fbutton.id}">checked="checked"</c:if></c:forEach>/>${fbutton.showName}</span>
			</c:forEach>
		</div>
	</div>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/privilege/privilegeauthList.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" /> 
		<input type="hidden" name="module" value="${module}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<button type="button" class="listbutton" onclick="ns.privilege.privilegeAdd()">新建</button>
				<button type="button" class="listbutton" onclick="ns.privilege.privilegeDelete();">删除</button>
				<button type="button" class="listbutton" onclick="ns.privilege.privilegeEnable();">启用</button>
				<button type="button" class="listbutton" onclick="ns.privilege.privilegeDisable();">禁用</button>
			</td></tr></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'listId')">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" rel="listId" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<table class="table" width="100%" layoutH="268">
			<thead>
				<tr>
					<th width="5%" align="center"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/></th>
					<th width="5%" align="center">序号</th>
					<th width="30%" align="center">权限名称</th>
					<th width="20%" align="center">权限代码</th>
					<th width="10%" align="center">是否启用</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="privilege" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
		                    <td align="center" width="5%"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${privilege.id}"></td>
		                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    <td>${privilege.privilegeName}</td>
		                    <td>${privilege.code}</td>
		                    <td>
		                    	<c:if test="${privilege.enabled==0}">否</c:if>
		                    	<c:if test="${privilege.enabled==1}">是</c:if>
		                    </td>
		                    <td>
								<a style="cursor: pointer;color: blue;" onclick="ns.privilege.privilegeView('${privilege.id}')">维护</a>
		                    </td>
		                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>