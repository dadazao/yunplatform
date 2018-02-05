<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.user.closeResetStatusDialog = function(){
		$.pdialog.close("resetStatusDialog");
	}
	
	ns.user.saveResetStatus = function(){
		$("#resetStatusForm").submit();
	}
//-->
</script>
<form id="resetStatusForm" onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/user/userresetStatus.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<input id="userId" type=hidden name="sysUser.id" value="${sysUser.id}"/>
 	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td align="left" class="Input_Table_Label" width="20%">
					是否锁定
				</td>
				<td align="left" colspan="3">
					<select name="sysUser.lock">
						<option value="0" <c:if test="${sysUser.lock == 0}">selected="selected"</c:if>>未锁定</option>
						<option value="1" <c:if test="${sysUser.lock == 1}">selected="selected"</c:if>>已锁定</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label" width="20%">
					当前状态
				</td>
				<td align="left" colspan="3">
					<select name="sysUser.active">
						<option value="0" <c:if test="${sysUser.active == 0}">selected="selected"</c:if>>激活</option>
						<option value="1" <c:if test="${sysUser.active == 1}">selected="selected"</c:if>>未激活</option>
					</select>
				</td>
			</tr>
		</table>
	<br/>
	<div class="buttonPanel" align="center">
		<button type="button" class="listbutton" onclick="ns.user.saveResetStatus()">保存</button>
		<button type="button" class="listbutton" onclick="ns.user.closeResetStatusDialog()">关闭</button>
	</div>
</form>
			

