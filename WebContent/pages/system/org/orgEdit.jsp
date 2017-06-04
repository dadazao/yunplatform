<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.org.orgSave = function(){
		$("#domainSubmit").click();
	}
	
	ns.org.selfDialogAjaxDone = function(json) {
		$("#orgId").val(json.domainId);
		$("#treeId").load("<%=basePath %>/pages/system/org/orgSelfTree.jsp");
		refreshList(json);
	}
	
	$(function(){
		ns.org.loadUserTabList();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" class="listbutton" onclick="ns.org.orgSave()">保存</button>
</div>
	<div style="height:2px;"></div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
	                        <li class="selected"><a><span>基本信息</span></a></li>
	                        <li class="selected"><a><span>机构人员</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/org/orgsave.action" class="pageForm required-validate" onsubmit="return validateCallback(this,ns.org.selfDialogAjaxDone);">
					<div style="display:none;">
						<input id="domainSubmit" type="submit">
					</div>
					<input id="orgId" type=hidden name="sysOrg.id" value="${sysOrg.id}"/>
					<input id="optCounter" type=hidden name="sysOrg.optCounter" value="${sysOrg.optCounter}"/>
					<input id="orgParentId" type=hidden name="sysOrg.parentId" value="${sysOrg.parentId}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								机构名称
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="sysOrg.orgName" value="${sysOrg.orgName}"  class="textInput required" style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								机构级别
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="sysOrg.orgLevel" value="${sysOrg.orgLevel}"  class="textInput digits required" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								顺序
							</td>
							<td colspan="3">
								<input type="text" name="sysOrg.orgOrder" value="${sysOrg.orgOrder}" class="digits" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								机构职能
							</td>
							<td colspan="3" align="left">
								<textarea name="sysOrg.orgFunction" rows="5" cols="103" class="textInput">${sysOrg.orgFunction}</textarea>
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

