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
	ns.${mainTable.variables.class?uncap_first}.save${mainTable.variables.class} = function(){
		$("#${mainTable.variables.class?uncap_first}Form").submit();
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
	
	$(function(){
	<#list mainTable.subTables as subTable>
		<#if subTable.relation=="1:1">
		$("#tabDivId").append('<div id="${subTable.variables.class?uncap_first}Tab"></div>');
		$("#${subTable.variables.class?uncap_first}Tab").loadUrl('${mainTable.namespace}/${subTable.variables.class?uncap_first}/edit.action?${mainTable.variables.class?uncap_first}Id='+$('#domainId').val());
		<#else>
		$("#tabDivId").append('<div id="${subTable.variables.class?uncap_first}Tab"></div>');
		$("#${subTable.variables.class?uncap_first}Tab").loadUrl('${mainTable.namespace}/${subTable.variables.class?uncap_first}/tab.action');
		</#if>
	</#list>
	});
	
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="edit${mainTable.variables.class}" name="edit${mainTable.variables.class}" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="save${mainTable.variables.class}" name="save${mainTable.variables.class}" class="listbutton" onclick="ns.${mainTable.variables.class?uncap_first}.save${mainTable.variables.class}();">保存</button>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected" onclick="ns.common.changeSaveAction('save${mainTable.variables.class}','ns.${mainTable.variables.class?uncap_first}.save${mainTable.variables.class}')"><a><span>基本信息</span></a></li>
					<#list mainTable.subTables as subTable>
						<#if subTable.relation=="1:1">	
					<li onclick="ns.common.changeSaveAction('save${mainTable.variables.class}','ns.${subTable.parentClassName?uncap_first}.save${subTable.variables.class}')"><a><span>${subTable.chName}</span></a></li>
						<#elseif subTable.relation="1:n">
					<li onclick="ns.common.changeSaveAction('save${mainTable.variables.class}','ns.${subTable.parentClassName?uncap_first}.save${mainTable.variables.class}')"><a><span>${subTable.chName}</span></a></li>
						</#if>
					</#list>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="${mainTable.variables.class?uncap_first}Form" method="post" action="<%=basePath %>/${mainTable.namespace}/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="${mainTable.variables.class?uncap_first}Id" type=hidden name="${mainTable.variables.class?uncap_first}.id" value="${'${'}${mainTable.variables.class?uncap_first}.id${'}'}"/>
					<input id="domainId" type="hidden" name="domainId" value="${'${'}${mainTable.variables.class?uncap_first}.id${'}'}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<#assign index = 0>
					<#list mainTable.formColumns as col>
						<#if col.edit>
						<#if index%2==0>
						<tr>
						</#if>
							<td align="left" class="Input_Table_Label" width="10%">
								${col.column.chName}
							</td>
							<#switch col.inputType>
								<#case 1>
							<td align="left" width="40%">
			 		 			<input type="text" name="${mainTable.variables.class?uncap_first}.${col.column.colName}" value="${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}"  class="textInput <#if col.notNull>required</#if>" style="width:180px;"/>
							</td>
								<#break>
								<#case 2>
							<td align="left" width="40%">
								<select style="width:186px" name="${mainTable.variables.class?uncap_first}.${col.column.colName}" class="valid">
										<option value="-1">请选择</option>
								</select>
							</td>
								<#break>
								<#case 3>
									<#if index%2==0>
							<td align="left" with="90%">
					 			<textarea name="${mainTable.variables.class?uncap_first}.${col.column.colName}" class="textInput <#if col.notNull>required</#if>" cols="80" rows="5">${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}</textarea>
							</td>
									<#else>
							<td align="left" width="40%"></td>
									</#if>
								<#break>
								<#case 4>
							<td align="left" width="40%">
							<input readonly="true" type="text" id="${mainTable.variables.class?uncap_first}.${col.column.colName}" name="${mainTable.variables.class?uncap_first}.${col.column.colName}" value='<fmt:formatDate value="${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 180px;"/>
							<img onclick="WdatePicker({el:'${mainTable.variables.class?uncap_first}.${col.column.colName}',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
							</td>
								<#break>
							</#switch>
						<#if index%2==1>
						</tr>
						</#if>
						<#assign index = index+1>
						</#if>
					</#list>
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