<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.user.userSave = function(){
		$("#domainSubmit").click();
	}
	
	ns.user.selfDialogAjaxDone = function(json) {
		$("#userId").val(json.domainId);
		refreshList(json);
	}
	
	$(function(){
		ns.user.loadOrgTabList();
		ns.user.loadPositionTabList();
		ns.user.loadRoleTabList();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" class="listbutton" onclick="ns.user.userSave()">保存</button>
</div>
	<div style="height:2px;"></div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
	                        <li class="selected"><a><span>基本信息</span></a></li>
	                        <li class="selected"><a><span>机构选择</span></a></li>
	                        <li class="selected"><a><span>岗位选择</span></a></li>
	                        <li class="selected"><a><span>角色选择</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/user/usersave.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return validateCallback(this,ns.user.selfDialogAjaxDone);">
					<div style="display:none;">
						<input id="domainSubmit" type="submit">
					</div>
					<input id="userId" type="hidden" name="sysUser.id" value="${sysUser.id}"/>
					<input id="optCounter" type=hidden name="sysUser.optCounter" value="${sysUser.optCounter}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="15%">
								账号
							</td>
							<td align="left" width="50%">
								<input maxlength="50" type="text" name="sysUser.username" value="${sysUser.username}"  class="textInput required" style="width:180px;"/>
							</td>
							<td rowspan="10">
								上传照片：
								<input type="file" name="upload"/>
								<input type="hidden" name="sysUser.userPic" value="${sysUser.userPic }">
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								密码
							</td>
							<td align="left">
								<c:if test="${sysUser.id==null}">
									<input maxlength="50" type="password" name="sysUser.password" value="${sysUser.password}"  class="textInput required" style="width:180px;"/>
								</c:if>
								<c:if test="${sysUser.id!=null}">
									●●●●●●
								</c:if>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								姓名
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="sysUser.fullname" value="${sysUser.fullname}"  class="textInput required" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								性别
							</td>
							<td align="left">
								<input type="radio" name="sysUser.sex" value="0" <c:if test="${sysUser.sex == 0}">checked="checked"</c:if>/>男
								<input type="radio" name="sysUser.sex" value="1" <c:if test="${sysUser.sex == 1}">checked="checked"</c:if>/>女
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								手机号码
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="sysUser.mobile" value="${sysUser.mobile}"  class="textInput mobile required" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								电话
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="sysUser.phone" value="${sysUser.phone}"  class="textInput telephone" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								邮箱
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="sysUser.email" value="${sysUser.email}"  class="textInput email required" style="width:360px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								是否锁定
							</td>
							<td align="left">
								<select name="sysUser.lock">
									<option value="0" <c:if test="${sysUser.lock == 0}">selected="selected"</c:if>>未锁定</option>
									<option value="1" <c:if test="${sysUser.lock == 1}">selected="selected"</c:if>>已锁定</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								是否过期
							</td>
							<td align="left">
								<select name="sysUser.overdue">
									<option value="0" <c:if test="${sysUser.overdue == 0}">selected="selected"</c:if>>未过期</option>
									<option value="1" <c:if test="${sysUser.overdue == 1}">selected="selected"</c:if>>已过期</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								当前状态
							</td>
							<td align="left">
								<select name="sysUser.active">
									<option value="0" <c:if test="${sysUser.active == 0}">selected="selected"</c:if>>激活</option>
									<option value="1" <c:if test="${sysUser.active == 1}">selected="selected"</c:if>>未激活</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div>
				<div id="orgTabList" align="center">
				</div>
			</div>
			<div>
				<div id="positionTabList" align="center">
				</div>
			</div>
			<div>
				<div id="roleTabList" align="center">
				</div>
			</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div>
	</div>

