<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.position.positionSave = function(){
		$("#domainSubmit").click();
	}
	
	ns.position.selfDialogAjaxDone = function(json) {
		$("#positionId").val(json.domainId);
		$("#treeId").load("<%=basePath %>/pages/system/position/positionTree.jsp");
		refreshList(json);
	}
	
	$(function(){
		ns.position.loadUserTabList();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" class="listbutton" onclick="ns.position.positionSave()">保存</button>
</div>
	<div style="height:2px;"></div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
	                        <li class="selected"><a><span>基本信息</span></a></li>
	                        <li class="selected"><a><span>岗位人员</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/position/positionsave.action" class="pageForm required-validate" onsubmit="return validateCallback(this,ns.position.selfDialogAjaxDone);">
					<div style="display:none;">
						<input id="domainSubmit" type="submit">
					</div>
					<input id="positionId" type=hidden name="position.id" value="${position.id}"/>
					<input id="optCounter" type=hidden name="position.optCounter" value="${position.optCounter}"/>
					<input id="positionParentId" type=hidden name="position.parentId" value="${position.parentId}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								岗位名称
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="position.positionName" value="${position.positionName}"  class="textInput required" style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								岗位编号
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="position.positionNo" value="${position.positionNo}"  class="textInput required" style="width:180px;"/>
							</td>
						</tr>
<!--						<tr>-->
<!--							<td align="left" class="Input_Table_Label" width="10%">-->
<!--								所在部门-->
<!--							</td>-->
<!--							<td align="left">-->
<!--								<input id="positionOrgName" type="text" readonly="true" value="${position.orgName}">-->
<!--								<input id="positionOrgId" type="hidden" value="${position.orgId}" name="position.orgId">-->
<!--								<button class="listbutton" onclick="showOrgTree('positionOrgName','positionOrgId')" type="button" style="width:44px;height:20px;">选择</button>-->
<!--							</td>-->
<!--							<td align="left" class="Input_Table_Label" width="10%">-->
<!--								薪资等级-->
<!--							</td>-->
<!--							<td align="left">-->
<!--								<input type="text" maxlength="50" name="position.xinzidengji" value="${position.xinzidengji}" style="width:180px" />-->
<!--							</td>-->
<!--						</tr>-->
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								岗位说明
							</td>
							<td colspan="3" align="left">
								<textarea name="position.comment" rows="5" cols="103" class="textInput">${position.comment}</textarea>
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

