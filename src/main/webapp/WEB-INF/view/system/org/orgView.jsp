<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.org.orgModify = function(){
		$.pdialog.close("viewDialog");
		$.pdialog.open("<%=basePath %>/pages/system/org/orgedit.action?orgId=${sysOrg.id}","modifyDialog","维护机构",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function(){
		ns.org.loadUserTabList();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbutton" onclick="ns.org.orgModify()">修改</button>
	<button type="button" class="listbuttonDisable" disabled="disabled">保存</button>
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
				<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/org/orgsave.action" class="pageForm required-validate" onsubmit="return validateCallback(this,selfDialogAjaxDone);">
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
								${sysOrg.orgName}
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								机构级别
							</td>
							<td align="left">
								${sysOrg.orgLevel}
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								顺序
							</td>
							<td colspan="3">
								${sysOrg.orgOrder}
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								机构职能
							</td>
							<td colspan="3" align="left">
								${sysOrg.orgFunction}
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

