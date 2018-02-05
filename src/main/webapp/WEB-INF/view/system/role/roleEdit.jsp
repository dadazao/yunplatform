<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.role.roleSave = function(){
		$("#domainSubmit").click();
	}
	
	ns.role.selfDialogAjaxDone = function(json) {
		$("#roleId").val(json.domainId);
		refreshList(json);
	}
	
	$(function(){
		ns.role.loadUserTabList();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" class="listbutton" onclick="ns.role.roleSave()">保存</button>
</div>
	<div style="height:2px;"></div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
	                        <li class="selected"><a><span>基本信息</span></a></li>
	                        <li class="selected"><a><span>人员分配</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/role/rolesave.action" class="pageForm required-validate" onsubmit="return validateCallback(this,ns.role.selfDialogAjaxDone);">
					<div style="display:none;">
						<input id="domainSubmit" type="submit">
					</div>
					<input id="roleId" type=hidden name="sysRole.id" value="${sysRole.id}"/>
					<input id="optCounter" type=hidden name="sysRole.optCounter" value="${sysRole.optCounter}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								角色名
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="sysRole.roleName" value="${sysRole.roleName}"  class="textInput required" style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								角色别名
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="sysRole.roleAliss" value="${sysRole.roleAliss}"  class="textInput required" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								角色说明
							</td>
							<td colspan="3" align="left">
								<textarea name="sysRole.comment" rows="5" cols="103" class="textInput">${sysRole.comment}</textarea>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								允许删除
							</td>
							<td colspan="3" align="left">
								<input type="radio" value="1" name="sysRole.canDelete" <c:if test="${sysRole.canDelete == 1}">checked="checked"</c:if>/>允许
								<input type="radio" value="0" name="sysRole.canDelete" <c:if test="${sysRole.canDelete == 0}">checked="checked"</c:if>/>不允许
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								允许编辑
							</td>
							<td colspan="3" align="left">
								<input type="radio" value="1" name="sysRole.canEdit" <c:if test="${sysRole.canEdit == 1}">checked="checked"</c:if>/>允许
								<input type="radio" value="0" name="sysRole.canEdit" <c:if test="${sysRole.canEdit == 0}">checked="checked"</c:if>/>不允许
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								是否启用
							</td>
							<td colspan="3" align="left">
								<input type="radio" value="1" name="sysRole.enable" <c:if test="${sysRole.enable == 1}">checked="checked"</c:if>/>启用
								<input type="radio" value="0" name="sysRole.enable" <c:if test="${sysRole.enable == 0}">checked="checked"</c:if>/>禁用
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div>
				<div id="userTabList" align="center">
				</div>
			</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div>
	</div>

