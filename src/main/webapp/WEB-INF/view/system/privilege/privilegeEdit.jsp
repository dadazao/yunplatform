<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.privilege.privilegeSave = function(){
		$("#domainSubmit").click();
	}
	
	ns.privilege.selfDialogAjaxDone = function(json) {
		$("#privilegeId").val(json.domainId);
		refreshList(json);
	}
	
	$(function(){
		ns.privilege.loadResource();
		ns.privilege.loadResourceList();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" class="listbutton" onclick="ns.privilege.privilegeSave()">保存</button>
</div>
	<div style="height:2px;"></div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
						<li class="selected"><a><span>基本信息</span></a></li>
						<li class="selected"><a><span>权限资源</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent">
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/privilege/privilegesave.action" class="pageForm required-validate" onsubmit="return validateCallback(this,ns.privilege.selfDialogAjaxDone);">
					<div style="display:none;">
						<input id="domainSubmit" type="submit">
					</div>
					<input id="privilegeId" type=hidden name="sysPrivilege.id" value="${sysPrivilege.id}"/>
					<input id="optCounter" type=hidden name="sysPrivilege.optCounter" value="${sysPrivilege.optCounter}"/>
					<input id="module" type=hidden name="sysPrivilege.module" value="${sysPrivilege.module}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								权限名
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="sysPrivilege.privilegeName" value="${sysPrivilege.privilegeName}"  class="textInput required" style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								权限代码
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="sysPrivilege.code" value="${sysPrivilege.code}"  class="textInput required" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								是否启用
							</td>
							<td align="left" colspan="3">
								<input type="radio" name="sysPrivilege.enabled" value="0" <c:if test="${sysPrivilege.enabled==0}">checked="checked"</c:if>/>否
								<input type="radio" name="sysPrivilege.enabled" value="1" <c:if test="${sysPrivilege.enabled==1}">checked="checked"</c:if>/>是
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								权限描述
							</td>
							<td colspan="3" align="left">
								<textarea name="sysPrivilege.comment" rows="5" cols="103" class="textInput">${sysPrivilege.comment}</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div>
				<div id="authResourceId" align="center"></div>
				<div id="authResourceListId" align="center"></div>
			</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div>
	</div>