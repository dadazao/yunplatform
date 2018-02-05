<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.user.closeResetPasswordDialog = function(){
		$.pdialog.close("resetPasswordDialog");
	}
	
	ns.user.saveResetPassword = function(){
		if($("#password_one").val()!=$("#password_two").val()){
			alertMsg.warn("两次输入的密码不一样，请重新输入!");
			return;
		}
		$("#resetPasswordForm").submit();
	}
//-->
</script>
<form id="resetPasswordForm" onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/user/userresetPassword.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<input id="userId" type=hidden name="sysUser.id" value="${sysUser.id}"/>
 	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		<tr>
			<td align="left" class="Input_Table_Label" width="20%">
				新密码
			</td>
			<td align="left" colspan="3">
				<input id="password_one" maxlength="50" type="password" class="textInput required" style="width:180px;"/>
			</td>
		</tr>
		<tr>
			<td align="left" class="Input_Table_Label" width="20%">
				确认密码
			</td>
			<td align="left" colspan="3">
				<input id="password_two" maxlength="50" type="password" name="sysUser.password" class="textInput required" style="width:180px;"/>
			</td>
		</tr>
	</table>
	<br/>
	<div class="buttonPanel" align="center">
		<button type="button" class="listbutton" onclick="ns.user.saveResetPassword()">保存</button>
		<button type="button" class="listbutton" onclick="ns.user.closeResetPasswordDialog()">关闭</button>
	</div>
</form>
			

