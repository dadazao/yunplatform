<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	//保存实体
	ns.employee.saveEmployee = function(){
		$("#employeeForm").submit();
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
	
	$(function(){
		$("#tabDivId").append('<div id="detailTab"></div>');
		$("#detailTab").loadUrl('<%=basePath %>/pages/business/employee/detail/edit.action?employeeId='+$('#domainId').val());
		$("#tabDivId").append('<div id="projectTab"></div>');
		$("#projectTab").loadUrl('<%=basePath %>/pages/business/employee/projectTab.jsp');
	});
	
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editEmployee" name="editEmployee" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveEmployee" name="saveEmployee" class="listbutton" onclick="ns.employee.saveEmployee();">保存</button>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected" onclick="ns.common.changeSaveAction('saveEmployee','ns.employee.saveEmployee')"><a><span>基本信息</span></a></li>
					<li onclick="ns.common.changeSaveAction('saveEmployee','ns.detail.saveDetail')"><a><span>员工详细信息表</span></a></li>
					<li onclick="ns.common.changeSaveAction('saveEmployee','ns.employee.saveEmployee')"><a><span>项目表</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="employeeForm" method="post" action="<%=basePath%>/pages/business/employee/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="employeeId" type=hidden name="employee.id" value="${employee.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${employee.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								姓名
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="employee.name" value="${employee.name}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								编号
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="employee.code" value="${employee.code}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								生日
							</td>
							<td align="left" width="40%">
							<input readonly="true" type="text" id="employee.birthday" name="employee.birthday" value='<fmt:formatDate value="${employee.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 180px;"/>
							<img onclick="WdatePicker({el:'employee.birthday',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								手机
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="employee.phone" value="${employee.phone}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>