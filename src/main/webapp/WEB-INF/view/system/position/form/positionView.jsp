<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.position.positionModify = function(){
		$.pdialog.close("viewDialog");
		$.pdialog.open("<%=basePath %>/pages/system/position/positionedit.action?positionId=${position.id}","modifyDialog","维护岗位",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function(){
		ns.position.loadUserTabList();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbutton" onclick="ns.position.positionModify()">修改</button>
	<button type="button" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/system/position/positionsave.action" class="pageForm required-validate" onsubmit="return validateCallback(this,ns.position.selfDialogAjaxDone);">
	<div style="display:none;">
		<input id="domainSubmit" type="submit">
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
					<input id="positionId" type=hidden name="position.id" value="${position.id}"/>
					<input id="positionParentId" type=hidden name="position.parentId" value="${position.parentId}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								岗位名称
							</td>
							<td align="left" width="40%">
								${position.positionName}
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								岗位编号
							</td>
							<td align="left">
								${position.positionNo}
							</td>
						</tr>
<!--						<tr>-->
<!--							<td align="left" class="Input_Table_Label" width="10%">-->
<!--								所在部门-->
<!--							</td>-->
<!--							<td align="left">-->
<!--								${position.orgName}-->
<!--							</td>-->
<!--							<td align="left" class="Input_Table_Label" width="10%">-->
<!--								薪资等级-->
<!--							</td>-->
<!--							<td align="left">-->
<!--								${position.xinzidengji}-->
<!--							</td>-->
<!--						</tr>-->
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								岗位说明
							</td>
							<td colspan="3" align="left">
								${position.comment}
							</td>
						</tr>
					</table>
			</div>
			<div>
				<div id="userTabList" align="center">
				</div>
			</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div>
	</div>
</form>
