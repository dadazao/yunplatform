<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<form method="post" action="<%=basePath %>/pages/system/user/change.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);" >
	<table width="98%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		<tr>
			<td align="left" class="Input_Table_Label" width="30%">
				原密码
			</td>
			<td align="left" width="70%">
				<input maxlength="50" type="password" name="oldPassword" class="required" style="width:180px"/><!-- <span class="star">*</span> -->
			</td>
		</tr>
		<tr>
			<td align="left" class="Input_Table_Label" width="30%">
				新密码
			</td>
			<td align="left" width="70%">
				<input maxlength="50" type="password" name="newPassword" class="required" style="width:180px"/><!-- <span class="star">*</span> -->
			</td>
		</tr>
		<tr>
			<td align="left" class="Input_Table_Label" width="30%">
				重复密码
			</td>
			<td align="left" width="70%">
				<input maxlength="50" type="password" name="reNewPassword" class="required" style="width:180px"/><!-- <span class="star">*</span> -->
			</td>
		</tr>
	</table>
	<br>
	<div align="center">
		<button type="submit" id="buttonSC" name="buttonSC" class="listbutton">保存</button>&nbsp;
		<button type="button" id="buttonBZ" name="buttonBZ" class="listbutton close">取消</button>
	</div>
</form>