<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	ns.system.saveAuthCustomer = function(){
		$("#authCustomerForm").submit();
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		
		$("#tabDivId").append('<div id="subTab" onclick=""></div>');
		$("#subTab").loadUrl('<%=basePath %>/pages/system/authCustomerHadware/tab.action');
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editAuthCustomer" name="editAuthCustomer" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveAuthCustomer" name="saveAuthCustomer" class="listbutton" onclick="ns.system.saveAuthCustomer();">保存</button>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
					<li onclick="ns.common.changeSaveAction('saveAuthCustomer','ns.system.saveAuthCustomer')"><a><span>硬件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="authCustomerForm" method="post" action="<%=basePath%>/pages/system/authCustomer/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="authCustomerId" type=hidden name="authCustomer.id" value="${authCustomer.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${authCustomer.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								客户名称
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="authCustomer.name" value="${authCustomer.name}"  class="textInput required" style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								联系人
							</td>
							<td align="left">
								<input maxlength="10" type="text" name="authCustomer.linkman" value="${authCustomer.linkman}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								手机
							</td>
							<td align="left" width="40%">
								<input maxlength="30" type="text" name="authCustomer.mobile" value="${authCustomer.mobile}"  class="textInput number" style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								邮箱
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="authCustomer.email" value="${authCustomer.email}"  class="textInput email" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								公司电话
							</td>
							<td align="left" width="40%">
								<input maxlength="30" type="text" name="authCustomer.tel" value="${authCustomer.tel}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								公司地址
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="authCustomer.address" value="${authCustomer.address}"  class="textInput " style="width:180px;"/>
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