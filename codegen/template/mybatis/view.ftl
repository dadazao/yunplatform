<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		<#list mainTable.subTables as subTable>
			<#if subTable.relation=="1:1">
		$("#tabDivId").append('<div id="${subTable.variables.class?uncap_first}Tab"></div>');
		$("#${subTable.variables.class?uncap_first}Tab").loadUrl('${mainTable.namespace}/${subTable.variables.class?uncap_first}/view.action?${mainTable.variables.class?uncap_first}Id='+$('#domainId').val());
			<#else>
		$("#tabDivId").append('<div id="${subTable.variables.class?uncap_first}Tab"></div>');
		$("#${subTable.variables.class?uncap_first}Tab").loadUrl('${mainTable.namespace}/${subTable.variables.class?uncap_first}Tab.jsp');
			</#if>
		</#list>
	});
		//--> 
</script>
<div class="buttonPanel">
	<button type="button" id="edit${mainTable.variables.class}" name="edit${mainTable.variables.class}" class="listbutton" onclick="ns.${mainTable.variables.class?uncap_first}.edit${mainTable.variables.class}('<#noparse>${</#noparse>${mainTable.variables.class?uncap_first}.id<#noparse>}</#noparse>');">修改</button>
	<button type="button" id="save${mainTable.variables.class}" name="save${mainTable.variables.class}" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${'${'}${mainTable.variables.class?uncap_first}.id${'}'}"/>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
					<#list mainTable.subTables as subTable>
					<li><a><span>${subTable.chName}</span></a></li>
					</#list>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		  	 	<#assign index=0>
				<#list mainTable.formColumns as col>
					<#if col.view>
						<#if index%2==0>
					<tr>
						</#if>
						<td align="left" class="Input_Table_Label" width="10%">
							${col.column.chName}
						</td>
						<td align="left" width="40%">
						<#if col.inputType==4>
							<fmt:formatDate value="${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}" pattern="yyyy-MM-dd HH:mm:ss"/>
						<#else>
							${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}
						</#if>
						</td>
						<#if index%2==1>
					</tr>
						</#if>
					<#assign index=index+1>
					</#if>
				</#list>
				</table>
			</div>	      	
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>