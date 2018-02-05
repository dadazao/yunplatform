<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--	
	ns.role.closeCopyDialog = function(){
		$.pdialog.close("copyDialog");
	}

	$(function(){
		
	});
//-->
</script>
	<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/role/rolecopy.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<div style="display:none;">
		<input id="domainSubmit" type="submit">
	</div>
	<input id="roleId" type=hidden name="sysRole.id" value="${sysRole.id}"/>
	<input id="optCounter" type=hidden name="sysRole.optCounter" value="${sysRole.optCounter}"/>
 	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		<tr>
			<td align="left" class="Input_Table_Label" width="15%">
				原角色名
			</td>
			<td align="left" width="35%">
				${sysRole.roleName}
			</td>
			<td align="left" class="Input_Table_Label" width="15%">
				新角色名
			</td>
			<td align="left" width="35%">
				<input maxlength="50" type="text" name="sysRole.roleName" class="textInput required" style="width:180px;"/>
			</td>
		</tr>
		<tr>
			<td align="left" class="Input_Table_Label" width="15%">
				原别名
			</td>
			<td align="left" width="35%">
				${sysRole.roleAliss}
			</td>
			<td align="left" class="Input_Table_Label" width="15%">
				新别名
			</td>
			<td align="left" width="35%">
				<input maxlength="50" type="text" name="sysRole.roleAliss" class="textInput required" style="width:180px;"/>
			</td>
		</tr>
	</table>
	<br/>
	<div class="buttonPanel" align="center">
		<button type="submit" class="listbutton" onclick="">保存</button>
		<button type="button" class="listbutton" onclick="ns.role.closeCopyDialog()">关闭</button>
	</div>
</form>