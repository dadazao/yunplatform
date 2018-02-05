<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	ns.system.saveAuthTemplate = function(){
		$("#authTemplateForm").submit();
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		
		$("#editauthTemplateId").combobox();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editAuthTemplate" name="editAuthTemplate" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveAuthTemplate" name="saveAuthTemplate" class="listbutton" onclick="ns.system.saveAuthTemplate();">保存</button>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="authTemplateForm" method="post" action="<%=basePath%>/pages/system/authTemplate/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="authTemplateId" type=hidden name="authTemplate.id" value="${authTemplate.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${authTemplate.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								产品
							</td>
							<td align="left" width="40%">
								<select id="editauthTemplateId" name="authTemplate.productid"
									style="width: 186px" class="textInput required">
									<c:forEach items="${authProducts}" var="bean">
										<option value="${bean.id}" <c:if test="${bean.id==authTemplate.productid }">selected="selected"</c:if>>${bean.name}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								模版名称
							</td>
							<td align="left">
								<input maxlength="50" type="text" name="authTemplate.name" value="${authTemplate.name}"  class="textInput required" style="width:180px;" />
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								授权类型
							</td>
							<td align="left" width="40%">
								<select id="authtypeId" name="authTemplate.authtype"
									style="width: 180px">
									<option value="CLOUD" >云授权</option>
									<option value="NATIVE" >本地授权</option>
								</select>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								有效期
							</td>
							<td align="left">
								<input maxlength="10" type="text" name="authTemplate.validdays" value="${authTemplate.validdays}"  class="textInput number" style="width:60px;"/>&nbsp;&nbsp;天
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								起始日期
							</td>
							<td align="left" >
								<input type="text" id="startdateId" name="authTemplate.startdate" value='<fmt:formatDate value="${authTemplate.startdate}" pattern="yyyy-MM-dd"/>' readonly="true" class="date" style="width:135px;"/>
								<img onclick="WdatePicker({el:'startdateId',dateFmt:'yyyy-MM-dd'})" src="<%=basePath %>js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								结束日期
							</td>
							<td align="left" >
								<input type="text" id="enddateId" name="authTemplate.enddate" value='<fmt:formatDate value="${authTemplate.enddate}" pattern="yyyy-MM-dd"/>' readonly="true" class="date" style="width:135px;"/>
								<img onclick="WdatePicker({el:'enddateId',dateFmt:'yyyy-MM-dd'})" src="<%=basePath %>js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;"/>
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